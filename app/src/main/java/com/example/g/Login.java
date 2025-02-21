package com.example.g;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Dictionary;
import java.util.Hashtable;

public class Login extends AppCompatActivity {

    private String pass;
    BroadcastReceiver broadcastReceiver = null;
    private String mobile;
    private String mypass, myid, mycode,myname;
    private ProgressBar verificationProgressBar;
    private TextView SignUp, back, Forgot;
    private Button login;

    private TextInputEditText id, code, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificationProgressBar = findViewById(R.id.verificationProgressBar);
        SignUp = findViewById(R.id.SignUp);
        back = findViewById(R.id.arrow);
        Forgot = findViewById(R.id.Forgot);
        id = findViewById(R.id.id);
        code = findViewById(R.id.code);
        password = findViewById(R.id.password);







        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, LogingAS.class));
                finish();
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Forgot.class));
                finish();
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    checkUser();
                }
            }
        });
    }

    private Boolean validateFields() {
        String adminId = id.getText().toString().trim();
        String collegeCode = code.getText().toString().trim();
        pass = password.getText().toString();

        if (adminId.isEmpty()) {
            id.setError("Admin Id cannot be empty");
            return false;
        } else if (collegeCode.isEmpty()) {
            code.setError("College Code cannot be empty");
            return false;
        }
        else if (pass.isEmpty()) {
            password.setError("College Code cannot be empty");
            return false;
        }
        return true;
    }

    private void checkUser() {
        String adminId = id.getText().toString().trim();
        String collegeCode = code.getText().toString().trim();
        verificationProgressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.INVISIBLE);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin");
        Query checkUserDatabase = reference.orderByChild("collegeCode").equalTo(collegeCode);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    DataSnapshot dataSnapshot = snapshot.getChildren().iterator().next();
                    myname= dataSnapshot.child("name").getValue(String.class);
                    mycode = dataSnapshot.child("collegeCode").getValue(String.class);
                    mypass = dataSnapshot.child("password").getValue(String.class);
                    myid = dataSnapshot.child("adminId").getValue(String.class);
                    mobile = dataSnapshot.child("mobile").getValue(String.class);
                    String clgname=dataSnapshot.child("clgname").getValue(String.class);

                    if (mycode != null && mypass != null && myid != null && mobile != null) {
                        if (mypass.equals(pass) && myid.equals(adminId)) {
                            verificationProgressBar.setVisibility(View.GONE);
                            // Start the OTP verification activity and pass data
                            Intent intent = new Intent(Login.this, AdminHome.class);
                            intent.putExtra("Mobile", mobile);
                            intent.putExtra("Name", myname);
                            intent.putExtra("Adminid", myid);
                            intent.putExtra("password", mypass);
                            intent.putExtra("collegecode", mycode);
                            intent.putExtra("clgname", clgname);
                            startActivity(intent);


                        } else {
                            verificationProgressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        verificationProgressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Data not found for this college code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    verificationProgressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    code.setError("College code does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                verificationProgressBar.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                Toast.makeText(Login.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Internetstatus() {
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
 }
