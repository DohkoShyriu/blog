package com.dohko.blog.mapper;

import com.dohko.blog.dto.ArticleDTO;
import com.dohko.blog.model.Article;
import com.dohko.blog.model.Author;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ArticleMapper {

    public static ArticleDTO mapToDto(Article article){
        if (article == null) {
            return null;
        }
        ArticleDTO articleDto = ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author_id(article.getAuthor().getId()).build();
            return articleDto;
    }


    public static Article mapToEntity(ArticleDTO articleDto, Author author){
        if (articleDto == null || author == null) {
            return null;
        }
        Article article =  Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .author(author)
                .build();

        return article;
    }

    public static Iterable<ArticleDTO> mapToDtos(Iterable<Article> articles){
        if (articles == null) {
            return null;
        }
        return StreamSupport.stream(articles.spliterator(), false)
                .map(ArticleMapper::mapToDto).collect(Collectors.toList());
    }
}
