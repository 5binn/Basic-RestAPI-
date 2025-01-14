package org.example.domain.article.service;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ArticleResponseDto;
import org.example.domain.article.entity.Article;
import org.example.domain.article.repository.ArticleRepository;
import org.example.domain.member.dto.MemberResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    //전체 게시글 조회, 없으면 빈 리스트 반환
    public List<ArticleResponseDto> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream()
                .map(ArticleResponseDto::new)
                .toList();
    }

    //해당 id의 단일 게시글 조회, 없으면 null 반환
    public ArticleResponseDto getArticleById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article
                .map(ArticleResponseDto::new)
                .orElse(null);
    }
    //게시글 등록
    public ArticleResponseDto registerArticle(String title, String content) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();
        articleRepository.save(article);
        return new ArticleResponseDto(article);
    }
    //게시글 수정, 해당 게시글 없으면 null 반환
    public ArticleResponseDto updateArticle(Long id, String title, String content) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            return null;
        }
        Article updateArticle = article.get().toBuilder()
                .title(title)
                .content(content)
                .build();
        articleRepository.save(updateArticle);
        return new ArticleResponseDto(updateArticle);
    }
    //게시글 삭제, 해당 게시글 없으면 false 반환
    public boolean deleteArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.delete(article.get());
            return true;
        }
        return false;
    }
}
