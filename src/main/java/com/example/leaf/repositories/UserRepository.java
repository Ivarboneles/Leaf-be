package com.example.leaf.repositories;

import com.example.leaf.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserByVerificationCode(String verificationCode);

    Optional<User> findUserByUsername(String username);
}
