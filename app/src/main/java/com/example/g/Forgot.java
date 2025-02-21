package com.example.g;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.example.g.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Forgot extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver = null;
    TextInputEditText id, code, pass;
    Button btn;
    ProgressBar verification;
    DatabaseReference adminRef;
    String password;

    // Map to store admin codes and passwords
    private Map<String, String> adminCodesMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        id = findViewById(R.id.id);
        code = findViewById(R.id.code);
        pass = findViewById(R.id.newpass);
        btn = findViewById(R.id.button);
        verification=findViewById(R.id.progress);
        TextView back=findViewById(R.id.arrow);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Forgot.this,Login.class);
                startActivity(i);
                finish();

            }
        });

        adminRef = FirebaseDatabase.getInstance().getReference("admin").child("adminCodes");

        // Populate the adminCodesMap with admin codes and passwords
        adminCodesMap = new HashMap<>();
        adminCodesMap.put("admin_code_1", "password1");
        adminCodesMap.put("admin_code_2", "password2");
        // Add more admin codes and passwords as needed

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clgcode = code.getText().toString();
                password = pass.getText().toString();
                final String enteredAdminCode = id.getText().toString();

                if (clgcode.isEmpty() || password.isEmpty() || enteredAdminCode.isEmpty()) {
                    Toast.makeText(Forgot.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    checkUser();

                }

            }
        });
    }



    public void checkUser(){
        verification.setVisibility(View.VISIBLE);
        btn.setVisibility(View.INVISIBLE);
        String password = pass.getText().toString();
        String adminid = id.getText().toString().trim();
        String collegecode =code.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin");
        Query checkUserDatabase = reference.orderByChild("collegeCode").equalTo(collegecode);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    id.setError(null);

                    String adminId = snapshot.child(collegecode).child("adminId").getValue(String.class);
                    String clgcode = snapshot.child(collegecode).child("collegeCode").getValue(String.class);
                    String mobile = snapshot.child(collegecode).child("mobile").getValue(String.class);



                    if (adminId.equals(adminid)&&clgcode.equals(collegecode)) {
                        id.setError(null);
                        verification.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        sendOTP(mobile,adminId,clgcode);

                    } else {
                        id.setError("Invalid Credentials");
                        code.setError("Invalid Credentials");
                        code.requestFocus();
                        verification.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        Toast.makeText(Forgot.this,"Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    verification.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.VISIBLE);
                    code.setError("College code does not exists");
                    code.requestFocus();
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                verification.setVisibility(View.INVISIBLE);
                btn.setVisibility(View.VISIBLE);
                Toast.makeText(Forgot.this,"Database Error", Toast.LENGTH_SHORT).show();

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
    private void sendOTP(String phoneNumber, String adminid ,String collegecode) {
        verification.setVisibility(View.VISIBLE);
        btn.setVisibility(View.INVISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,
                60, // Timeout duration
                TimeUnit.SECONDS,
                this, // Activity (or some context)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verification.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(Forgot.this, verifyotp.class);
                        intent.putExtra("Mobile", phoneNumber);
                        intent.putExtra("Adminid", adminid);
                        intent.putExtra("password", password);
                        intent.putExtra("collegecode", collegecode);
                        intent.putExtra("verificationId", verificationId); // Corrected parameter name
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Handle automatic code verification if needed
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verification.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        Toast.makeText(Forgot.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
