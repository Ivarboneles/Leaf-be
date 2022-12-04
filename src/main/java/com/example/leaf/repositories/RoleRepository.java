package com.example.leaf.repositories;

import com.example.leaf.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findRoleByName(String id);
}
