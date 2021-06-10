package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void savePostToDBTest(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingPostWithDuplicateTitle(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
        log.info("Created a blog post --> {}", blogPost);

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        log.info("Created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));

    }

    @Test
    void whenPostIsSaved_thenSaveAuthorTest(){


        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        log.info("Created a blog post --> {}", blogPost);


        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Wick");
        author.setEmail("john@mail.com");
        author.setPhoneNumber("090799697896");

        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post After saving --> {}", blogPost);

    }

    @Test
    void findAllPostInTheDbTest(){

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);

    }


    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("Post from database --> {}",savedPost);

//        delete post
        postRepository.deleteById(41);

        Post deletedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(deletedPost).isNull();
    }
    @Test
    @Transactional
    void updateSavedPostTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("Update from database --> {}", savedPost);
        assertThat(savedPost.getTitle()).isEqualTo("Title post 1");

        savedPost.setTitle(("Pentax Post title"));
        postRepository.save(savedPost);

        Post updatePost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatePost).isNotNull();
        assertThat(updatePost.getTitle()).isEqualTo("Pentax Post title");
    }

    @Test
    void updatePostAuthorTest() {
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNull();
        log.info("saved from database --> {}", savedPost);

        Author author = new Author();
        author.setFirstname("Aka");
        author.setLastname("kenny");
        author.setPhoneNumber("0704537354");
        author.setEmail("Aka@gmail.com");
        author.setProfession("programmer");

        savedPost.setAuthor(author);
        postRepository.save(savedPost);

        Post updatePost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatePost).isNotNull();
        assertThat(updatePost.getAuthor()).isNotNull();
        assertThat(updatePost.getAuthor().getLastname()).isEqualTo("kenny");

        log.info("Update from database --> {}", updatePost);

    }

    @Test
    @Rollback(value = false)
    void addCommentToPostTest(){
        Post savedPost = postRepository.findById(43).orElse(null);
        assertThat(savedPost).isNotNull();

//        creat a comment object
        Comment comment1 = new Comment("Billy","Really whats happening");
        Comment comment2 = new Comment("Kenny", "Nice one!");

        savedPost.addComment(comment1, comment2);

//        save the post
        postRepository.save(savedPost);

        Post commentedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(commentedPost).isNotNull();
        assertThat(commentedPost.getComments()).hasSize(2);
        log.info("commented post --> {}", commentedPost);
    }

    @Test
    void findAllPostInDescendingOrderTest(){

        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();
        assertThat(allPosts.get(0).getDateCreated().isAfter((allPosts.get(1).getDateCreated())));
        allPosts.get(0).getDateCreated()
                .isAfter(allPosts.get(1).getDateCreated());

        allPosts.forEach(post -> {
            log.info("Post Date {}", post.getDateCreated());
        });
    }
}