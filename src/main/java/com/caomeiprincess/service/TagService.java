package com.caomeiprincess.service;

import com.caomeiprincess.entity.Tags;

import java.util.List;

public interface TagService {
    int findAllCount();

    List<Tags> findByArticleId(Long id);

    void save(Tags tags);

    Tags findByName(String name);
}
