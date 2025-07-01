package com.dohko.blog.mapper;

import com.dohko.blog.dto.ArticleNoAuthorDTO;
import com.dohko.blog.dto.ArticleResponseDTO;
import com.dohko.blog.model.Article;
import com.dohko.blog.model.Author;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMapper {

    public static ArticleResponseDTO mapToDto(Article article) {
        if (article == null) {
            return null;
        }
        ArticleResponseDTO articleResponseDto = ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author_id(article.getAuthor().getId())
                .build();
        return articleResponseDto;
    }

    public static ArticleNoAuthorDTO mapToNoAuthorDto(Article article) {
        if (article == null) {
            return null;
        }
        ArticleNoAuthorDTO articleNoAuthorDto = ArticleNoAuthorDTO.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .build();
        return articleNoAuthorDto;
    }


    public static Article mapToEntity(ArticleResponseDTO articleResponseDto, Author author) {
        if (articleResponseDto == null || author == null) {
            return null;
        }
        Article article = Article.builder()
                .id(articleResponseDto.getId())
                .title(articleResponseDto.getTitle())
                .content(articleResponseDto.getContent())
                .author(author)
                .build();

        return article;
    }

    public static Article mapNoAuthorToEntity(ArticleNoAuthorDTO articleDto) {
        if (articleDto == null) {
            return null;
        }
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .build();

        return article;
    }

    public static List<ArticleResponseDTO> mapToDtos(List<Article> articles) {
        if (articles == null) {
            return null;
        }
        return articles.stream()
                .map(ArticleMapper::mapToDto).collect(Collectors.toList());
    }
}
