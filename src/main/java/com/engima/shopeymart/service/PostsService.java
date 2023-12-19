package com.engima.shopeymart.service;

import com.engima.shopeymart.entity.Posts;
import com.engima.shopeymart.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final RestTemplate restTemplate;
    private final PostsRepository postsRepository;

    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    public ResponseEntity<String> getAllPosts(){
        String apiUrl = BASE_URL;
        return responseMethod(restTemplate.getForEntity(apiUrl, String.class), "Failed to load data");
    }

    public ResponseEntity<String> getById(Long id){
        return responseMethod(restTemplate.getForEntity(BASE_URL + "/" + id, String.class), "Failed to load data");
    }

    public ResponseEntity<String> createPost(Posts posts){
        //mengatur permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //membungkus data permintaan dalam httpEntity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts,headers);
        postsRepository.save(posts);
        //response
        return responseMethod(restTemplate.postForEntity(BASE_URL, requestEntity, String.class), "Failed to create data");

    }

    public ResponseEntity<List<Posts>> getAllDatas(){
        //ngebuat response entity post dengan list yang menampung data getAll base URL
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL, Posts[].class);
        //Ini ngebuat list baru dengan nama externalPosts dengan data dapetin bodynya
        List<Posts> externalPosts= List.of(apiResponse.getBody());

        List<Posts> dbPosts = postsRepository.findAll();

        //combine  result
        dbPosts.addAll(externalPosts);
        //return combine list
        return ResponseEntity.ok(dbPosts);
    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> restTemplate, String message){
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(message);
    }
}
