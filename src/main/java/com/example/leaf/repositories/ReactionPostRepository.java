package com.example.leaf.repositories;

import com.example.leaf.entities.ReactionPost;
import com.example.leaf.entities.keys.ReactionPostKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionPostRepository extends JpaRepository<ReactionPost, ReactionPostKey> {
}
