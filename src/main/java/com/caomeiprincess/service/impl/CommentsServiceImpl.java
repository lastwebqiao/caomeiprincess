package com.caomeiprincess.service.impl;

import com.caomeiprincess.dto.CommentsDTO;
import com.caomeiprincess.entity.Comments;
import com.caomeiprincess.mapper.CommentsMapper;
import com.caomeiprincess.service.CommentsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Override
    public List<Comments> findAll() {
        Example example = new Example(Comments.class);
        example.setOrderByClause("'id' desc");
        return commentsMapper.selectByExampleAndRowBounds(example,new RowBounds(0,8));
    }

    @Override
    public int findAllCount() {
        return commentsMapper.selectCount(new Comments());
    }

    @Override
    public Map<String, Object> findCommentsList(Integer pageCode, Integer pageSize, Integer articleId, Integer sort) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageCode, pageSize);
        Page<Comments> page = commentsMapper.findAllId(articleId, sort);
        List<Comments> list = commentsMapper.findCommentsList(articleId, sort);
        List<CommentsDTO> commentsDTOS = new ArrayList<CommentsDTO>();

        /**
         * 封装结果类型结构：
         *      [{{Comments-Parent}, [{Comments-Children}, {Comments-Children}...]}, {{}, [{}, {}, {}...]}]
         */
        list.forEach(comments -> {
            List<Comments> commentList = new ArrayList<Comments>();
            if (comments.getPId() == 0 && comments.getCId() == 0) {
                //说明是顶层的文章留言信息
                list.forEach(parent -> {
                    if (parent.getPId() != 0) {
                        if (parent.getPId().equals(comments.getId())) {
                            //说明属于当前父节点
                            commentList.add(parent);
                        }
                    }
                });
                commentsDTOS.add(new CommentsDTO(comments, commentList));
            }
        });
        if (page.getTotal() < (pageCode * pageSize) - 1) {
            map.put("total", page.getTotal());
            map.put("rows", commentsDTOS.subList((pageCode - 1) * pageSize, commentsDTOS.size()));
            return map;
        } else {
            map.put("total", page.getTotal());
            map.put("rows", commentsDTOS.subList((pageCode - 1) * pageSize, (pageCode * pageSize) - 1));
            return map;
        }
    }
}
