package org.example.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ArticleResponseDto;
import org.example.domain.article.entity.Article;
import org.example.domain.article.repository.ArticleRepository;
import org.example.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //dto 가 아닌 객체 반환
    public Article getArticleEntityById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> null);
    }

    //게시글 등록
    public ArticleResponseDto registerArticle(String title, String content, Member member) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
        articleRepository.save(article);
        return new ArticleResponseDto(article);
    }

    //게시글 수정
    public ArticleResponseDto updateArticle(Long id, String title, String content) {
        Article updateArticle = getArticleEntityById(id).toBuilder()
                .title(title)
                .content(content)
                .build();
        articleRepository.save(updateArticle);
        return new ArticleResponseDto(updateArticle);
    }

    //게시글 삭제
    public void deleteArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        article.ifPresent(articleRepository::delete);
    }


}
