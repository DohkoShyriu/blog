package com.dohko.blog.controller;

import com.dohko.blog.dto.ArticleRequestDTO;
import com.dohko.blog.mapper.ArticleMapper;
import com.dohko.blog.model.Article;
import com.dohko.blog.model.Author;
import com.dohko.blog.repository.ArticleRepository;
import com.dohko.blog.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleControllerTest {


    private static String uriTemplate = "/v1/articles";
    private static String firstArticleTitle = "Hey";
    private static String firstArticleContent = "This is my first article";
    private static String secondArticleTitle = "Hello";
    private static String secondArticleContent = "This is my second article";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private Author existingAuthor;
    private Author nonExistingAuthor;
    private Article myFirstArticle;
    private Article mySecondArticle;

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
        authorRepository.deleteAll();

        existingAuthor = Author.builder()
                .surname("Dupont")
                .firstName("Jean")
                .build();
        existingAuthor = authorRepository.save(existingAuthor);

        nonExistingAuthor = Author.builder()
                .id(999L)
                .surname("Nonexistent")
                .firstName("Author")
                .build();

        myFirstArticle = Article.builder()
                .title(firstArticleTitle)
                .content(firstArticleContent)
                .author(existingAuthor)
                .build();
        myFirstArticle = articleRepository.save(myFirstArticle);

        mySecondArticle = Article.builder()
                .title(secondArticleTitle)
                .content(secondArticleContent)
                .author(existingAuthor)
                .build();
        mySecondArticle = articleRepository.save(mySecondArticle);
    }

    @Test
    public void testGetArticles() throws Exception {
        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(firstArticleTitle))
                .andExpect(jsonPath("$[0].content").value(firstArticleContent))
                .andExpect(jsonPath("$[1].title").value(secondArticleTitle))
                .andExpect(jsonPath("$[1].content").value(secondArticleContent));

    }

    @Test
    public void testGetFirstArticle() throws Exception {
        mockMvc.perform(get(uriTemplate + "/" + myFirstArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(firstArticleTitle))
                .andExpect(jsonPath("$.content").value(firstArticleContent))
                .andExpect(jsonPath("$.id").value(myFirstArticle.getId()))
                .andExpect(jsonPath("$.author_id").value(myFirstArticle.getAuthor().getId()));
    }

    @Test
    public void testGetUnknownArticle() throws Exception {
        mockMvc.perform(get(uriTemplate + "/99999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPutUnknownArticle() throws Exception {
        Article myDummyArticle = Article.builder()
                .title(firstArticleTitle + firstArticleTitle)
                .content(firstArticleContent + firstArticleContent)
                .build();

        mockMvc.perform(put(uriTemplate + "/99999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ArticleMapper.mapToNoAuthorDto(myDummyArticle))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPutArticleTryAuthorError() throws Exception {
        Article myDummyArticle = Article.builder()
                .title(firstArticleTitle)
                .content(firstArticleContent)
                .author(existingAuthor)
                .build();

        mockMvc.perform(put(uriTemplate + "/" + myFirstArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ArticleMapper.mapToDto(myDummyArticle).toString())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutArticle() throws Exception {
        Article myDummyArticle = Article.builder()
                .title(firstArticleTitle + firstArticleTitle)
                .content(firstArticleContent + firstArticleContent)
                .build();

        mockMvc.perform(put(uriTemplate + "/" + myFirstArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ArticleMapper.mapToNoAuthorDto(myDummyArticle))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(firstArticleTitle + firstArticleTitle))
                .andExpect(jsonPath("$.content").value(firstArticleContent + firstArticleContent))
                .andExpect(jsonPath("$.author_id").value(myFirstArticle.getAuthor().getId()))
                .andExpect(jsonPath("$.id").value(myFirstArticle.getId()));
    }

    @Test
    public void testPostArticle() throws Exception {
        String title = "Article 3";
        String content = "Content 3";

        ArticleRequestDTO myDummyArticle = ArticleRequestDTO.builder()
                .title(title)
                .content(content)
                .author_id(existingAuthor.getId())
                .build();

        mockMvc.perform(post(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myDummyArticle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.author_id").isNumber())
                .andExpect(jsonPath("$.id").isNumber());
    }


}
