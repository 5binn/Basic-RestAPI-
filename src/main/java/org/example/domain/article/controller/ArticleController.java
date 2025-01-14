package org.example.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ArticleRequestDto;
import org.example.domain.article.dto.ArticleResponseDto;
import org.example.domain.article.service.ArticleService;
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

    @GetMapping("/")
    public ResponseEntity<ResultData<List<ArticleResponseDto>>> getAllArticles() {
        List<ArticleResponseDto> data = articleService.getAllArticles();
        if (data.isEmpty()) {
            return ResponseEntity.ok(ResultData.of(ResultCode.S_05, "게시글 없음"));
        }
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_05, "전체 게시글 조회", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> getArticleById(@PathVariable("id") Long id) {
        ArticleResponseDto data = articleService.getArticleById(id);
        if (data == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResultData.of(ResultCode.F_04, "해당 게시글 없음"));
        }
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_05, String.format("ID:%d 게시글 조회", id), data));
    }

    @PostMapping("/")
    public ResponseEntity<ResultData<ArticleResponseDto>> registerArticle(
            @RequestBody @Valid ArticleRequestDto.CreateRq request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultData.of(ResultCode.F_06, "공백 입력"));
        }
        ArticleResponseDto data = articleService.registerArticle(request.getTitle(), request.getContent());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultData.of(ResultCode.S_02, "게시글 등록 성공", data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> updateArticle(
            @PathVariable("id") Long id,
            @RequestBody @Valid ArticleRequestDto.CreateRq request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultData.of(ResultCode.F_06, "공백 입력"));
        }
        ArticleResponseDto data = articleService.updateArticle(id, request.getTitle(), request.getContent());
        if (data == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResultData.of(ResultCode.F_04, "해당 게시글 없음"));
        }
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_03, "게시글 수정 성공", data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<ArticleResponseDto>> deleteArticle(@PathVariable("id") Long id) {
        if (articleService.deleteArticle(id)) {
            return ResponseEntity
                    .ok(ResultData.of(ResultCode.S_04, "게시글 삭제 성공"));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResultData.of(ResultCode.F_04, "해당 게시글 없음"));
    }
}
