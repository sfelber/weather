package sk.obt.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds data about current weather.
 * 
 * @author sfelber
 * @since 08.10.2022
 */
public class CurrentWeather {

	/**
	 * Constant to be used when it is not possible to provide current weather data, caused by:
	 * <ul>
	 *	<li>invalid request</li>
	 *  <li>invalid data</li>
	 * 	<li>external weather service down</li>
	 *  <li>etc.</li>
	 * </ul>
	 */
	public static final CurrentWeather UNKNOWN = new CurrentWeather(null, null, 0.0);
	
	//
	// Fields
	//
	
	/**
	 * The description of the weather. eg: scattered clouds.
	 */
	private String condition;
	
	/**
	 * The actual temperature of the city in celsius.
	 */
	private Double temperature;
	
	/**
	 * Speed of the wind in km/h. 
	 * <p>{@code minimum = 0}
	 */
	@JsonProperty("wind_speed")
	private Double windSpeed;

	//
	// Constructors
	//
	
	/**
	 * Constructor 
	 * 
	 * @param condition
	 * @param temperature
	 * @param windSpeed
	 */
	public CurrentWeather(String condition, Double temperature, Double windSpeed) {
		super();
		this.condition = condition;
		this.temperature = temperature;
		this.windSpeed = windSpeed;
	}

	//
	// Getters
	//
	
	public String getCondition() {
		return condition;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}
	
}
