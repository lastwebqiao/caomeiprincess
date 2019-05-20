package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.ArticleTags;
import com.caomeiprincess.entity.Tags;

import java.util.List;

public interface ArticleTagService extends BaseService<ArticleTags> {
    void deleteByArticleId(Long id);

    List<Tags> findByArticleId(Long id);
}
