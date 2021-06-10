package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;

import java.util.List;

public interface PostService {


    Post savePost(PostDto postDto) throws PostObjectIsNullException;

    List<Post> findAllPost();

    List<Post> findPostInDesOrder();

    Post updatePost(PostDto postDto);

    Post findById(Integer id);

    void deletePostById(Integer id);

    Post addCommentToPost(Integer id, Comment comment);

}