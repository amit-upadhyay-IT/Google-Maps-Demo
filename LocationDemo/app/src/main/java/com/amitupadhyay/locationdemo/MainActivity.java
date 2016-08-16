package com.amitupadhyay.locationdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    TextView txtLocation;
    Button btnFetch;

    LocationManager locationManager;
    ProgressDialog progressDialog;

    public void initViews() {
        txtLocation = (TextView) findViewById(R.id.textViewLocation);
        btnFetch = (Button) findViewById(R.id.buttonFetch);

        btnFetch.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Locaitoin...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    public void onClick(View view) { // when I click on the button I need to request the LocationManager to request the location.

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please grant permission in Settings", Toast.LENGTH_LONG).show();
        } else {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                progressDialog.show();
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);// in this way the locationManager will run in a loop and after some interval of time / or distance its gonna give a new location.
            }
            else
                Toast.makeText(this, "Kindly Enable GPS", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onLocationChanged(Location location) {// we are gonna use this API only.

        // it has input as Location which contains the latitude and longitude.

        //Geocoding

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        txtLocation.setText("Location " + latitude + ", " + longitude);

        // reverse Geocoding : here we use Geocoder

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addrsList = geocoder.getFromLocation(latitude, longitude, 2); // 2 means we are requesting only 2 addresses nearest to my latitude and longitude
            // so reverse geocoding will not give us the precise address rather it will give us the closest address from the latitude and longitude we are supplying from googles database.

            if (addrsList != null && addrsList.size() > 0)
            {
                Address address = addrsList.get(0);
                StringBuilder builder = new StringBuilder();

                for(int i = 0; i < address.getMaxAddressLineIndex(); ++i)
                {
                    builder.append(address.getAddressLine(i)+"\n");
                }
                txtLocation.setText(builder.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please grant permission in Settings", Toast.LENGTH_LONG).show();
        }
        else {
            locationManager.removeUpdates(this); // it means no more location fetching
        }

        progressDialog.dismiss();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
