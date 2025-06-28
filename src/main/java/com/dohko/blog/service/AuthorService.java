package com.dohko.blog.service;

import com.dohko.blog.model.Author;
import com.dohko.blog.repository.AuthorRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository AuthorRepository;
    public Optional<Author> getAuthor(final long id) {
        return AuthorRepository.findById(id);
    }

    public Iterable<Author> getAllAuthors() {
        return AuthorRepository.findAll();
    }

    public Author saveAuthor(Author Author) {
        return AuthorRepository.save(Author);
    }

    public Optional<Author> updateAuthor(final long id, Author Author) {
        Optional<Author> AuthorOptional = AuthorRepository.findById(id);

        if (AuthorOptional.isPresent()) {
            Author existingAuthor = AuthorOptional.get();
            existingAuthor.setSurname(Author.getSurname());
            existingAuthor.setFirstName(Author.getFirstName());
            return Optional.of(AuthorRepository.save(existingAuthor));
        } else {
            return Optional.empty();
        }
    }

    public void deleteAuthor(final long id) {
        AuthorRepository.deleteById(id);
    }
}
