package com.blogapp.service;

import com.blogapp.dto.PostDTO;
import com.blogapp.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortType);
    PostDTO getPostById(Integer postId);
    List<PostDTO> getAllPostByCategory(Integer categoryId);
    List<PostDTO> getAllPostByUser(Integer userId);
    List<PostDTO> SearchPosts(String keyword);
}
