package edu.illinois.cs.cs563finalprojectapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView fab = (TextView) findViewById(R.id.fab);
        View.OnClickListener listener = new MyLovelyOnClickListener(this);
        fab.setOnClickListener(listener);
    }
}

class MyLovelyOnClickListener implements View.OnClickListener {
    Activity myLovelyVariable;

    public MyLovelyOnClickListener(Activity myLovelyVariable) {
        this.myLovelyVariable = myLovelyVariable;
    }

    @Override public void onClick(View v) {
        LocationManager locationManager = (LocationManager) myLovelyVariable.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation((LocationManager.GPS_PROVIDER));

        TelephonyManager tMgr = (TelephonyManager) myLovelyVariable.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        if (location != null) {
            Toast.makeText(myLovelyVariable.getBaseContext(),
                           "Got last location as " + location.getLatitude() + "\nGot phone number as " + mPhoneNumber,
                           Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(myLovelyVariable.getBaseContext(),
                           "No know location." + "\nGot phone number as " + mPhoneNumber, Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(myLovelyVariable, LoggedInActivity.class);
        myLovelyVariable.startActivity(intent);
    }
}
