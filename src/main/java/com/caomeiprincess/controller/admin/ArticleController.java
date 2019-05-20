package com.caomeiprincess.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Article;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.entity.Tags;
import com.caomeiprincess.entity.User;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.service.ArticleService;
import com.caomeiprincess.service.ArticleTagService;
import com.caomeiprincess.service.CommentsService;
import com.caomeiprincess.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController extends BaseController {

    @RequestMapping("admin/article/publish")
    public String articlePublish(Model model){
        model.addAttribute("user",(User) SecurityUtils.getSubject().getPrincipal());
        return "admin/page/publish";
    }

    @Autowired
    private ArticleService articleService;
    @BodyMapping(value = "article/save")
    @Log("新增文章")
    public ResponseCode save(@Validated @RequestBody Article article) {
        try {
            articleService.save(article);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @RequestMapping("admin/article")
    public String articleAdmin(Model model){
        init(model);
        return "admin/page/article";
    }

    @BodyMapping("article/findByPage")
    public ResponseCode findArticleByPage(QueryPage queryPage, Article article){
        return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> articleService.findByPage(article)));
    }

    @RequestMapping(value = "admin/article/edit/{id}")
    public String editById(@PathVariable Long id, Model model){
        init(model);
        if (id == null || id == 0) {
            return "admin/page/article";
        } else {
            return "admin/page/edit";
        }
    }

    @Autowired
    private ArticleTagService articleTagService;

    @BodyMapping("article/findById")
    public ResponseCode findArticleById(@RequestParam("id") Long id,Model model){
        Article article = articleService.findById(id);
        if (article.getId() != 0) {
            List<String> tags = new ArrayList<>();
            List<Tags> tagList = articleTagService.findByArticleId(article.getId());
            tagList.forEach(t -> {
                tags.add(t.getName());
            });
            article.setTags(JSON.toJSONString(tags));
            return ResponseCode.success(article);
        } else {
            return ResponseCode.error();
        }
    }

    @BodyMapping("article/update")
    @Log("修改文章")
    public ResponseCode updateArticle(@RequestBody Article article){
        try {
            articleService.update(article);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @BodyMapping(value = "article/delete",method = RequestMethod.POST)
    @Log("删除文章")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            articleService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Autowired
    private CommentsService commentService;

    @RequestMapping("article/{id}")
    public String generate(@PathVariable("id") Long id,
                           @RequestParam(value = "cp", required = false) Integer cp, Model model) {
        if (id != null && id != 0) {
            articleService.addViews(id);
            model.addAttribute("article", articleService.findById(id));
            if (cp == null) {
                //查询的第一页评论数据
                cp = 1;
            }
            //三个参数：1.pageCode当前页；2.PageSize默认每页显示6条（大）留言；3.文章ID值；4.sort当前是文章详情页，sort=0。
            //规定：sort=0表示文章详情页的评论信息；sort=1表示友链页的评论信息；sort=2表示关于我页的评论信息
            getPage(commentService.findCommentsList(cp, 6, new Long(id).intValue(), 0), model, cp, 0);
            return "site/page/detail";
        } else {
            return "site/index";
        }
    }
    private void getPage(Map<String, Object> map, Model model, Integer cp, Integer sort) {
        model.addAttribute("count", map.get("total"));
        map.put("total", (long) Math.ceil(((Long) map.get("total")).doubleValue() / (double) 6));
        model.addAttribute("talkList", map);
        model.addAttribute("cp", cp);
        model.addAttribute("sort", sort);
        initCommon(model);
    }

    @Autowired
    private UserService userService;
    private void initCommon(Model model) {
        model.addAttribute("newArticle", articleService.findAll());
        model.addAttribute("newComment", commentService.findAll());
        Setting setting = userService.findSetting();
        setting.setSiteLinks(JSONArray.parseArray((String) setting.getSiteLinks()));
        setting.setSiteDonation(JSONArray.parseArray((String) setting.getSiteDonation()));
        model.addAttribute("setting", setting);
    }
}

