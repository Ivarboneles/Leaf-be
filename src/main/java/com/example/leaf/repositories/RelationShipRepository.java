package com.example.leaf.repositories;

import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.User;
import com.example.leaf.entities.keys.RelationShipKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationShipRepository extends JpaRepository<RelationShip, RelationShipKey> {
    List<RelationShip> findAllByUserFromOrUserTo(User userFrom, User userTo);
    List<RelationShip> findAllByUserToAndStatus(User userTo, String status);
}
