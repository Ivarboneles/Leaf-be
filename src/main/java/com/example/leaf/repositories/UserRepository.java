package com.example.leaf.repositories;

import com.example.leaf.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserByVerificationCode(String verificationCode);

    Optional<User> findUserByUsername(String username);

    @Query(nativeQuery = true, value ="SELECT * FROM user WHERE name like '%' :name '%' and role_id = 'CUSTOMER'")
    List<User> searchByName(@Param("name") String text);
}
