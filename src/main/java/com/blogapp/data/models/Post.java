package com.blogapp.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity()
@Table(name = "blog_post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String title;

    @Column(length = 200)
    private String content;

    private String coverImageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    private Author author;

    @CreationTimestamp
    private LocalDate dateCreated;

    @UpdateTimestamp
    private LocalDate dateModified;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Comment> comments;

    public void addComment(Comment...comment){
        if (this.comments == null){
            this.comments = new ArrayList<>();
        }
        this.comments.addAll(Arrays.asList(comment));
    }

}
