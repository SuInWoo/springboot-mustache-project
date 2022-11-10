package com.mustache.bbs2.domain.dto;

import com.mustache.bbs2.domain.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleDto {
    private String title;
    private String content;

    public ArticleEntity toEntity() {
        return new ArticleEntity(title, content);
    }
}
