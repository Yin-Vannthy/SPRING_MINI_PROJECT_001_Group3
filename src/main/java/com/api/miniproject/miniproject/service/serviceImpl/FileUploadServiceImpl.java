package com.api.miniproject.miniproject.service.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.api.miniproject.miniproject.configuration.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;

    public FileUploadServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public S3Object getFile(String keyName) {
        try {
            S3Object s3Object = s3client.getObject(bucketName, keyName);
            if (s3Object != null) {
                return s3Object;
            } else {
                throw new CustomNotFoundException("No file found with key: " + keyName);
            }
        } catch (AmazonS3Exception e) {
            return null;
        }
    }

    @Override
    public String deleteFile(String keyName) {
        try {
            S3Object s3Object = s3client.getObject(bucketName, keyName);
            if (s3Object != null) {
                s3client.deleteObject(bucketName, keyName);
                return keyName;
            }else{
                throw new CustomNotFoundException("No file found with key: " + keyName);
            }
        } catch (AmazonS3Exception e) {
            return null;
        }
    }

    @Override
    public String uploadFile(String keyName, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(keyName);

        try {
            PutObjectResult putObjectResult = s3client.putObject(bucketName, fileName, file.getInputStream(), null);
            if (putObjectResult != null) {
                return generateFileUrl(fileName);
            } else {
                throw new IOException("File upload failed, result is null");
            }
        } catch (AmazonS3Exception e) {
            throw new IOException("Error occurred while uploading file to S3", e);
        }
    }

    private String generateFileUrl(String keyName) {
        return endpointUrl + "/" + keyName;
    }
}