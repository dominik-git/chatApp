package com.dkolesar.chatapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.dkolesar.chatapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.dkolesar.chatapp.utils.Constants.APPLICATION_TAG;

public class MainActivity extends AppCompatActivity {

    private Object GUIStatics;
    private String country = "";
    private LocationManager locationManager;
    private String provider;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.fragment);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);




        NavigationUI.setupWithNavController(bottomNav, navController);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        try {
            country = getAddress(latitude,longitude);
            System.out.println("main aktivity "+country);
            SharedPreferences prefs = getSharedPreferences("CHAT_PREFS", 0);
            prefs.edit().putString("country", country).apply();
        }
         catch (IOException e) {
            e.printStackTrace();
        }

    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
        }
    };

    public String getAddress(double lat, double lng) throws IOException {
        Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
        String countryName="";
        if (addresses.size() > 0)
        {
             countryName=addresses.get(0).getCountryName();
        }
        return countryName;
    }



}
