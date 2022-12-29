package com.example.leaf.repositories;

import com.example.leaf.entities.Notify;
import com.example.leaf.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotifyRepository extends JpaRepository<Notify, String> {
    List<Notify> findAllByUser(User user, Sort sort);
}
