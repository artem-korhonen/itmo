package com.notnot.lab4.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notnot.lab4.dto.PointRequest;
import com.notnot.lab4.dto.PointResponse;
import com.notnot.lab4.services.PointService;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping
    public ResponseEntity<PointResponse> addPoint(
            @RequestBody PointRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        PointResponse response = pointService.addPoint(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PointResponse> getAllPoints(Authentication authentication) {
        String username = authentication.getName();
        PointResponse response = pointService.getAllPoints(username);
        return ResponseEntity.ok(response);
    }
}