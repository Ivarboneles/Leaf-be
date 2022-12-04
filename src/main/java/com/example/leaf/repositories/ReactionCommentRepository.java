package com.example.leaf.repositories;

import com.example.leaf.entities.ReactionComment;
import com.example.leaf.entities.keys.ReactionCommentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionCommentRepository extends JpaRepository<ReactionComment, ReactionCommentKey> {
}
