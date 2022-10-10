package sk.obt.weather.provider;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate	;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.obt.weather.current.CurrentWeather;

/**
 * Weather service that will send request to the external weather API - Open Weather Map.
 * 
 * @author sfelber
 * @since 08.10.2022
 */
@Service
public class OpenWeatherMapService {

	private static final String SERVICE_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&lang=sk";
	
	@Value("${weather.api.openweathermap.apikey}")
	private String apiKey;
	
	private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public OpenWeatherMapService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }
    
    
    /**
     * Method calls external weather service Open Weather Map to get data about current weather in the requested city.
     * 
     * @param city		the city for which we want to receive current weather data
     * 
     * @return {@link CurrentWeather} object with  current weather data. 
     * 	<p>In case of invalid city name or some error {@link CurrentWeather#UNKNOWN} is returned.
     */
    public CurrentWeather getCurrentWeather(String city) {
    	URI url = new UriTemplate(SERVICE_URL).expand(city, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new CurrentWeather(root.path("weather").get(0).path("main").asText(),
                    Double.valueOf(root.path("main").path("temp").asDouble()),
                    Double.valueOf(root.path("wind").path("speed").asDouble()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}

