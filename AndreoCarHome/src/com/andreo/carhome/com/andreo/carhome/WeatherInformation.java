package com.andreo.carhome.com.andreo.carhome;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;
import android.widget.Toast;


public class WeatherInformation {
	
	private WeatherDataSet weatherDS;
	private WeatherCondition weatherCondition;
	private ArrayList<WeatherForecastCondition> weatherForecast;
	private WeatherForecastCondition weatherLastForecast;
	
	public WeatherDataSet getWeatherDS() {
		return weatherDS;
	}

	public WeatherInformation(){
		try{
			URL url = new URL("http://www.google.com/ig/api?weather=Dunedin,New%20Zealand");
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			GoogleWeatherHandler weatherHandler = new GoogleWeatherHandler();
			xr.setContentHandler(weatherHandler);
			xr.parse(new InputSource(url.openStream()));
			
			WeatherDataSet weatherDS = weatherHandler.getWeatherDataSet();	
			weatherCondition = weatherDS.getWeatherCondition();
			weatherForecast = weatherDS.getWeatherForecastConditions();
			weatherLastForecast = weatherDS.getLastWeatherForecastCondition();
			
		} catch(Exception e){
			Log.e("AndreoCarHome", "WeatherQueryError", e);
		}
	}
	public ArrayList<WeatherForecastCondition> getWeatherForecast() {
		return weatherForecast;
	}

	public WeatherCondition getWeatherCondition() {
		return weatherCondition;
	}
	
	public WeatherForecastCondition getWeatherLastForecastCondition(){
		return weatherLastForecast;
	}
	
}
