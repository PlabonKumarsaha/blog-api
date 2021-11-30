package com.pks.blog.service;

import com.pks.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getListCommentIdByPostId(long postId);
    CommentDto getCommentById(long postId,Long commentId);
    CommentDto updateComment(long postId,Long commentId,CommentDto commentDto);
}
