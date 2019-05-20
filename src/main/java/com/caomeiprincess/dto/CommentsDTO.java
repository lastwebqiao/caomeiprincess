package com.caomeiprincess.dto;

import com.caomeiprincess.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装评论信息的DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO implements Serializable {

    /**
     * 父级留言信息
     */
    private Comments parent;
    /**
     * 所有子级回复、评论列表
     */
    private List<Comments> childrenList;
}
