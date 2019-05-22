package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.Article;
import com.caomeiprincess.entity.ArticleTags;
import com.caomeiprincess.entity.Tags;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleTagMapper extends MyMapper<ArticleTags> {

    @Select("SELECT t.name FROM tb_article_tags at,tb_tags t WHERE at.article_id = #{articleId} AND t.id = at.tag_id")
    List<Tags> findByArticleId(Long articleId);

    @Select(" SELECT a.id, a.category, a.publish_time, a.title, a.views FROM tb_article a,tb_category c,tb_article_category ac " +
            "WHERE c.name = #{name} AND c.id = ac.category_id AND a.id = ac.article_id;")
    List<Article> findByTagName(String name);
}
