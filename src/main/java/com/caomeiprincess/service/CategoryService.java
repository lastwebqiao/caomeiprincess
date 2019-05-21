package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {
    List<Category> findByArticleId(Long id);

    List<Category> findAll();

    void save(Category category);

    Category findByName(String category);

    void delete(List<Long> ids);

    Category findById(Long id);

    void update(Category category);
}
