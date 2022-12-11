package com.example.leaf.repositories;

import com.example.leaf.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
