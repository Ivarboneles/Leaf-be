package com.example.leaf.repositories;

import com.example.leaf.entities.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotifyRepository extends JpaRepository<Notify, UUID> {
}
