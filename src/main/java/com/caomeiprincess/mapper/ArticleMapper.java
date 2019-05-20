package com.caomeiprincess.mapper;

import com.caomeiprincess.common.config.MyMapper;
import com.caomeiprincess.entity.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends MyMapper<Article> {
    @Select("select * from tb_article where state = '1' order by id desc")
    Page<Article> findByPageForSite();

    @Select("SELECT id FROM tb_article ORDER BY id DESC LIMIT 1")
    Long getLastId();

    @Select("update tb_article set views = (views + 1) where id = #{id}")
    void addViews(Long id);
}
