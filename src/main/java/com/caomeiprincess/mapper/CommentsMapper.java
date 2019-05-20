package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.Comments;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentsMapper extends MyMapper<Comments> {
    @Select("<script> SELECT * FROM tb_comments WHERE p_id = 0 AND c_id = 0 <when test='articleId != 0'> AND article_id = #{articleId} </when> AND type = #{sort} </script>")
    Page<Comments> findAllId(@Param("articleId") Integer articleId,@Param("sort") Integer sort);

    @Select("<script> SELECT * FROM tb_comments WHERE 1=1 <when test='articleId != 0'> AND article_id = #{articleId} </when> AND type = #{sort} </script>")
    List<Comments> findCommentsList(@Param("articleId") Integer articleId, Integer sort);
}
