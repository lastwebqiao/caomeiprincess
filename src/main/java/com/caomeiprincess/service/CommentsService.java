package com.caomeiprincess.service;

import com.caomeiprincess.entity.Comments;

import java.util.List;
import java.util.Map;

public interface CommentsService {

    List<Comments> findAll();

    int findAllCount();

    Map<String, Object> findCommentsList(Integer pageCode, Integer pageSize, Integer articleId, Integer sort);
}
