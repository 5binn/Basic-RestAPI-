package org.example.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getMember().getUsername();
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
    }

}
