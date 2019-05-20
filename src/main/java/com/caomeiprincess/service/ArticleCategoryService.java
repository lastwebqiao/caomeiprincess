package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.ArticleCategory;

public interface ArticleCategoryService extends BaseService<ArticleCategory> {
    void deleteByArticleId(Long id);
}
