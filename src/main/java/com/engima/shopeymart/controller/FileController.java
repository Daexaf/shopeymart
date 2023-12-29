package com.engima.shopeymart.controller;

import com.engima.shopeymart.entity.FileStorage;
import com.engima.shopeymart.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileStorage> uploadFile(@RequestPart(name = "file") MultipartFile file){
        String result = fileStorageService.storageFile(file);
//        return fileStorageService.storageFile(file);
        return ResponseEntity.ok(FileStorage.builder()
                        .fileName(result)
                        .dateTime(LocalDateTime.now())
                .build());
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFIle(@PathVariable String fileName){
        Resource resource;
        try{
            resource = fileStorageService.downloadFile(fileName);
        } catch (FileNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
//                        .fileName(resource.getFilename())
//                        .dateTime(LocalDateTime.now())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment ; fileName=\"" + resource.getFilename() + "\"")
        .body(resource);
    }
}
