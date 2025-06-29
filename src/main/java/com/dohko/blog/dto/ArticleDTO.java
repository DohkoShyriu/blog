package com.dohko.blog.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private Long author_id;

    public Long getId() {
        return id;
    }

}
