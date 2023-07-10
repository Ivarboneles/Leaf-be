package com.example.leaf;

import com.example.leaf.entities.ModelAI;
import com.example.leaf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LeafApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeafApplication.class, args);
    }

}
