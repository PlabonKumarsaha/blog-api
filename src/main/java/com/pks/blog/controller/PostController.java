package com.pks.blog.controller;

import com.pks.blog.Utils.AppConstants;
import com.pks.blog.dto.PostDTO;
import com.pks.blog.dto.PostResponse;
import com.pks.blog.entity.Post;
import com.pks.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post
    @PostMapping()
    public ResponseEntity<PostDTO>createPost(@Valid @RequestBody PostDTO postDTO){
    return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //get all post
    @GetMapping()
    public PostResponse getAllPost(@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int  pageNo,
                                   @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int  pageSize,
                                   @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_PAGE_SORT_BY,required = false) String  sortBy,
                                   @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String  sortDirection){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDirection);
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO>getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO>updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") long id){
    PostDTO dtoResponse= postService.updatePost(postDTO,id);
    return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

    //delete methods
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable("id")long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post was successfully deleted",HttpStatus.OK);
    }

}
