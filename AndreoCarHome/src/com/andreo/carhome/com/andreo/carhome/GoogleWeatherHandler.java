package com.andreo.carhome.com.andreo.carhome;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class GoogleWeatherHandler extends DefaultHandler {

	private boolean in_outertag = false;
	private boolean in_innertag = false;

	private boolean in_forecast_information = false;
	private boolean in_current_conditions = false;
	private boolean in_forecast_conditions = false;

	private boolean usingSITemperature = true; // false means Fahrenheit
	private WeatherDataSet weatherDS = null;

	@Override
	public void startDocument() throws SAXException {
		this.weatherDS = new WeatherDataSet();
	}

	public WeatherDataSet getWeatherDataSet(){
		return weatherDS;
	}

	public void setWeatherDataSet(WeatherDataSet weatherDS){
		this.weatherDS = weatherDS;
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		// 'Outer' Tags
		if (localName.equals("forecast_information")) {
			this.in_forecast_information = true;
		} else if (localName.equals("current_conditions")) {
			this.weatherDS
			.setWeatherCondition(new WeatherCondition());
			this.in_current_conditions = true;
		} else if (localName.equals("forecast_conditions")) {
			this.weatherDS.getWeatherForecastConditions().add(
					new WeatherForecastCondition());
			this.in_forecast_conditions = true;
		} else {
			String dataAttribute = atts.getValue("data");
			// 'Inner' Tags of "<forecast_information>"
			if (localName.equals("city")) {
			} else if (localName.equals("postal_code")) {
			} else if (localName.equals("latitude_e6")) {
				/* One could use this to convert city-name to Lat/Long. */
			} else if (localName.equals("longitude_e6")) {
				/* One could use this to convert city-name to Lat/Long. */
			} else if (localName.equals("forecast_date")) {
			} else if (localName.equals("current_date_time")) {
			} else if (localName.equals("unit_system")) {
				if (dataAttribute.equals("SI"))
					this.usingSITemperature = true;
			}
			// SHARED(!) 'Inner' Tags within "<current_conditions>" AND
			// "<forecast_conditions>"
			else if (localName.equals("day_of_week")) {
				if (this.in_current_conditions) {
					this.weatherDS.getWeatherCondition()
					.setDayofWeek(dataAttribute);
				} else if (this.in_forecast_conditions) {
					this.weatherDS.getLastWeatherForecastCondition()
					.setDayofWeek(dataAttribute);
				}
			} else if (localName.equals("icon")) {
				if (this.in_current_conditions) {
					this.weatherDS.getWeatherCondition().setIconURL(
							dataAttribute);
				} else if (this.in_forecast_conditions) {
					this.weatherDS.getLastWeatherForecastCondition()
					.setIconURL(dataAttribute);
				}
			} else if (localName.equals("condition")) {
				if (this.in_current_conditions) {
					this.weatherDS.getWeatherCondition()
					.setCondition(dataAttribute);
				} else if (this.in_forecast_conditions) {
					this.weatherDS.getLastWeatherForecastCondition()
					.setCondition(dataAttribute);
				}
			}
			// 'Inner' Tags within "<current_conditions>"
			else if (localName.equals("temp_f")) {
				this.weatherDS.getWeatherCondition()
				.setTempFahrenheit(Integer.parseInt(dataAttribute));
			} else if (localName.equals("temp_c")) {
				this.weatherDS.getWeatherCondition().setTempCelcius(
						Integer.parseInt(dataAttribute));
			} else if (localName.equals("humidity")) {
				this.weatherDS.getWeatherCondition().setHumidity(
						dataAttribute);
			} else if (localName.equals("wind_condition")) {
				this.weatherDS.getWeatherCondition()
				.setWindCondition(dataAttribute);
			}

			// 'Inner' Tags within "<forecast_conditions>"
			else if (localName.equals("low")) {
				int temp = Integer.parseInt(dataAttribute);
				if (this.usingSITemperature) {
					this.weatherDS.getLastWeatherForecastCondition()
					.setTempMinCelsius(temp);
				} else {
					this.weatherDS.getLastWeatherForecastCondition()
					.setTempMinCelsius(
							WeatherUtils.fahrenheitToCelsius(temp));
				}
			} else if (localName.equals("high")) {
				int temp = Integer.parseInt(dataAttribute);
				if (this.usingSITemperature) {
					this.weatherDS.getLastWeatherForecastCondition()
					.setTempMaxCelsius(temp);
				} else {
					this.weatherDS.getLastWeatherForecastCondition()
					.setTempMaxCelsius(
							WeatherUtils.fahrenheitToCelsius(temp));
				}
			}
		}
			
		}

		@Override
		public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
			if (localName.equals("forecast_information")) {
				this.in_forecast_information = false;
			} else if (localName.equals("current_conditions")) {
				this.in_current_conditions = false;
			} else if (localName.equals("forecast_conditions")) {
				this.in_forecast_conditions = false;
			}
		}


		@Override
		public void endDocument() throws SAXException {
			// Do some finishing work if needed
		}
	}
