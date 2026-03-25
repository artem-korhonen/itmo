package com.notnot.lab4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notnot.lab4.entities.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByUsername(String username);
}
