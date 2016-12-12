package edu.illinois.cs.cs563finalprojectapp;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        View.OnClickListener listener = new MyLovelyOnClickListener(this);
        fab.setOnClickListener(listener);


        ImageButton contacts = (ImageButton) findViewById(R.id.contacts);
        PhoneOnClickListener phoneOnClickListener = new PhoneOnClickListener(this);
        contacts.setOnClickListener(phoneOnClickListener);
    }
}

class PhoneOnClickListener implements View.OnClickListener
{
    Activity myLovelyVariable;
    public PhoneOnClickListener(Activity myLovelyVariable) {
        this.myLovelyVariable = myLovelyVariable;
    }

    @Override
    public void onClick(View v)
    {
        TelephonyManager tMgr = (TelephonyManager)myLovelyVariable.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        Toast.makeText(myLovelyVariable.getBaseContext(), "Got phone number as " + mPhoneNumber, Toast.LENGTH_LONG)
                .show();
    }
};

class MyLovelyOnClickListener implements View.OnClickListener
{
    Activity myLovelyVariable;
    public MyLovelyOnClickListener(Activity myLovelyVariable) {
        this.myLovelyVariable = myLovelyVariable;
    }

    @Override
    public void onClick(View v)
    {
        LocationManager locationManager = (LocationManager)
                                                  myLovelyVariable.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener(myLovelyVariable.getBaseContext(), v);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        Toast.makeText(myLovelyVariable.getBaseContext(), "Getting location...", Toast.LENGTH_LONG)
                .show();
    }
};

class MyLocationListener implements LocationListener {
    private Context myContext;
    private View myView;

    public MyLocationListener(Context context, View view) {
        myContext = context;
        myView = view;
    }

    @Override
    public void onLocationChanged(Location loc) {
        Geocoder gcd = new Geocoder(myContext, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                                            loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                Log.d("CS563", "Location is " + addresses.toString());
            }
            Toast.makeText(myContext, "Last location: " + loc.getLatitude(), Toast.LENGTH_LONG)
                    .show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
