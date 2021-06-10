package com.blogapp.service.post.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface CloudStorageService {


    public Map<?,?> uploadImage(File file, Map<?, ?> imageProperties) throws IOException;
    public Map<?,?> uploadImage(MultipartFile file, Map<?,?> imageProperties) throws IOException;

}
