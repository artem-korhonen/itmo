package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import models.PointRequest;
import models.PointResponse;
import utils.JsonParser;

@WebServlet("/areacheck")
public class AreaCheckServlet extends HttpServlet {
    private final JsonParser jsonParser = new JsonParser();

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PointRequest[] points = (PointRequest[]) request.getAttribute("points");
        Instant startTime = (Instant) request.getAttribute("startTime");

        if (points == null || startTime == null) {
            response.setStatus(400);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Bad request");
            return;
        }

        List<PointResponse> pointsResponse = new ArrayList<>();

        for (PointRequest point : points) {
            int x = point.getX();
            BigDecimal y = point.getY();
            int r = point.getR();

            boolean result = checkArea(BigDecimal.valueOf(x), y, BigDecimal.valueOf(r));
            Instant endTime = Instant.now();

            PointResponse pointResponse = new PointResponse(
                    x, y, r, result, endTime.plus(Duration.ofHours(3)), Duration.between(startTime, endTime)
            );

            pointsResponse.add(pointResponse);
        }

        try {
            HttpSession session = request.getSession();
            List<PointResponse> history = (List<PointResponse>) session.getAttribute("history");
            if (history == null) {
                history = new ArrayList<>();
                session.setAttribute("history", history);
            }
            history.addAll(pointsResponse);

            String jsonResponse = jsonParser.parsePoints(pointsResponse);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Ошибка при формировании JSON");
        }
    }

    private boolean checkArea(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal halfR = r.divide(BigDecimal.valueOf(2));

        boolean rect = x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0
                && x.compareTo(r) <= 0 && y.compareTo(r) <= 0;

        boolean circle = x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0
                && x.pow(2).add(y.pow(2)).compareTo(r.pow(2)) <= 0;

        boolean triangle = x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0
                && y.compareTo(x.negate().subtract(halfR)) >= 0;

        return rect || circle || triangle;
    }
}
