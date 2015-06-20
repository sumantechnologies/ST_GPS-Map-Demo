package st.gpsmap;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import st.gpsmap.R;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	 Context mContext =MainActivity.this;
	 InterstitialAd mInterstitialAd;
	 int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-4198488845038230/2487862906");

        
        displayad();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                requestNewInterstitial();


                Log.d("ad", "coming");

            }
        }, 3000);
        final TabHost tabHost = getTabHost();
		 // Tab for Photos
       TabSpec mygive = tabHost.newTabSpec("Map Fragment");
       mygive.setIndicator("Map Fragment", getResources().getDrawable(R.drawable.ic_launcher));
       Intent smsIntent = new Intent(mContext, MapFragmentDemo.class);
       mygive.setContent(smsIntent);
       
       TabSpec createnew = tabHost.newTabSpec("Support Map Fragment");
       createnew.setIndicator("Support Map Fragment", getResources().getDrawable(R.drawable.ic_launcher));
       Intent profileIntent = new Intent(mContext, SupportMapDemo.class);
       createnew.setContent(profileIntent);
       
       // Adding all TabSpec to TabHost
       tabHost.addTab(mygive);
       tabHost.addTab(createnew);
       Bundle extras = getIntent().getExtras();
       
       if (extras != null) {
       	if (extras.getString("value") != null) {
       		value = Integer.parseInt(extras.getString("value"));
       		tabHost.setCurrentTab(value);
       	} else {
       		tabHost.setCurrentTab(value);
       	}
       }
       for(int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
       	
       	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
       	tv.setTextColor(Color.BLACK);
       	tv.setPadding(0, 0, 0, 5);
       	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
       	tabHost.getTabWidget().getChildAt(i).setPadding(0, -2, 0, 0);
       	
       	
       }
       tabHost.setOnTabChangedListener(new OnTabChangeListener() {

           @Override
           public void onTabChanged(String arg0) {
           	for(int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
           		tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
           	}
           	 	
           	requestNewInterstitial();
           	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.roundshape); 
           }
       });
       	
      
       tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.roundshape); 
	
    }
    
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
      // .addTestDevice("DCD78C67A11D7952AECF0349896AD31D")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void displayad()
    {
        // Prepare an Interstitial Ad Listener
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
