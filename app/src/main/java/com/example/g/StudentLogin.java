package com.example.g;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @noinspection ReassignedVariable*/
public class StudentLogin extends AppCompatActivity {
    private TextView back;
    private TextInputEditText pin,code;
    Button login;
    String pinno2,batch;
    BroadcastReceiver broadcastReceiver = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        back=findViewById(R.id.back);
        login=findViewById(R.id.login);
        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(StudentLogin.this, LogingAS.class);
                startActivity(i1);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUser();

            }
        });

        pin=findViewById( R.id.pinno );
        code=findViewById( R.id.code );






    }
    public void Internetstatus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    void batch() {
        if (pinno2 != null && pinno2.length() >= 2) {
            batch = pinno2.substring(0, 2) + "-Batch";
        }
    }

    public void checkUser() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.load);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.show();


        String code1=code.getText().toString().trim();
        pinno2=pin.getText().toString();

        if ( pinno2.isEmpty() && code1.isEmpty()) {
            dialog.dismiss();
            Toast.makeText( StudentLogin.this,"All fields are Required",Toast.LENGTH_SHORT ).show();
        }
        else {
            batch();
        }
        if(!isValidPin(pinno2)) {
            dialog.dismiss();
            pin.setError("Format of Pinno is not valid. Syn:YYClC-BR-NUM Eg:21101-CM-065" );

        }
        else if(code1.length()<3) {
            dialog.dismiss();
             code.setError("College Code Must Contain 3 Digits");
        }

        else {

            String branch;
            String branch1 = "";
            if(pinno2.length()==12) {
                branch = pinno2.substring(6,8);
                if(branch.equals("CM"))
                {
                    branch1="DCME";
                }
                else if(branch.equals("EC")){
                    branch1="DECE";
                }
                else if(branch.equals("EE")){
                    branch1="DEEE";
                }

            }
            if(pinno2.length()==11){
                branch=pinno2.substring(6,7);
                if(branch.equals("M")){
                    branch1="DME";
                }
            }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "admin" ).child( code1 ).child( "Student" ).child( branch1 ).child( batch );

            Query checkUserDatabase = reference.orderByChild( "pinno" ).equalTo( pinno2 );

            checkUserDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Iterate through the results (although there should be only one)
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            viewstudent viewstudent=dataSnapshot.getValue(viewstudent.class);

                            String pinno = dataSnapshot.child( "pinno" ).getValue( String.class );
                            String branch=  dataSnapshot.child( "branch" ).getValue( String.class );
                            String name=  dataSnapshot.child( "name" ).getValue( String.class );
                            String year = dataSnapshot.child( "year" ).getValue( String.class );
                            String clgname=dataSnapshot.child( "clgname" ).getValue( String.class );

                            viewstudent.FirstYearFees f1 = viewstudent.getFirstyearfees();
                            String f1nongovt = f1.getNongovtfee();
                            String f1tution = f1.getTutionfee();

                            viewstudent.SecondYearFees f2 = viewstudent.getSecondyearfees();
                            String f2nongovt = f2.getNongovtfee();
                            String f2tution = f2.getTutionfee();

                            viewstudent.ThirdYearFees f3 = viewstudent.getThirdyearfees();
                            String f3nongovt = f3.getNongovtfee();
                            String f3tution = f3.getTutionfee();

                            if (pinno != null && pinno.equals( pinno2 )) {
                                dialog.dismiss();
                                Intent i1 = new Intent( StudentLogin.this, StuMainActivity.class );
                                i1.putExtra("branch",branch);
                                i1.putExtra("pinno", pinno);
                                i1.putExtra("name",name);
                                i1.putExtra("year", year);
                                i1.putExtra("batch",batch);
                                i1.putExtra("collegecode",code1);
                                i1.putExtra("clgname",clgname);

                                i1.putExtra("f1tution", f1tution);
                                i1.putExtra("f1nongovt",f1nongovt);
                                i1.putExtra("f2tution", f2tution);
                                i1.putExtra("f2nongovt",f2nongovt);
                                i1.putExtra("f3tution", f3tution);
                                i1.putExtra("f3nongovt",f3nongovt);

                                startActivity( i1 );
                                finish();
                            }
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText( StudentLogin.this, "Pin Does not Number Exists", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    dialog.dismiss();
                    Toast.makeText( StudentLogin.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }
    public static boolean isValidPin(String pin) {
        // Define a regular expression pattern to match the PIN format
        String pattern = "\\d{5}-[A-Z]{1,2}-\\d{3}";


        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(pin);
        return matcher.matches();
    }

}