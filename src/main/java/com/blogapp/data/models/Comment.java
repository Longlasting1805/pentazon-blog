package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String commentatorName;

    @Column(nullable = false, length = 150)
    private String content;

    @CreationTimestamp
    private LocalDate dateCreated;

    public Comment (String commentatorName, String content){
        this.content = content;
        this.commentatorName = commentatorName;
    }

    public Comment(){}
}
