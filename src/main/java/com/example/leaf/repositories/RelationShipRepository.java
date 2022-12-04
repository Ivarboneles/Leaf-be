package com.example.leaf.repositories;

import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.keys.RelationShipKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationShipRepository extends JpaRepository<RelationShip, RelationShipKey> {
}
