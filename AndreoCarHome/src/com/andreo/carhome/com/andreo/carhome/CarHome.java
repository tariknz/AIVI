package com.andreo.carhome.com.andreo.carhome;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.andreo.carhome.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.*;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;


public class CarHome extends Activity implements OnInitListener {
    /** Called when the activity is first created. */

	private final static String DEBUG_TAG = "AndreoCarHome";
	private int currItem;
	private ImageView currImageView;
	private TextView tvWeather;
	private TextView tvSpeed;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private float currSpeed;
	private Location currLocation;
	private Location prevLocation;
	private TextView tvWeather2;
	private Address currAddress;
	
	private TextView tvStreetName;
	private TextView tvSuburb;
	private TextView tvTemp;
	private TextView tvCondition;
	private TextView tvCity;
	private RelativeLayout layoutWeather;
	private RelativeLayout layoutAddress;
	private TextToSpeech tts;
	private WeatherInformation weather;
	ImageView ivWeather;
	private static final int ADDRESS_REFRESH_RATE = 10000; //Update address every 10 Seconds 
	private String currentDate;
	private boolean runOnce;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.gallery); //navigation
    	tvWeather = (TextView)findViewById(R.id.tvWeather);
    	tvSpeed = (TextView)findViewById(R.id.tvSpeed);
    	tvWeather2 = (TextView)findViewById(R.id.tvWeather2);
    	tvStreetName = (TextView)findViewById(R.id.tvStreetName);
    	tvSuburb = (TextView)findViewById(R.id.tvSuburb);
    	tvTemp = (TextView)findViewById(R.id.tvTemp);
    	tvCondition = (TextView)findViewById(R.id.tvCondition);
    	tvCity = (TextView)findViewById(R.id.tvCity);
    	layoutWeather = (RelativeLayout)findViewById(R.id.RelativeLayout02);
    	layoutAddress = (RelativeLayout)findViewById(R.id.RelativeLayout03);
    	ivWeather = (ImageView)findViewById(R.id.ivWeather);  
    	
        currItem = 3;
        currImageView = null;
        tts = new TextToSpeech(this, this);
    	
    	final Calendar c = Calendar.getInstance();
    	final String[] strDays = new String[] { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
    	        "Friday", "Saturday" };
    	
    	currentDate = strDays[c.get(Calendar.DAY_OF_WEEK)] + " " + c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH);
    	Log.e(DEBUG_TAG, currentDate);
    	tvCity.setText(currentDate);
        
        ImageAdapter ia = new ImageAdapter(this);
        g.setAdapter(ia);
        g.setSpacing(10);
        g.setSelection(currItem);
        g.setMinimumHeight(200);
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
            	Context context = getApplicationContext();
            	NavItem navItem = (NavItem)parent.getAdapter().getItem(position);
            	String text = "" + navItem;
            	
            	//Application Name text
            	TextView tvAppName = (TextView)findViewById(R.id.tvAppName);
            	

