package org.example.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
    }
}
