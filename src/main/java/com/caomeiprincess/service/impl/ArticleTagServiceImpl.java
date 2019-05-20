package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.ArticleTags;
import com.caomeiprincess.entity.Tags;
import com.caomeiprincess.mapper.ArticleTagMapper;
import com.caomeiprincess.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTags> implements ArticleTagService{

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void deleteByArticleId(Long id) {
        articleTagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Tags> findByArticleId(Long id) {
        return articleTagMapper.findByArticleId(id);
    }
}
