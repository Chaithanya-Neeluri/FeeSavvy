package com.example.g;


import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LogingAS extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver = null;
    private Button stubtn;
    private Button admbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_as);
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
        stubtn = findViewById(R.id.stubtn);
        admbtn = findViewById(R.id.admbtn);
        stubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(LogingAS.this, StudentLogin.class);
                startActivity(i1);
                finish();
            }
        });

        admbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent i2 = new Intent(LogingAS.this, Login.class);
                startActivity(i2);
                finish();

            }
        });

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


