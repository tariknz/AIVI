package com.andreo.carhome.com.andreo.carhome;

import java.util.concurrent.locks.Condition;

import com.andreo.carhome.R;

import android.util.Log;
import android.widget.ImageView;

public class WeatherImage {

	WeatherForecastCondition currWeather;
	String weatherCondition;
	WeatherImageItem[] images = {
			new WeatherImageItem("Not Available",R.drawable.weather_not_available),
			new WeatherImageItem("Clear",R.drawable.weather_clear),
			new WeatherImageItem("Cloudy",R.drawable.weather_cloudy),
			new WeatherImageItem("Fog",R.drawable.weather_fog),
			new WeatherImageItem("Haze",R.drawable.weather_haze),
			new WeatherImageItem("Light Rain",R.drawable.weather_light_rain),
			new WeatherImageItem("Mostly Cloudy",R.drawable.weather_mostly_cloudy),
			new WeatherImageItem("Partly Cloudy",R.drawable.weather_partly_cloudy),
			new WeatherImageItem("Rain",R.drawable.weather_rain),
			new WeatherImageItem("Rain Showers",R.drawable.weather_rain_showers),
			new WeatherImageItem("Showers",R.drawable.weather_showers),
			new WeatherImageItem("Thunderstorm",R.drawable.weather_thunderstorm),
			new WeatherImageItem("Chance of Showers",R.drawable.weather_chance_of_showers),
			new WeatherImageItem("Chance of Snow",R.drawable.weather_chance_of_snow),
			new WeatherImageItem("Chance of Storm",R.drawable.weather_chance_of_storm),
			new WeatherImageItem("Mostly Sunny",R.drawable.weather_mostly_sunny),
			new WeatherImageItem("Partly Sunny",R.drawable.weather_partly_sunny),
			new WeatherImageItem("Scattered Showers",R.drawable.weather_scattered_showers),
			new WeatherImageItem("Sunny",R.drawable.weather_sunny),
			new WeatherImageItem("Snow",R.drawable.weather_snow),
			new WeatherImageItem("Chance of Rain",R.drawable.weather_chance_of_rain)

		
	};

	public WeatherImage(){

		currWeather = null;

	}
	

	public int getWeatherImage(WeatherInformation weather){
		try{
			currWeather = weather.getWeatherForecast().get(0);
			weatherCondition = currWeather.getCondition();
			
			int index = getImageIndex(weatherCondition);
			return images[index].getImage();
			

		}catch(Exception e){
			Log.e("DEBUG", "Could not retrieve weather image: " + e.toString());
		}

		return images[0].getImage(); //"not available" image
	}
	
	
	private int getImageIndex(String condition){
		for (int i = 0; i < images.length; i++) {
			if(images[i].getCondition().equals(condition))
				return i;
		}
		
		return -1;
	}
	
}
