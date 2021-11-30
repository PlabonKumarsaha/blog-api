package com.pks.blog.controller;

import com.pks.blog.dto.CommentDto;
import com.pks.blog.entity.Comment;
import com.pks.blog.service.impl.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@PathVariable("postId") Long postId,
                                                   @RequestBody CommentDto commentDto){
    return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    //get comments by post id
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto>getCommentsByPostId(@PathVariable("postId") Long postId){
    return commentService.getListCommentIdByPostId(postId);
    }

}
