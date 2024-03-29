package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.Comments;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CommentsMapper extends MyMapper<Comments> {
    @Select("<script> SELECT * FROM tb_comments WHERE p_id = 0 AND c_id = 0 <when test='articleId != 0'> AND article_id = #{articleId} </when> AND type = #{sort} </script>")
    @Results({
            @Result(column="p_id", property="pId"),
            @Result(column="c_id", property="cId"),
            @Result(column="article_title", property="articleTitle"),
            @Result(column="article_id", property="articleId"),
            @Result(column="c_name", property="cName")
    })
    Page<Comments> findAllId(@Param("articleId") Integer articleId, @Param("sort") Integer sort);

    @Results({
            @Result(column="p_id", property="pId"),
            @Result(column="c_id", property="cId"),
            @Result(column="article_title", property="articleTitle"),
            @Result(column="article_id", property="articleId"),
            @Result(column="c_name", property="cName")
    })
    @Select("<script> SELECT * FROM tb_comments WHERE 1=1 <when test='articleId != 0'> AND article_id = #{articleId} </when> AND type = #{sort} </script>")
    List<Comments> findCommentsList(@Param("articleId") Integer articleId, Integer sort);
}
