package com.blogapp.controller;

import com.blogapp.dto.ApiResponse;
import com.blogapp.dto.CommentDTO;
import com.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController  {

    @Autowired
    private CommentService commentService;

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment, @PathVariable Integer postId){
        CommentDTO savedComment = this.commentService.createComment(comment, postId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments")
    public ResponseEntity<ApiResponse> deleteComment(Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment is deleted", true), HttpStatus.OK);
    }
}
