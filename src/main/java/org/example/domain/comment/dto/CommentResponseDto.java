package org.example.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private Long articleId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getMember().getUsername();
        this.articleId = comment.getArticle().getId();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
