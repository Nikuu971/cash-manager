package com.backend.repository;

import com.backend.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApplication, Long>{
    UserApplication findByUsername(String username);
}