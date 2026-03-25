package com.notnot.lab4.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notnot.lab4.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
