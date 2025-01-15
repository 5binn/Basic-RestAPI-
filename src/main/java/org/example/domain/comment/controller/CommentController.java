package org.example.domain.comment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.article.dto.ArticleResponseDto;
import org.example.domain.article.entity.Article;
import org.example.domain.article.service.ArticleService;
import org.example.domain.comment.dto.CommentRequestDto;
import org.example.domain.comment.dto.CommentResponseDto;
import org.example.domain.comment.service.CommentService;
import org.example.domain.member.entity.Member;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommonHandler commonHandler;

    private <T> ResponseEntity<ResultData<T>> notFoundArticle(Long articleId) {
        Article article = articleService.getArticleEntityById(articleId);
        if (article == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResultData.of(ResultCode.F_04, "해당 데이터 없음"));
        return null;
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ResultData<List<CommentResponseDto>>> getCommentsByArticleId(@PathVariable("articleId") Long id) {
        //NOT_FOUND 검증
        ResponseEntity<ResultData<List<CommentResponseDto>>> notFound = notFoundArticle(id);
        if (notFound != null) return notFound;
        List<CommentResponseDto> data = commentService.getCommentsByArticleId(id);
        return ResponseEntity.ok(ResultData.of(ResultCode.S_05, data.isEmpty() ? "데이터 없음" : "전체 댓글 조회", data));
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<ResultData<CommentResponseDto>> registerComment(
            @PathVariable("articleId") Long id,
            @Valid @RequestBody CommentRequestDto.CommentRq createRq,
            BindingResult bindingResult, HttpServletRequest request
    ) {
        //공백 검증
        ResponseEntity<ResultData<CommentResponseDto>> blankErrors = commonHandler.handleBlankErrors(bindingResult);
        if (blankErrors != null) return blankErrors;
        //NOT_FOUND 검증
        ResponseEntity<ResultData<CommentResponseDto>> notFound = notFoundArticle(id);
        if (notFound != null) return notFound;
        //등록
        CommentResponseDto data = commentService.registerComment(
                createRq.getContent(),
                memberService.getLoggedInMember(request),
                articleService.getArticleEntityById(id));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultData.of(ResultCode.S_02, "댓글 등록 성공", data));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResultData<CommentResponseDto>> updateComment(
            @PathVariable("commentId") Long id,
            @Valid @RequestBody CommentRequestDto.CommentRq updateRq,
            BindingResult bindingResult, HttpServletRequest request
    ) {
        //공백 검증
        ResponseEntity<ResultData<CommentResponseDto>> blankErrors = commonHandler.handleBlankErrors(bindingResult);
        if (blankErrors != null) return blankErrors;
        //해당 ID의 comment 불러옴
        CommentResponseDto data = commentService.getCommentById(id);
        //NOT_FOUND 검증
        ResponseEntity<ResultData<CommentResponseDto>> notFound = commonHandler.handleNotFound(data);
        if (notFound != null) return notFound;
        //권한 검증
        ResponseEntity<ResultData<CommentResponseDto>> unauthorized = commonHandler.handleUnauthorized(request, data.getAuthor());
        if (unauthorized != null) return unauthorized;
        //수정 수행
        CommentResponseDto updateData = commentService.updateComment(id, updateRq.getContent());
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_03, "댓글 수정 완료", updateData));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResultData<CommentResponseDto>> deleteComment(
            @PathVariable("commentId") Long id, HttpServletRequest request
    ) {
        //해당 ID의 comment 불러옴
        CommentResponseDto data = commentService.getCommentById(id);
        //NOT_FOUND 검증
        ResponseEntity<ResultData<CommentResponseDto>> notFound = commonHandler.handleNotFound(data);
        if (notFound != null) return notFound;
        //권한 검증
        ResponseEntity<ResultData<CommentResponseDto>> unauthorized = commonHandler.handleUnauthorized(request, data.getAuthor());
        if (unauthorized != null) return unauthorized;
        //삭제 수행
        commentService.deleteComment(id);
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_01, "댓글 삭제 성공"));
    }
}
