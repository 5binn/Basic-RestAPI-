package org.example.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ArticleRequestDto {
    @Getter
    @Setter
    public static class ArticleRq {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
}
