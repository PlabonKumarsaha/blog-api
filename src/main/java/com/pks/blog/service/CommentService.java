package com.pks.blog.service;

import com.pks.blog.dto.CommentDto;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);
}
