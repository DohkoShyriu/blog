package com.dohko.blog.service;

import com.dohko.blog.model.Article;
import com.dohko.blog.repository.ArticleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    public Optional<Article> getArticle(final long id) {
        return articleRepository.findById(id);
    }

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> updateArticle(final long id, Article article) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isPresent()) {
            Article existingArticle = articleOptional.get();
            existingArticle.setTitle(article.getTitle());
            existingArticle.setContent(article.getContent());
            existingArticle.setAuthor(article.getAuthor());
            return Optional.of(articleRepository.save(existingArticle));
        } else {
            return Optional.empty();
        }
    }

    public void deleteArticle(final long id) {
        articleRepository.deleteById(id);
    }
}
