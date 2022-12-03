package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.comments.Comment;
import com.buzas.springstorehomework.entities.comments.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;

    public List<Comment> showCommentByProductId(Long id) {
        return commentRepo.findAllByProductId(id);
    }

    public void addComment(String text, String username, Long productId) {
        Date date = new Date();
        commentRepo.addNewComment(date, text, username, productId);
    }

    public void deleteCommentById(Long id) {
        commentRepo.deleteCommentById(id);
    }
}
