package com.example.leaf.repositories;

import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.User;
import com.example.leaf.entities.keys.RelationShipKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationShipRepository extends JpaRepository<RelationShip, RelationShipKey> {
    List<RelationShip> findAllByUserFromOrUserToAndStatus(User userFrom, User userTo, String status);
    Page<RelationShip> findAllByUserFromOrUserToAndStatus(User userFrom, User userTo, String status, Pageable pageable);
    List<RelationShip> findAllByUserToAndStatus(User userTo, String status);
}
