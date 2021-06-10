package com.blogapp;

import com.blogapp.service.post.util.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BlogAppApplication {

	@Autowired
	CloudinaryConfig cloudinaryConfig;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	@Bean
	public Cloudinary getCloudinary(){
	  return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudinaryConfig.getCloudName(),
				"api_key", cloudinaryConfig.getApiKey(),
				"api_secret", cloudinaryConfig.getApiSecret()));


	}


}
