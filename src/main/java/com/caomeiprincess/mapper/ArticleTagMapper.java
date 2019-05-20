package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.ArticleTags;
import com.caomeiprincess.entity.Tags;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleTagMapper extends MyMapper<ArticleTags> {

    @Select("SELECT t.name FROM tb_article_tags at,tb_tags t WHERE at.article_id = #{articleId} AND t.id = at.tag_id")
    List<Tags> findByArticleId(Long articleId);
}
