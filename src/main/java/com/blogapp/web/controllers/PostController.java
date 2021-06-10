package com.blogapp.web.controllers;

import com.blogapp.data.models.Post;
import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postServiceImpl;

    @GetMapping("")
    public  String getIndex(Model model){
        List<Post> postList = postServiceImpl.findAllPost();
        model.addAttribute("postList", postList);
        return "index";
    }

    @GetMapping("/create")
     public String getPostForm(Model model){
        model.addAttribute("posts",new PostDto());
        return "create";
    }


    @PostMapping("/save")
    public String savePost(@ModelAttribute @Valid PostDto postDto,
                            BindingResult result, Model model) {
        log.info("Post dto received --> {}", postDto);

        if (result.hasErrors()){
            return "create";
        }


        try {
            postServiceImpl.savePost(postDto);
        } catch (PostObjectIsNullException e) {
            log.info("Exception occured --> {}", e.getMessage());
        } catch (DataIntegrityViolationException dx) {
            log.info("constraint exception occurred --> {}", dx.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "title not accepted, already exists.");

            return "create";
        }
        return "redirect:/posts";
    }
    @ModelAttribute
    public void createPostModel(Model model){
        model.addAttribute("postDto", new PostDto());

    }

    @GetMapping("/info/{postId}")
    public String getPostDDetails(@PathVariable("postId") Integer postId,Model model){
        log.info("Request for a post path --> {}", postId);
        return "post";

    }

}