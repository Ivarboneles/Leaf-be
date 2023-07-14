package com.example.leaf.repositories;

import com.example.leaf.entities.Story;
import com.example.leaf.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, String> {

    List<Story> findAllByUserAndStatus(User user, String status);

    Page<Story> findAllByUserAndStatus(User user, String status, Pageable pageable);
}
