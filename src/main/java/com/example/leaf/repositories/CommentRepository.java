package com.example.leaf.repositories;

import com.example.leaf.entities.Comment;
import com.example.leaf.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByPostAndStatus(Post post, String status, Sort sort);
    Page<Comment> findAllByPostAndStatus(Post post, String status, Pageable pageable);
}
