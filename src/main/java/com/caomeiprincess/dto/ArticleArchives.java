package com.caomeiprincess.dto;

import com.caomeiprincess.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装Article表按时间归档的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleArchives implements Serializable {

    private String date;
    private List<Article> articles;
}
