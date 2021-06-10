package com.blogapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class TestDataSource {

    @Value("${test.datasource.url}")
    private String url;
    @Value("${test.datasource.username}")
    private String username;
    @Value("${test.datasource.password}")
    private String password;

}
