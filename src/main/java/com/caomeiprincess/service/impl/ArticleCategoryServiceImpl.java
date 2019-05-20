package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.ArticleCategory;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.mapper.ArticleCategoryMapper;
import com.caomeiprincess.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    public void deleteByArticleId(Long id) {
        if(id!=null && id!=0){
            articleCategoryMapper.deleteByPrimaryKey(id);
        }else{
            throw new GlobalException("参数错误！");
        }
    }
}
