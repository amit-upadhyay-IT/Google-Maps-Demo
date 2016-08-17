package com.amitupadhyay.googleapiclientdemo;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button btnFetch;
    private TextView txtLocation;

    private GoogleApiClient googleApiClient;

    public void initViews() {
        btnFetch = (Button) findViewById(R.id.btnFetch);
        txtLocation = (TextView) findViewById(R.id.textViewLocation);

        btnFetch.setOnClickListener(this);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        builder.addApi(LocationServices.API);

        googleApiClient = builder.build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        googleApiClient.connect(); // now onConnected will be called
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Grand The permissions", Toast.LENGTH_SHORT).show();
        }
        else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient); // its the last known location

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            txtLocation.setText(String.format("Location %s,%s", latitude, longitude));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
