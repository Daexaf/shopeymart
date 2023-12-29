package com.engima.shopeymart.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("/home/user/Batch14/shopeymart/src/main/java/com/engima/shopeymart/file");

    public FileStorageService() {
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e){
            throw new RuntimeException("Cannot create new directory to upload file to storage");
        }
    }

    public String storageFile(MultipartFile file){

        String mimeType = file.getContentType();

        //Check file type just image
        if (mimeType == null || (!mimeType.startsWith("image/"))){
            throw new RuntimeException("Invalid type data, only upload image dude fr");
        }

        try{
            String filename = file.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        }catch (IOException e){
            throw new RuntimeException(("could not store " + file.getOriginalFilename() + " please check again " + e));
        }
    }

    public Resource downloadFile(String nameFile) throws FileNotFoundException {
        try{
            Path targetLocation = this.fileStorageLocation.resolve(nameFile).normalize();
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()){
                return resource;
            }else{
                throw new FileNotFoundException("File Not Found " + nameFile);
            }
        }catch (MalformedURLException e){
            throw new FileNotFoundException("File Not Found " + nameFile);
        }
    }


}
