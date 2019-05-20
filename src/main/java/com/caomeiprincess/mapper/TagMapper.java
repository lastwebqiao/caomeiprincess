package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.Tags;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper extends MyMapper<Tags> {

    @Select("SELECT * FROM tb_tags t, tb_article a, tb_article_tags ta" +
            " WHERE t.id = ta.tag_id AND a.id = ta.article_id AND a.id = #{id}")
    List<Tags> findTagByArticleId(Long id);
}
