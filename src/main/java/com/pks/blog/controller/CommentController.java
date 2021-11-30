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

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentsByPostId(@PathVariable("postId") Long postId,
                                               @PathVariable("commentId") Long commentId){
        CommentDto commentDto =commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable("postId") Long postId,
                                                         @PathVariable("commentId") Long commentId,
                                                         @RequestBody CommentDto commentDto) {
    CommentDto updatedDto = commentService.updateComment(postId,commentId,commentDto);
    return new ResponseEntity<>(updatedDto,HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String>updateComment(@PathVariable("postId") Long postId,
                                                   @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Delete successful",HttpStatus.OK);
    }
    
    }
