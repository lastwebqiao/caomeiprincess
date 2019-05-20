package com.caomeiprincess.service.impl;

import com.caomeiprincess.entity.Tags;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.mapper.TagMapper;
import com.caomeiprincess.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public int findAllCount() {
        return tagMapper.selectCount(new Tags());
    }

    @Override
    public List<Tags> findByArticleId(Long id) {
        if (!id.equals(null) && id != 0) {
            return tagMapper.findTagByArticleId(id);
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    public void save(Tags tags) {
        tagMapper.insert(tags);
    }

    @Override
    public Tags findByName(String name) {
        Tags tags = new Tags();
        tags.setName(name);
        return tagMapper.select(tags).get(0);
    }
}
