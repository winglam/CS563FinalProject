package edu.illinois.cs.cs563finalprojectapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        View.OnClickListener listener = new MyLovelyOnClickListener(this);
        fab.setOnClickListener(listener);


        FloatingActionButton contacts = (FloatingActionButton) findViewById(R.id.contacts);
        PhoneOnClickListener phoneOnClickListener = new PhoneOnClickListener(this);
        contacts.setOnClickListener(phoneOnClickListener);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        if (ContextCompat.checkSelfPermission(myLovelyVariable, android.Manifest.permission.ACCESS_FINE_LOCATION ) ==
            PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager)myLovelyVariable.getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            Snackbar.make(v, "Got phone number as " + mPhoneNumber, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            ActivityCompat.requestPermissions(myLovelyVariable,
                                              new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
            Snackbar.make(v, "Getting phone permission", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
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
        if (ContextCompat.checkSelfPermission(myLovelyVariable, android.Manifest.permission.ACCESS_FINE_LOCATION ) ==
            PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            Snackbar.make(v, "Getting your location...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            ActivityCompat.requestPermissions(myLovelyVariable,
                                              new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            Snackbar.make(v, "Location permission not granted :(", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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
            Snackbar.make(myView, "Last location: " + loc.getLatitude(), Snackbar.LENGTH_LONG).setAction("Action", null)
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
