package com.pks.blog.service;

import com.pks.blog.dto.PostDTO;

import java.util.List;

public interface PostService {

    public PostDTO createPost(PostDTO postDTO);
    public List<PostDTO> getAllPost();
}
