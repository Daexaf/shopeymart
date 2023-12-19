package com.engima.shopeymart.repository;

import com.engima.shopeymart.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
}
