package com.andreo.carhome.com.andreo.carhome;


public class WeatherCondition {

	// ===========================================================
	// Fields
	// ===========================================================

	private String dayofWeek = null;
	private Integer tempCelcius = null;
	private Integer tempFahrenheit = null;
	private String iconURL = null;
	private String condition = null;
	private String windCondition = null;
	private String humidity = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	public WeatherCondition() {

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public String getDayofWeek() {
		return this.dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) {
		this.dayofWeek = dayofWeek;
	}

	public Integer getTempCelcius() {
		return this.tempCelcius;
	}

	public void setTempCelcius(Integer temp) {
		this.tempCelcius = temp;
	}

	public Integer getTempFahrenheit() {
		return this.tempFahrenheit;
	}

	public void setTempFahrenheit(Integer temp) {
		this.tempFahrenheit = temp;
	}

	public String getIconURL() {
		return this.iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getWindCondition() {
		return this.windCondition;
	}

	public void setWindCondition(String windCondition) {
		this.windCondition = windCondition;
	}

	public String getHumidity() {
		return this.humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "WeatherCondition [dayofWeek=" + dayofWeek + ", tempCelcius=" 
				+ tempCelcius + "\n, tempFahrenheit=" + tempFahrenheit
				+ "\n, iconURL=" + iconURL + ", condition=" + condition
				+ "\n, windCondition=" + windCondition + ", humidity=" + humidity
				+ "]";
	}
}
