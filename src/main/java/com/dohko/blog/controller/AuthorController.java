package com.dohko.blog.controller;

import com.dohko.blog.model.Author;
import com.dohko.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService AuthorService;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(AuthorService.saveAuthor(author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") final long id) {
        return ResponseEntity.ok(AuthorService.getAuthor(id));
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthor() {
        return ResponseEntity.ok(AuthorService.getAllAuthors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") final long id) {
        AuthorService.deleteAuthor(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") final long id, @RequestBody Author updatedAuthor) {
        Optional<Author> author = AuthorService.updateAuthor(id, updatedAuthor);
        return (author.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
