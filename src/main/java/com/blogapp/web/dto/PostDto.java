package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotBlank(message = "Title cannot be null")
    private String title;

    @NotBlank(message = "content cannot be empty")
    private String content;

    private MultipartFile imageFile;
}
