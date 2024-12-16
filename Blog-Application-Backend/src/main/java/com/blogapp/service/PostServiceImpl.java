package com.blogapp.service;

import com.blogapp.dto.PostDTO;
import com.blogapp.dto.PostResponse;
import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.repository.CategoryRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User ", "User Id ", userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post createdPost = this.postRepository.save(post);

        return this.modelMapper.map(createdPost, PostDTO.class);

    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post ", "Post Id ", postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post ", "Post Id ", postId));
        this.postRepository.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortType) {
        Sort sort = (sortType.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = this.postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDTO> postDTOS = posts.stream().
                map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFoundException("Post ", "PostId ", postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));

        List<Post> posts = this.postRepository.findByCategory(category);

        List<PostDTO> postDTOS = posts.stream().map((post)-> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {
        User user = this.userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User ", "User Id", userId));
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDTO> postDTOS = posts.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).
                collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> SearchPosts(String keyword) {
        List<Post> posts = this.postRepository.findByTitleContaining(keyword);
        List<PostDTO> postDTOS = posts.stream().map((post)-> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOS;
    }
}
