package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.service.post.cloud.CloudStorageService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    CloudStorageService cloudStorageService;

    @Override
    public Post savePost(PostDto postDto) throws PostObjectIsNullException {
        if (postDto == null) {
            throw new PostObjectIsNullException("Post cannot be null");
        }

        Post post = new Post();

        if (postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()) {
            try {
                Map<?, ?> uploadResult = cloudStorageService.uploadImage(postDto.getImageFile(), ObjectUtils.asMap(
                        "public_id", "blogapp/" + extractFileName(postDto.getImageFile().getOriginalFilename()),
                        "overwrite", true));
                post.setCoverImageUrl(String.valueOf(uploadResult.get("url")));
                log.info("Image url --> {}", uploadResult.get("url"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        log.info("log data before saving --> {}", post);


//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map(postDto,post);
//        log.info("Post object after mapping --> {}", post);

        try {
            return postRepository.save(post);
        } catch (DataIntegrityViolationException ex) {
            log.info("Exception occurred --> {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findPostInDesOrder() {
        return postRepository.findByOrderByDateCreatedDesc();
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findById(Integer id) {
        return null;
    }

    @Override
    public void deletePostById(Integer id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment) {
        return null;
    }

    private String extractFileName(String fileName) {
        return fileName.split("\\.")[0];
    }
}