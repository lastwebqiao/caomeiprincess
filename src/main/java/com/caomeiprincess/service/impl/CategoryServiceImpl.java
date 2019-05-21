package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.Category;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.mapper.CaregoryMapper;
import com.caomeiprincess.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

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

    @Transactional
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

    @Override
    public void delete(List<Long> ids) {
        batchDelete(ids,"id",Category.class);
    }

    @Override
    public Category findById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
}
