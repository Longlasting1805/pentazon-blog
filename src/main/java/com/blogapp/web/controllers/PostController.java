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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
    public ModelAndView savePost(PostDto postDto) throws PostObjectIsNullException {

        log.info("Post dto received --> {}", postDto);
        postServiceImpl.savePost(postDto);
        ModelMap model = new ModelMap();
        model.addAttribute("message", "next page");
        return new ModelAndView(
                new RedirectView("/posts", true),
                model
        );
    }

    @GetMapping("/fullPost/{postId}")
    public String getPostDDetails(@PathVariable("postId") Integer postId,Model model){
        log.info("Request for a post path --> {}", postId);
        Post post=postServiceImpl.findById(postId);
        model.addAttribute("post",post);
        return "post";

    }

}
