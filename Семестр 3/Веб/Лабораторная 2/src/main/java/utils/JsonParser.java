package utils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.PointRequest;
import models.PointResponse;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParser {
    private final ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public PointRequest[] parsePointRequest(String request) throws JsonProcessingException, JsonMappingException {
        return objectMapper.readValue(request, PointRequest[].class);
    }

    public String parseHtmlAndPointsResponse(String tableHtml, List<PointResponse> pointsResponse) throws JsonProcessingException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("html", tableHtml);
        responseData.put("points", pointsResponse);

        return objectMapper.writeValueAsString(responseData);
    }

    public String parsePoints(List<PointResponse> pointsList) throws JsonProcessingException {
        Map<String, Object> pointsMap = new HashMap<>();
        pointsMap.put("points", pointsList);
        return objectMapper.writeValueAsString(pointsMap);
    }
}
