package com.it326.BrickByBrick;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TaskSuggester {

    private static final String API_KEY = "528c40ae6788fa4b3bf7f65e78468ec6";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String LOCATION = "Chicago,US";

    public TaskSuggester() {}

    /**
     * Returns weather condition (e.g., Rain, Clear, Snow) for the specified date.
     */
    public String getWeather(Date date) {
        try {
            LocalDateTime taskTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return getWeatherForDateTime(taskTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unavailable";
        }
    }

    /**
     * Suggests a better time if the current weather at task's date is bad.
     */
    public String makeSuggestion(Task task) {
        try {
            LocalDateTime originalTime = task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String currentWeather = getWeatherForDateTime(originalTime);

            if (isBadWeather(currentWeather)) {
                LocalDateTime betterTime = findNextGoodWeather(originalTime);
                if (betterTime != null) {
                    return "Weather on " + formatDateTime(originalTime) + " is " + currentWeather
                         + ". Suggested new time: " + formatDateTime(betterTime) + ".";
                } else {
                    return "Weather on " + formatDateTime(originalTime) + " is " + currentWeather
                         + ". No better time found in the next 5 days.";
                }
            } else {
                return "Weather on " + formatDateTime(originalTime) + " is " + currentWeather + ". No change needed.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to make a suggestion.";
        }
    }

    // --- Helper Methods ---

    private String getWeatherForDateTime(LocalDateTime target) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "?q=" + LOCATION + "&appid=" + API_KEY + "&units=imperial";

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        for (JsonNode item : root.get("list")) {
            String dtTxt = item.get("dt_txt").asText(); // Format: "2025-04-20 18:00:00"
            LocalDateTime forecastTime = LocalDateTime.parse(dtTxt.replace(" ", "T"));

            if (forecastTime.toLocalDate().equals(target.toLocalDate())) {
                long hoursDiff = Math.abs(Duration.between(forecastTime, target).toHours());
                if (hoursDiff <= 3) {  // 3-hour window match
                    return item.get("weather").get(0).get("main").asText();
                }
            }
        }
        return "Unknown";
    }

    private LocalDateTime findNextGoodWeather(LocalDateTime from) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "?q=" + LOCATION + "&appid=" + API_KEY + "&units=imperial";

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        for (JsonNode item : root.get("list")) {
            String dtTxt = item.get("dt_txt").asText();
            LocalDateTime forecastTime = LocalDateTime.parse(dtTxt.replace(" ", "T"));

            if (forecastTime.isAfter(from)) {
                String weather = item.get("weather").get(0).get("main").asText();
                if (!isBadWeather(weather)) {
                    return forecastTime;
                }
            }
        }
        return null;
    }

    private boolean isBadWeather(String condition) {
        return condition.equalsIgnoreCase("Rain")
            || condition.equalsIgnoreCase("Thunderstorm")
            || condition.equalsIgnoreCase("Snow");
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

