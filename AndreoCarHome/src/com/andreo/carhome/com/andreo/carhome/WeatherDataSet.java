package com.andreo.carhome.com.andreo.carhome;

import java.util.ArrayList;


public class WeatherDataSet {

	private WeatherCondition myCurrentCondition = null;
	private ArrayList<WeatherForecastCondition> myForecastConditions = 
		new ArrayList<WeatherForecastCondition>(4);

	public WeatherCondition getWeatherCondition() {
		return myCurrentCondition;
	}

	public void setWeatherCondition(
			WeatherCondition myCurrentWeather) {
		this.myCurrentCondition = myCurrentWeather;
	}


	public ArrayList<WeatherForecastCondition> getWeatherForecastConditions() {
		return this.myForecastConditions;
	}

	public WeatherForecastCondition getLastWeatherForecastCondition() {
		return this.myForecastConditions
		.get(this.myForecastConditions.size() - 1);
	}

}
