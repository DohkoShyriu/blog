package com.dohko.blog.controller;

import com.dohko.blog.model.Article;
import com.dohko.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @GetMapping("/{id}")
    public Optional<Article> getArticle(@PathVariable("id") final long id) {
        return articleService.getArticle(id);
    }

    @GetMapping
    public Iterable<Article> getArticles() {
        return articleService.getAllArticles();
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") final long id) {
        articleService.deleteArticle(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") final long id, @RequestBody Article updatedArticle) {
        Optional<Article> article = articleService.updateArticle(id, updatedArticle);
        return (article.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
