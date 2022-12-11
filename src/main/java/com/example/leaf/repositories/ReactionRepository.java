package com.example.leaf.repositories;

import com.example.leaf.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, String> {
}
