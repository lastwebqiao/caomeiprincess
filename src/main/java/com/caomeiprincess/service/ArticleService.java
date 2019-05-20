package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Article;

import java.sql.Savepoint;
import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService<Article> {

    List<Article> findAll();

    Map<String, Object> findByPageForSite(int pageCode, int pageSize);

    int findAllCount();

    void save(Article article);

    List<Article> findByPage(Article article);

    void delete(List<Long> ids);

    Article findById(Long id);

    void update(Article article);

    void addViews(Long id);
}
