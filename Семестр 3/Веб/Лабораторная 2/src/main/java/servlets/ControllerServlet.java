package servlets;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.PointRequest;
import models.PointResponse;
import utils.JsonParser;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private final JsonParser jsonParser = new JsonParser();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            PointRequest[] points = jsonParser.parsePointRequest(requestBody);
            request.setAttribute("points", points);
            request.setAttribute("startTime", Instant.now());

            request.getRequestDispatcher("/areacheck").forward(request, response);

        } catch (JsonProcessingException exception) {
            response.setStatus(400);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Invalid JSON");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            List<PointResponse> history = (List<PointResponse>) session.getAttribute("history");

            String jsonResponse = jsonParser.parsePoints(history);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Ошибка при формировании JSON");
        }
    }
}
