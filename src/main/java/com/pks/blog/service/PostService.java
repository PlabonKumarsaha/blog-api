package com.pks.blog.service;

import com.pks.blog.dto.PostDTO;
import com.pks.blog.dto.PostResponse;

import java.util.List;

public interface PostService {

    public PostDTO createPost(PostDTO postDTO);
    public PostResponse getAllPost(int pageNo, int pageSize);
    public PostDTO getPostById(long id);
    public PostDTO updatePost(PostDTO postDTO, long id);
    public void deleteById(long id);
}
