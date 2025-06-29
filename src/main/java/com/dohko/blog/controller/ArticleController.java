package com.dohko.blog.controller;

import com.dohko.blog.dto.ArticleDTO;
import com.dohko.blog.mapper.ArticleMapper;
import com.dohko.blog.model.Article;
import com.dohko.blog.service.ArticleService;
import com.dohko.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.BiFunction;

@RestController
@RequestMapping("/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO article) {
        ArticleDTO articleResponse = ArticleMapper.mapToDto(
                articleService.saveArticle(ArticleMapper.mapToEntity(article,
                        authorService.getAuthor(article.getAuthor_id()))));
        if (articleResponse != null) {
            return ResponseEntity.ok(articleResponse);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO>  getArticleById(@PathVariable("id") final long id) {
        ArticleDTO articleResponse = ArticleMapper.mapToDto(articleService.getArticle(id));
        if (articleResponse != null) {
            return ResponseEntity.ok(articleResponse);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Iterable<ArticleDTO>> getArticles() {
        Iterable<ArticleDTO> articleResponse = ArticleMapper.mapToDtos(articleService.getAllArticles());
        if (articleResponse != null) {
            return ResponseEntity.ok(articleResponse);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") final long id) {
        articleService.deleteArticle(id);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") final long id, @RequestBody Article updatedArticle) {
        Optional<Article> article = articleService.updateArticle(id, updatedArticle);
        return (article.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

 */
}
