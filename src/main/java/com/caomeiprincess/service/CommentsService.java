package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Comments;

import java.util.List;
import java.util.Map;

public interface CommentsService extends BaseService<Comments> {

    List<Comments> findAll();

    int findAllCount();

    Map<String, Object> findCommentsList(Integer pageCode, Integer pageSize, Integer articleId, Integer sort);

    void deleteComments(List<Long> ids);

    List<Comments> findByPage(Comments comments);
}
