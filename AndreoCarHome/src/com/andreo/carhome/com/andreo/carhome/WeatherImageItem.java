package com.andreo.carhome.com.andreo.carhome;

public class WeatherImageItem {

	private String condition;
	private int image;
	
	public WeatherImageItem(String condition, int image){
		this.condition = condition;
		this.image = image;
	}
	
	public String getCondition() {
		return condition;
	}

	public int getImage() {
		return image;
	}
	
}
