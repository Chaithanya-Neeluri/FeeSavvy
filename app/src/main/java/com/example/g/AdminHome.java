package com.example.g;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

public class   AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    BroadcastReceiver broadcastReceiver = null;
    TextView admname,clgname;
    private DataViewModel dataViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);

        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String password = intent.getStringExtra("password");
        String AdminId = intent.getStringExtra("Adminid");
        String collegecode = intent.getStringExtra("collegecode");
        String mobile = intent.getStringExtra("Mobile");
        String clgname= intent.getStringExtra("clgname");

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Inflate the navigation drawer header layout
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false);

        // Find the TextView in the header layout
        TextView headerTextView = headerView.findViewById(R.id.adminname);

        // Set the text for the TextView
        headerTextView.setText(name);
        TextView headerText = headerView.findViewById(R.id.clgname);

        // Set the text for the TextView
        headerText.setText(clgname);

        // Add the header view to the navigation view
        if (navigationView.getHeaderCount() == 0) {
            navigationView.addHeaderView(headerView);
        }


        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.setSharedData(name, collegecode, mobile, password, AdminId,clgname);


        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home1Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home1);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (item.getItemId() == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        } else if (item.getItemId() == R.id.nav_home1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home1Fragment()).commit();
        } else if (item.getItemId() == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new ShareFragment() ).commit();
        }
        else if (item.getItemId() == R.id.nav_detain) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new detainstudent()).commit();
        }
        else if (item.getItemId() == R.id.nav_pin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new viewbypinno()).commit();
        } else if (item.getItemId() == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        } else if (item.getItemId() == R.id.nav_eee) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EeeFragment()).commit();
        } else if (item.getItemId() == R.id.nav_upgrade) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateFragment()).commit();
        } else if (item.getItemId() == R.id.nav_notification) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
        } else if (item.getItemId() == R.id.nav_logout) {
            // Create a custom dialog for confirmation
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.logout);
            dialog.setCanceledOnTouchOutside(false);

            // Set the dialog's size
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
            Button yes, no;
            Window window = getWindow();
            if (window != null) {
                window.setDimAmount(1.0f); // Adjust the value (0.0f to 1.0f) for the desired opacity
            }
            yes = dialog.findViewById(R.id.yes);
            no = dialog.findViewById(R.id.no);
            dialog.show();
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Clear any user data or session information here if needed
                    // Example: You may want to clear user preferences or tokens

                    // Start the LoginActivity to log the user out
                    Intent intent = new Intent(AdminHome.this, Login.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to it
                    Toast.makeText(AdminHome.this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


        }



        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }





    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void Internetstatus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}