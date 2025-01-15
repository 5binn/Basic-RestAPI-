package org.example.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentRequestDto {
    @Getter
    @Setter
    public static class CommentRq{
        @NotBlank
        private String content;
    }
}
