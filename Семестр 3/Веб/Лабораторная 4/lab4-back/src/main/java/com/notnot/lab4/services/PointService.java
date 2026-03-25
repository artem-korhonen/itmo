package com.notnot.lab4.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notnot.lab4.dto.PointRequest;
import com.notnot.lab4.dto.PointResponse;
import com.notnot.lab4.entities.Point;
import com.notnot.lab4.repositories.PointRepository;

@Service
@Transactional
public class PointService {

    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointResponse addPoint(PointRequest request, String username) {
        long startTime = System.nanoTime();

        int x = request.getX();
        double y = request.getY();
        int r = request.getR();

        boolean hit = checkHit(x, y, r);

        long endTime = System.nanoTime();
        long requestTime = endTime - startTime;

        Point point = new Point(x, y, r, hit, LocalDateTime.now(), requestTime, username);

        pointRepository.save(point);

        PointResponse response = new PointResponse(hit, pointRepository.findAllByUsername(username));

        return response;
    }

    public PointResponse getAllPoints(String username) {
        PointResponse response = new PointResponse(false, pointRepository.findAllByUsername(username));
        return response;
    }

    private boolean checkHit(int x, double dy, int r) {
        double dx = x;
        double dr = r;

        if (dr >= 0) {
            return ((dx >= 0 && dy >= 0 && dx <= dr/2 && dy <= dr)
                || (dx <= 0 && dy >= 0 && (dy <= dx + dr/2)) 
                || (dx <= 0 && dy <= 0 && (dx*dx + dy*dy <= dr*dr)));
        } else {
            return ((dx <= 0 && dy <= 0 && dx >= dr/2 && dy >= dr)
                || (dx >= 0 && dy <= 0 && (dy >= dx + dr/2))
                || (dx >= 0 && dy >= 0 && (dx*dx + dy*dy <= dr*dr)));
        }
    }
}
