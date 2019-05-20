package com.caomeiprincess.service;

import com.caomeiprincess.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findByArticleId(Long id);

    List<Category> findAll();

    void save(Category category);

    Category findByName(String category);
}
