package com.api.miniproject.miniproject.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    S3Object getFile(String keyName);

    String deleteFile(String keyName);

    String uploadFile(String keyName, MultipartFile file) throws IOException;
}