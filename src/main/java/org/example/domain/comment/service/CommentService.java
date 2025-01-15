package org.example.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.article.entity.Article;
import org.example.domain.comment.dto.CommentResponseDto;
import org.example.domain.comment.entity.Comment;
import org.example.domain.comment.repository.CommentRepository;
import org.example.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getCommentsByArticleId(Long id) {
        List<Comment> commentList = commentRepository.findByArticleId(id);
        return commentList.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public CommentResponseDto getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment
                .map(CommentResponseDto::new)
                .orElse(null);
    }

    public Comment getCommentEntityById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 댓글 없음")));
    }

    public CommentResponseDto registerComment(String content, Member member, Article article) {
        Comment comment = Comment.builder()
                .content(content)
                .member(member)
                .article(article)
                .build();
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long id, String content) {
        Comment updateComment = getCommentEntityById(id).toBuilder()
                .content(content)
                .build();
        commentRepository.save(updateComment);
        return new CommentResponseDto(updateComment);
    }

    public void deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        comment.ifPresent(commentRepository::delete);
    }
}
