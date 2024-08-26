package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.service.FileUploadService;
import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/files-upload")
@AllArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping(path = "/uploadFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = fileUploadService.uploadFile(file.getOriginalFilename(), file);
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            fileNames.add(fileName);
        }
        return ResponseEntity.ok(APIResponseUtil.apiResponse(fileNames, HttpStatus.CREATED));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(fileUploadService.getFile(fileName).getObjectContent()));
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable String fileName) {
        var s3Object = fileUploadService.getFile(fileName);

        var content = s3Object.getObjectContent();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(content));
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        fileUploadService.deleteFile(filename),
                        HttpStatus.OK
                )
        );
    }
}

