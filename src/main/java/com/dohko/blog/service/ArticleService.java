package com.dohko.blog.service;

import com.dohko.blog.dto.ArticleDTO;
import com.dohko.blog.dto.ArticleNoAuthorDTO;
import com.dohko.blog.mapper.ArticleMapper;
import com.dohko.blog.model.Article;
import com.dohko.blog.repository.ArticleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AuthorService authorService;

    public Article getArticle(final long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article not found"));
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Optional<ArticleDTO> saveArticle(ArticleDTO articleDTO) {
        Article article = ArticleMapper.mapToEntity(articleDTO, authorService.getAuthor(articleDTO.getAuthor_id()));
        return Optional.of(ArticleMapper.mapToDto(saveArticle(article)));
    }

    public Optional<ArticleDTO> updateArticle(final long id, ArticleNoAuthorDTO article) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isPresent()) {
            Article existingArticle = articleOptional.get();
            existingArticle.setTitle(article.getTitle());
            existingArticle.setContent(article.getContent());
            return Optional.of(ArticleMapper.mapToDto(saveArticle(existingArticle)));
        } else {
            return Optional.empty();
        }
    }

    public void deleteArticle(final long id) {
        articleRepository.deleteById(id);
    }

}
