package com.andreo.carhome.com.andreo.carhome;

/**
 * Holds the information between the <forecast_conditions>-tag of what the
 * Google Weather API returned.
 */
public class WeatherForecastCondition {

	// ===========================================================
	// Fields
	// ===========================================================

	private String dayofWeek = null;
	private Integer tempMin = null;
	private Integer tempMax = null;
	private String iconURL = null;
	private String condition = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	public WeatherForecastCondition() {

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public String getDayofWeek() {
		return dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) {
		this.dayofWeek = dayofWeek;
	}

	public Integer getTempMinCelsius() {
		return tempMin;
	}

	public void setTempMinCelsius(Integer tempMin) {
		this.tempMin = tempMin;
	}

	public Integer getTempMaxCelsius() {
		return tempMax;
	}

	public void setTempMaxCelsius(Integer tempMax) {
		this.tempMax = tempMax;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "WeatherForecastCondition [dayofWeek=" + dayofWeek
				+ ", tempMin=" + tempMin + ", tempMax=" + tempMax
				+ ", iconURL=" + iconURL + ", condition=" + condition + "]";
	}
}
