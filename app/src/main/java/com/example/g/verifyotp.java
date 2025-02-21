package com.example.g;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class verifyotp extends AppCompatActivity {
    EditText[] otpFields;
    Button verifyButton;
    String enteredOtp,code,id,mobile,password;
    String verificationId;
    TextView phone;
    EditText ot1, ot2, ot3, ot4, ot5, ot6;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpauth);
        ProgressBar p=findViewById(R.id.progress);

        // Initialize the OTP EditText fields
        otpFields = new EditText[]{
                findViewById(R.id.ot1),
                findViewById(R.id.ot2),
                findViewById(R.id.ot3),
                findViewById(R.id.ot4),
                findViewById(R.id.ot5),
                findViewById(R.id.ot6)
        };
        ot1 = findViewById(R.id.ot1);
        ot2 = findViewById(R.id.ot2);
        ot3 = findViewById(R.id.ot3);
        ot4 = findViewById(R.id.ot4);
        ot5 = findViewById(R.id.ot5);
        ot6 = findViewById(R.id.ot6);

        addTextWatcher(ot1, ot2);
        addTextWatcher(ot2, ot3);
        addTextWatcher(ot3, ot4);
        addTextWatcher(ot4, ot5);
        addTextWatcher(ot5, ot6);
        ImageView i=findViewById(R.id.back);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(verifyotp.this,Forgot.class);
                startActivity(i);
                finish();
            }
        });
        // Initialize the Verify button
        verifyButton = findViewById(R.id.button);

        // Get the verification ID passed from the previous activity
        verificationId = getIntent().getStringExtra("verificationId");
        code = getIntent().getStringExtra("collegecode");
        id = getIntent().getStringExtra("Adminid");
        mobile= getIntent().getStringExtra("Mobile");
        password= getIntent().getStringExtra("password");
        phone=findViewById(R.id.mobile);
        phone.setText(mobile);



        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredOtp = getEnteredOtp();

                if (!enteredOtp.isEmpty()) {
                    p.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, enteredOtp);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    p.setVisibility(View.INVISIBLE);
                                    verifyButton.setVisibility(View.VISIBLE);
                                    updatePassword(code, password);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    p.setVisibility(View.INVISIBLE);
                                    verifyButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(verifyotp.this, "Phone authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    p.setVisibility(View.INVISIBLE);
                    verifyButton.setVisibility(View.VISIBLE);
                    Toast.makeText(verifyotp.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getEnteredOtp() {
        StringBuilder builder = new StringBuilder();
        for (EditText otpField : otpFields) {
            builder.append(otpField.getText().toString().trim());
        }
        return builder.toString();
    }
    private void updatePassword(String clgcode, String newPassword) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("admin").child(clgcode).child("password");
        myRef.setValue(newPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(verifyotp.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(verifyotp.this,Login.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(verifyotp.this, "Not updated! Try again..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addTextWatcher(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    nextEditText.requestFocus(); // Move focus to the next EditText
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}
