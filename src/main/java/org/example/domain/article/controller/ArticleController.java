package org.example.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.article.service.ArticleService;
import org.example.global.resultData.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/")
    public ResultData getAllArticles() {

    }
}
