package com.caomeiprincess.service.impl;

import com.caomeiprincess.entity.Category;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.mapper.CaregoryMapper;
import com.caomeiprincess.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CaregoryMapper categoryMapper;
    public List<Category> findByArticleId(Long id) {
        if (!id.equals(null) && id != 0) {
            return categoryMapper.findCategoryByArticleId(id);
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public void save(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public Category findByName(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return categoryMapper.select(category).get(0);
    }
}
