package com.example.g;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StuMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    BroadcastReceiver broadcastReceiver = null;
    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
        }

        loadFragment(new AccountFragment());

        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String pinno = intent.getStringExtra("pinno");
        String branch = intent.getStringExtra("branch");
        String year = intent.getStringExtra("year");
        String batch = intent.getStringExtra("batch");
        String code3=intent.getStringExtra( "collegecode" );
        String clgname=intent.getStringExtra( "clgname" );

        String f1tution=intent.getStringExtra( "f1tution" );
        String f1nongovt=intent.getStringExtra( "f1nongovt" );

        String f2tution=intent.getStringExtra( "f2tution" );
        String f2nongovt=intent.getStringExtra( "f2nongovt" );

        String f3tution=intent.getStringExtra( "f3tution" );
        String f3nongovt=intent.getStringExtra( "f3nongovt" );



        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        studentViewModel.setSharedData(branch,name,pinno,year,branch,code3,clgname,f1tution,f1nongovt,f2tution,f2nongovt,f3tution,f3nongovt);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Fragment fragment = null;

        if (itemId == R.id.profile) {
            fragment = new AccountFragment();
        } else if (itemId == R.id.payment) {
            fragment = new PaymentFragment();
        } else if (itemId == R.id.dashboard) {
            fragment = new DashBoardFragment();
        } else if (itemId == R.id.users) {
            fragment = new InfoFragment();
        }
        else if(itemId==R.id.logout)
        {
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
                window.setDimAmount(0.5f); // Adjust the value (0.0f to 1.0f) for the desired opacity
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
                    Intent intent = new Intent(StuMainActivity.this, StudentLogin.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to it
                    Toast.makeText(StuMainActivity.this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


        }


        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }



    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
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
