package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.about.AboutFragment;
import com.example.myapplication.ui.login.LoginFragment;
import com.example.myapplication.ui.photo.PhotoFragment;
import com.example.myapplication.ui.register.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.register:
                    selectedFragment = new RegisterFragment();
                    break;
                case R.id.login:
                    selectedFragment = new LoginFragment();
                    break;
                case R.id.photo:
                    selectedFragment = new PhotoFragment();
                    break;
                case R.id.webview:
                    selectedFragment = new AboutFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }

        ;
    };
}
