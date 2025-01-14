package org.example.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ResponseDto;
import org.example.domain.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


}