//            	int duration = Toast.LENGTH_SHORT;
//            	Toast toast = Toast.makeText(context, text, duration);
//            	toast.setGravity(0, 0, 250);
//            	toast.show();
            	
            	//Application Icon
            	tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            	
            	if(tvAppName.getText().equals(text) && navItem.getActivity() != null){
            		final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            		intent.addCategory(Intent.CATEGORY_LAUNCHER);
            		Log.e(DEBUG_TAG, navItem.getActivity() + ", " + navItem.getComponent());
            		try{
            			final ComponentName cn = new ComponentName(navItem.getComponent(), navItem.getActivity());

            			intent.setComponent(cn);
            			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            			startActivity( intent);
            		}catch(Exception e){
            			Log.e(DEBUG_TAG, "Not found: " + navItem.getActivity() + ", " + navItem.getComponent());
            		}
            	}
            	
            	tvAppName.setText(text);
            	
            	if(currImageView != null)
            		animateRestore(currImageView);
            	
            	currItem = position;
            	currImageView = (ImageView)v;
            	animateEnlarge((ImageView)v);
            }
      
        });
        
        
        //Weather
        updateAddressTask.run();
        
        try{
        	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        	runOnce = true;
    		locationListener = new LocationListener() {

				public void onStatusChanged(String provider, int status, Bundle extras) {
					Log.e(DEBUG_TAG, "Location: " + status);
				}
    			public void onProviderEnabled(String provider) {
    				Log.e(DEBUG_TAG, "Provider Enabled: " + provider);
    			}
    			public void onProviderDisabled(String provider) {
    				Log.e(DEBUG_TAG, "Provider Disabled: " + provider);
    			}
    			public void onLocationChanged(Location location) {
    				//Log.e(DEBUG_TAG, location.toString());
    				
    				currLocation = location;
    				if(runOnce){
    					long time = location.getTime();
    					SimpleDateFormat sdf = new SimpleDateFormat("EEE");
    					Date resultdate = new Date(time);
    					String dayOfWeek = strDays[Integer.parseInt(sdf.format(resultdate))];
    					currentDate = dayOfWeek + " " + resultdate.getDay() + "/" + resultdate.getMonth();
    					tvCity.setText(currentDate);
    					runOnce = false;
    				}
    				
    				
    				if(currLocation != null)
    					tvSpeed.setText("" + Math.round(currLocation.getSpeed())); //round the speed no decimal points
    			}
    		};
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    		
        	
        }catch(Exception e){Log.e("AndreoCarHome","Error: " + e);}

        handler.removeCallbacks(updateAddressTask);
        handler.postDelayed(updateAddressTask, 1000);	
        
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, 0);
       
        
        layoutWeather.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String speech;
				if(!tvTemp.getText().equals("N/A")){
					speech = "The weather conditions are. " + tvCondition.getText() + ". The temperture is. " + weather.getWeatherCondition().getTempCelcius() + " degrees celsius";
				}else{
					speech = "Sorry, there is no weather information available.";
				}
				tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
			}
		});

        layoutAddress.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String speech;
				if(currAddress != null){
					System.out.println(currAddress.getThoroughfare());
					speech = "You are currently on " + currAddress.getThoroughfare() + ", " + currAddress.getLocality();
				}else{
					speech = "Sorry, there is no address information available.";
				}
				tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
				
			}
		});

    }
    @Override
    protected void onStop() {
    	super.onStop();
    	handler.removeCallbacks(updateAddressTask);
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	handler.removeCallbacks(updateAddressTask);
    	handler.postDelayed(updateAddressTask, ADDRESS_REFRESH_RATE);
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if ( handler != null )
    		handler.removeCallbacks(updateAddressTask);
    	handler = null;	
    	
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    
    
    private Handler handler = new Handler();
	
    private Runnable updateAddressTask = new Runnable() {
    	public void run() {
    		Log.e(DEBUG_TAG,"TimerTask executed");
    		updateAddressUI();
    		
    		if(tvTemp.getText().equals("N/A")){
    			updateWeatherUI();
    			if(weather != null){
    				tvCondition.setText(weather.getWeatherForecast().get(0).getCondition());
    				tvTemp.setText(weather.getWeatherCondition().getTempCelcius() + "\u00B0C"); //unicode for degrees symbol
    				tvCity.setText(currentDate);
    				WeatherImage wi = new WeatherImage();
    				ivWeather.setImageResource(wi.getWeatherImage(weather));
    			}

    		}
    		handler.postDelayed(this, ADDRESS_REFRESH_RATE);
    	}
    };
    
    private void updateWeatherUI(){
    	
        //Weather
    	Log.e(DEBUG_TAG, "Updating Weather....");
        try{
        	weather = new WeatherInformation();
        	WeatherCondition weatherCondition = weather.getWeatherCondition();
        	WeatherForecastCondition weatherLastForecast = weather.getWeatherLastForecastCondition();
        	
        	String condition = weather.getWeatherForecast().get(0).getCondition();
        	
        	Log.e(DEBUG_TAG, "Forecast size: " + weather.getWeatherForecast().size());
        	Log.e(DEBUG_TAG, "Condition: " + condition);
        	Log.e(DEBUG_TAG, "Day of Week: " + weather.getWeatherForecast().get(0).getDayofWeek());
        	Log.e(DEBUG_TAG, weatherCondition.toString());
        	Log.e(DEBUG_TAG, weatherLastForecast.toString());
        	

        }catch(Exception e){
        	Log.e("AndreoCarHome","Error retrieving weather: " + e);
        }
    }

   private void updateAddressUI(){
		System.out.println("Retrieve Address");
        getAddress();
        if(currLocation != null && currAddress != null){
   
	        Log.e(DEBUG_TAG, "HasSpeed: " + currLocation.hasSpeed() + " Location: " + currLocation.getLatitude() + ", " + currLocation.getLongitude());
	        tvSuburb.setText(currAddress.getLocality());
	    	tvStreetName.setText(currAddress.getThoroughfare());

	    	Log.e(DEBUG_TAG, currAddress.getLocality());
	
        }
   }
      
    
    private void getAddress(){
    	Log.e(DEBUG_TAG, "Geocoding..");
        Geocoder geo = new Geocoder(getApplicationContext());
        boolean sameLoc = false;
        
        if (currLocation != null && prevLocation != null)
        	if (currLocation.getLatitude() == prevLocation.getLatitude() && currLocation.getLongitude() == prevLocation.getLongitude())
        		sameLoc = true;
        
        
        if(currLocation != null && !sameLoc){
        	Log.e(DEBUG_TAG, "Retrieving Address...");
        	try {goo
        		Log.e(DEBUG_TAG, currLocation.getLatitude() + ", " + currLocation.getLongitude());
        		List<Address> addressList = geo.getFromLocation(currLocation.getLatitude(), currLocation.getLongitude(), 1);
        		
        		this.currAddress = addressList.get(0);
        		Log.e(DEBUG_TAG, "Address: " + currAddress.toString());
        		prevLocation = currLocation;
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		Log.e(DEBUG_TAG, e.toString());
        		e.printStackTrace();
        	}
        }else{
	        Log.e(DEBUG_TAG, "No new GPS Fix");

        }
    }
    
    
    private void animateEnlarge(ImageView imageView) {
        ScaleAnimation scale = new ScaleAnimation((float)1.0, (float)1.5, (float)1.0, (float)1.5, 50, 50);
        scale.setFillAfter(true);
        scale.setDuration(500);
        imageView.setAlpha(255);
        imageView.startAnimation(scale); 
        
    }
    private void animateRestore(ImageView imageView) {
        ScaleAnimation scale = new ScaleAnimation((float)1.0, (float)1.0, (float)1.0, (float)1.0);
        scale.setFillAfter(true);
        scale.setDuration(500);
        imageView.setAlpha(80);
        imageView.startAnimation(scale); 
        
    }
    
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;

        private NavItem[] navItems = {
                
        		new NavItem("Browser", R.drawable.icon_browser, "com.android.browser", "com.android.browser.BrowserActivity"),
        		new NavItem("Navigation", R.drawable.icon_nav, "brut.googlemaps",  "com.google.android.maps.driveabout.app.DestinationActivity"),
        		new NavItem("Music", R.drawable.icon_music, "com.android.music", "com.android.music.MusicBrowserActivity"),
        		new NavItem("Maps", R.drawable.icon_maps, "com.google.android.maps", "com.google.android.maps.MapsActivity"),
        		new NavItem("Reverse Cam", R.drawable.icon_camera),
        		new NavItem("Voice", R.drawable.icon_voice),
        		new NavItem("Mail", R.drawable.icon_email, "com.android.email", "com.android.email.activity.Welcome")
                
        };

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return navItems.length;
        }

        public NavItem getItem(int position) {
            return navItems[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            i.setImageResource(navItems[position].getImageId());
            i.setLayoutParams(new Gallery.LayoutParams(100, 100));
            i.setScaleType(ImageView.ScaleType.FIT_END);
            i.setAlpha(80);
            
            return i;
        }
        

    }


	public void onInit(int status) {
		
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Lanuage data is missing or the language is not supported.
                Log.e(DEBUG_TAG, "Language is not available.");
            } else {
            	speakIntro();
            }
        } else {
            // Initialization failed.
            Log.e(DEBUG_TAG, "Could not initialize TextToSpeech.");
            
        }

	}
	
	private void speakIntro(){
		String speech1 = "Hello Master.";
		tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
	}
}