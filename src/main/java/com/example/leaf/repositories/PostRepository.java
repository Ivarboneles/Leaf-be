package com.example.leaf.repositories;

import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByUserAndStatus(User user, String status, Sort sort);
    List<Post> findAllByUser(User user, Sort sort);

    Page<Post> findAllByStatus(String status, Pageable pageable);
}
