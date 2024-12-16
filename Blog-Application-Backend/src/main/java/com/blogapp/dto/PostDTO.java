package com.blogapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDTO user;
    private CategoryDTO category;
    private List<CommentDTO> comments = new ArrayList<>();
}
