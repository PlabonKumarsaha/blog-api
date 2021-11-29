package com.pks.blog.service.impl;

import com.pks.blog.dto.PostDTO;
import com.pks.blog.entity.Post;
import com.pks.blog.exceptions.ResourceNotFoundException;
import com.pks.blog.repository.PostRepository;
import com.pks.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        //convert post to entity
        Post post = new Post();
        post = mapToEntity(postDTO);
        Post newPost =postRepository.save(post);

        PostDTO postResponse = new PostDTO();
        postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        Post updatedPost = postRepository.save(post);
        return  mapToDTO(updatedPost);
    }

    @Override
    public void deleteById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        postRepository.delete(post);
    }

    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }
}
