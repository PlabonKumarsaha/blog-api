package com.pks.blog.service.impl;

import com.pks.blog.dto.PostDTO;
import com.pks.blog.dto.PostResponse;
import com.pks.blog.entity.Post;
import com.pks.blog.exceptions.ResourceNotFoundException;
import com.pks.blog.repository.PostRepository;
import com.pks.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
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
    public PostResponse getAllPost(int pageNo, int pageSize,String  sortBy,String  sortDirection) {

        //extracting sort direction
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //create pageable object
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content of page object
        List<Post>postList = posts.getContent();

        List<PostDTO>content= postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setPageNo(posts.getNumber());


        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",String.valueOf(id)));
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
        PostDTO postDTO = modelMapper.map(post,PostDTO.class);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }
    private Post mapToEntity(PostDTO postDTO){
        Post post = modelMapper.map(postDTO,Post.class);
//        Post post = new Post();
//        post.setId(postDTO.getId());
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }
}
