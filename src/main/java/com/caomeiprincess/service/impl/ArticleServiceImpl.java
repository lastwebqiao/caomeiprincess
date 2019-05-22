package com.caomeiprincess.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.dto.ArticleArchives;
import com.caomeiprincess.entity.*;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.mapper.ArticleMapper;
import com.caomeiprincess.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<Article> findAll() {
        Example example = new Example(Article.class);
        example.setOrderByClause("`id` desc");
        return articleMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 8));
    }

    @Override
    public Map<String, Object> findByPageForSite(int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPageForSite();
        List<Article> articleList = page.getResult();
        findInit(articleList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("data", articleList);
        return map;
    }

    @Override
    public int findAllCount() {
        return articleMapper.selectCount(new Article());
    }

    @Override
    @Transactional
    public void save(Article article) {
        try {
            if (article.getState() == "1") {
                article.setPublishTime(new Date());
            }
            article.setAuthor(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
            article.setEditTime(new Date());
            article.setCreateTime(new Date());
            articleMapper.insert(article);
            article.setId(articleMapper.getLastId());
            updateArticleCategoryTags(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public List<Article> findByPage(Article article) {
        Example example = new Example(Article.class);
        if (!StringUtils.isEmpty(article.getTitle())) {
            example.createCriteria().andLike("title", "%" + article.getTitle() + "%");
        }
        List<Article> list = articleMapper.selectByExample(example);
        findInit(list);
        return list;
    }

    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public void delete(List<Long> ids) {
        if(!ids.isEmpty()){
            ids.forEach(id->{
                articleMapper.deleteByPrimaryKey(id);
                //删除文章-分类表的关联
                articleCategoryService.deleteByArticleId(id);
                //删除文章-标签表的关联
                articleTagService.deleteByArticleId(id);
            });
        }
    }

    @Override
    public Article findById(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Article article) {
        try {
            this.updateNotNull(article);
            updateArticleCategoryTags(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void addViews(Long id) {
        if (!id.equals(null) && id != 0) {
            try {
                articleMapper.addViews(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    public List<ArticleArchives> findArchives() {
        List<ArticleArchives> articleArchivesList = new ArrayList<ArticleArchives>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<Article> articleList = articleMapper.findArchivesByDate(date);
                ArticleArchives articleArchives = new ArticleArchives(date, articleList);
                articleArchivesList.add(articleArchives);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return articleArchivesList;
    }

    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param article
     */
    @Transactional
    private void updateArticleCategoryTags(Article article) {
        if (article.getId() != 0) {
            if (article.getCategory() != null) {
                //证明新插入的文章有分类信息，将这个文章分类保存到分类表中
                categoryService.save(new Category(article.getCategory()));

                //保存了分类信息再保存分类-文章的关联信息
                Category category = categoryService.findByName(article.getCategory());
                articleCategoryService.save(new ArticleCategory(article.getId(), category.getId()));
            }
            if (article.getTags() != null) {
                //证明新插入的文章有标签数据，将标签数据保存到标签表中
                List<String> list = (List) JSONArray.parse(article.getTags()); //前端传来的标签是JSON字符串格式的标签名称
                if (list.size() > 0) {
                    list.forEach(name -> {
                        tagService.save(new Tags(name));
                        Tags tag = tagService.findByName(name); //因为标签是多个的，需要依次将标签信息保存到标签表中

                        if (tag != null) {
                            //说明该标签插入成功或已存在，建立标签-文章关联信息
                            articleTagService.save(new ArticleTags(article.getId(), tag.getId()));
                        }
                    });
                }
            }
        }
    }


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    private void findInit(List<Article> list) {
        if (!list.isEmpty()) {
            list.forEach(article -> {
                List<Category> categoryList = categoryService.findByArticleId(article.getId());
                if (categoryList.size() > 0) {
                    article.setCategory(categoryList.get(0).getName());
                }
                List<Tags> tagList = tagService.findByArticleId(article.getId());
//                List<String> stringList = new ArrayList<>();
//                tagList.forEach(tags -> {
//                    stringList.add(tags.getName());
//                });
                article.setTags(JSON.toJSONString(tagList));
            });
        }
    }
}
