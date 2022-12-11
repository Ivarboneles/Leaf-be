package com.example.leaf.repositories;

import com.example.leaf.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, String> {
}
