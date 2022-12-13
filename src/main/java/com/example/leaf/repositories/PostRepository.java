package com.example.leaf.repositories;

import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByUserAndStatus(User user, String status);
}
