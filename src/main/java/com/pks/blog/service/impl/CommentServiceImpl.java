package com.pks.blog.service.impl;

import com.pks.blog.dto.CommentDto;
import com.pks.blog.entity.Comment;
import com.pks.blog.entity.Post;
import com.pks.blog.exceptions.BlogAPIException;
import com.pks.blog.exceptions.ResourceNotFoundException;
import com.pks.blog.repository.CommentRepository;
import com.pks.blog.repository.PostRepository;
import com.pks.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("comment","id",String.valueOf(postId)));
        comment.setPost(post);

        //save comment to db
        Comment newComment = commentRepository.save(comment);

        return mapToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getListCommentIdByPostId(long postId) {
        List<Comment>comments = commentRepository.findByPostId(postId);
        //converting to dto
        return comments.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("comment","id",String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment","id",String.valueOf(commentId)));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comments not from this post");
        }

        return mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, Long commentId, CommentDto commentDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("comment","id",String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment","id",String.valueOf(commentId)));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comments not from this post");
        }
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);

        return mapToCommentDto(updatedComment);
    }

    private CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        return comment;
    }
}
