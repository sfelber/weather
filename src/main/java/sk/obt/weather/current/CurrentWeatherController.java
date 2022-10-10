package sk.obt.weather.current;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sk.obt.weather.provider.OpenWeatherMapService;

/**
 * Handle request to get current weather in city.
 * 
 * @author sfelber
 * @since 08.10.2022
 */
@RestController
public class CurrentWeatherController {

	private final OpenWeatherMapService weatherService;
	
	public CurrentWeatherController(OpenWeatherMapService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/current-weather/{city}")
	CurrentWeather getCurrentWeather(@PathVariable String city) {
		CurrentWeather currentWeather = CurrentWeather.UNKNOWN;
		
		if (isCityValid(city)) {
			try {
				currentWeather = weatherService.getCurrentWeather(city);
			} catch (Exception e) {
				// just catch it for now
			}
		}
		
		return currentWeather; 
	}
	
	/**
	 * Check if provided city name is potentially invalid by checking if it
	 * <ul>
	 * 	<li>is {@code null} or empty string</li>
	 * 	<li>contains letters</li>
	 * 	<li>contains spaces</li>
	 * </ul>
	 * 
	 * @param city		to be checked.
	 * 
	 * @return {@code true} when input city is 
	 */
	private boolean isCityValid(final String city) {
		
		if (city == null || city.isEmpty()) {
			return false;
		}
		
		return city.chars().allMatch(c -> Character.isLetter(c) || Character.isWhitespace(c));
	}
}
