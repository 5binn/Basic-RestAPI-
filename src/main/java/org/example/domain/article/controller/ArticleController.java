package org.example.domain.article.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ArticleRequestDto;
import org.example.domain.article.dto.ArticleResponseDto;
import org.example.domain.article.service.ArticleService;
import org.example.domain.member.service.MemberService;
import org.example.global.handler.CommonHandler;
import org.example.global.resultData.ResultCode;
import org.example.global.resultData.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommonHandler commonHandler;

    @GetMapping("/")
    public ResponseEntity<ResultData<List<ArticleResponseDto>>> getAllArticles() {
        List<ArticleResponseDto> data = articleService.getAllArticles();
        return ResponseEntity.ok(ResultData.of(ResultCode.S_05, data.isEmpty() ? "데이터 없음" : "전체 게시글 조회", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> getArticleById(@PathVariable("id") Long id) {
        //해당 ID의 article 불러옴
        ArticleResponseDto data = articleService.getArticleById(id);
        //NOT_FOUND 검증
        ResponseEntity<ResultData<ArticleResponseDto>> notFoundResponse = commonHandler.handleNotFound(data);
        if (notFoundResponse != null) return notFoundResponse;

        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_05, String.format("ID:%d 게시글 조회", id), data));
    }

    @PostMapping("/")
    public ResponseEntity<ResultData<ArticleResponseDto>> registerArticle(
            @RequestBody @Valid ArticleRequestDto.ArticleRq createRq,
            BindingResult bindingResult,
            HttpServletRequest request) {
        //공백 검증
        ResponseEntity<ResultData<ArticleResponseDto>> blankErrors = commonHandler.handleBlankErrors(bindingResult);
        if (blankErrors != null) return blankErrors;
        //등록
        ArticleResponseDto data = articleService.registerArticle(
                createRq.getTitle(),
                createRq.getContent(),
                memberService.getLoggedInMember(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultData.of(ResultCode.S_02, "게시글 등록 성공", data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> updateArticle(
            @PathVariable("id") Long id,
            @RequestBody @Valid ArticleRequestDto.ArticleRq updateRq,
            BindingResult bindingResult, HttpServletRequest request) {
        //공백 검증
        ResponseEntity<ResultData<ArticleResponseDto>> blankErrors = commonHandler.handleBlankErrors(bindingResult);
        if (blankErrors != null) return blankErrors;
        //해당 ID의 article 불러옴
        ArticleResponseDto data = articleService.getArticleById(id);
        //NOT_FOUND 검증
        ResponseEntity<ResultData<ArticleResponseDto>> notFound = commonHandler.handleNotFound(data);
        if (notFound != null) return notFound;
        //권한 검증
        ResponseEntity<ResultData<ArticleResponseDto>> unauthorized = commonHandler.handleUnauthorized(request, data.getAuthor());
        if (unauthorized != null) return unauthorized;
        //수정 수행
        ArticleResponseDto updateData = articleService.updateArticle(id, updateRq.getTitle(), updateRq.getContent());
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_03, "게시글 수정 성공", updateData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> deleteArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        //해당 ID의 article 불러옴
        ArticleResponseDto data = articleService.getArticleById(id);
        //NOT_FOUND 검증
        ResponseEntity<ResultData<ArticleResponseDto>> notFound = commonHandler.handleNotFound(data);
        if (notFound != null) return notFound;
        //권한 검증
        ResponseEntity<ResultData<ArticleResponseDto>> unauthorized = commonHandler.handleUnauthorized(request, data.getAuthor());
        if (unauthorized != null) return unauthorized;
        //삭제 수행
        articleService.deleteArticle(id);
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_01, "게시글 삭제 성공"));
    }
}
