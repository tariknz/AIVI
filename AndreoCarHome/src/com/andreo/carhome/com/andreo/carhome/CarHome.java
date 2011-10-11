package com.andreo.carhome.com.andreo.carhome;

import com.andreo.carhome.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;


public class CarHome extends Activity {
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
	private TextView tvWeather2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.gallery);
    	tvWeather = (TextView)findViewById(R.id.tvWeather);
    	tvSpeed = (TextView)findViewById(R.id.tvSpeed);
    	tvWeather2 = (TextView)findViewById(R.id.tvWeather2);
    	
        currItem = 3;
        currImageView = null;
    	
        ImageAdapter ia = new ImageAdapter(this);
        g.setAdapter(ia);
        g.setSpacing(10);
        g.setSelection(currItem);
        g.setMinimumHeight(200);
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
            	Context context = getApplicationContext();
            	CharSequence text = "" + parent.getAdapter().getItem(position);
            	
            	TextView tvAppName = (TextView)findViewById(R.id.tvAppName);
            	tvAppName.setText(text);

//            	int duration = Toast.LENGTH_SHORT;
//            	Toast toast = Toast.makeText(context, text, duration);
//            	toast.setGravity(0, 0, 250);
//            	toast.show();
            	
            	if(currImageView != null)
            		animateRestore(currImageView);
            	
            	currItem = position;
            	currImageView = (ImageView)v;
            	animateEnlarge((ImageView)v);
            }
      
        });
        try{
        	WeatherInformation weather = new WeatherInformation();
        	WeatherCondition weatherCondition = weather.getWeatherCondition();
        	WeatherForecastCondition weatherLastForecast = weather.getWeatherLastForecastCondition();
        	tvWeather.setText(weatherCondition.toString());
        	tvWeather2.setText(weatherLastForecast.toString());
        	
        	
        	Log.e("DEBUG", "Forecast size: " + weather.getWeatherForecast().size());
        	Log.e("DEBUG", "Forecast size: " + weather.getWeatherForecast().get(0).getCondition());
        	Log.e("DEBUG", "Forecast size: " + weather.getWeatherForecast().get(0).getDayofWeek());
        	
        	WeatherImage wi = new WeatherImage();
        	ImageView ivWeather = (ImageView)findViewById(R.id.ivWeather);  
        	ivWeather.destroyDrawingCache();
        	ivWeather.setImageResource(wi.getWeatherImage(weather));
        	

        }catch(Exception e){
        	Log.e("AndreoCarHome","Error retrieving weather: " + e);
        }

        try{
        	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
    				Log.e(DEBUG_TAG, location.toString());
    				currLocation = location;
    		        
    		        if(currLocation != null){
    			        Log.e(DEBUG_TAG, "HasSpeed: " + location.hasSpeed() + " Location: " + currLocation.getLatitude() + ", " + currLocation.getLongitude());
    			    	tvSpeed.setText("Speed: " + currLocation.getSpeed());
    			    	
    			    	
    		        }
    				
    			}
    		};
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        	
        }catch(Exception e){Log.e("AndreoCarHome","Error: " + e);}

    }
    
    
    private void animateEnlarge(ImageView imageView) {
        ScaleAnimation scale = new ScaleAnimation((float)1.0, (float)1.5, (float)1.0, (float)1.5, 50, 50);
        scale.setFillAfter(true);
        scale.setDuration(500);
        imageView.setAlpha(255);
        //AlphaAnimation alpha = new AlphaAnimation(80, 255);
        //alpha.setFillAfter(true);
        //imageView.startAnimation(alpha);
        imageView.startAnimation(scale); 
        
    }
    private void animateRestore(ImageView imageView) {
        ScaleAnimation scale = new ScaleAnimation((float)1.0, (float)1.0, (float)1.0, (float)1.0);
        scale.setFillAfter(true);
        scale.setDuration(500);
        imageView.setAlpha(80);
        //AlphaAnimation alpha = new AlphaAnimation(255, 100);
        //imageView.getDrawable().setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SCREEN));
        //imageView.startAnimation(alpha);
        imageView.startAnimation(scale); 
        
    }
    
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;

        private NavItem[] navItems = {
                
        		new NavItem("Browser", R.drawable.icon_browser),
        		new NavItem("Navigation", R.drawable.icon_nav),
        		new NavItem("Music", R.drawable.icon_music),
        		new NavItem("Maps", R.drawable.icon_maps),
        		new NavItem("ReverseCam", R.drawable.icon_camera),
        		new NavItem("Voice", R.drawable.icon_voice),
        		new NavItem("Mail", R.drawable.icon_email)
                
        };

        public ImageAdapter(Context c) {
            mContext = c;
            //TypedArray a = obtainStyledAttributes(android.R.styleable.Theme);
            //mGalleryItemBackground = a.getResourceId(android.R.styleable.Theme_galleryItemBackground, 0);
            //a.recycle();
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
            //i.getDrawable().setColorFilter(new PorterDuffColorFilter(0x0, PorterDuff.Mode.CLEAR));
            
            return i;
        }
        

    }
}