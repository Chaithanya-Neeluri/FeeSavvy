package com.example.g;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private TextInputEditText textInputEditTextFullName, textInputEditTextAdminId, textInputEditTextCollegeCode, textInputEditTextEmail, textInputEditTextEnterPassword, textInputEditTextConfirmPassword;
    private Button signbtn;
    private FirebaseDatabase database;
    private int choice;
    TextView login;
    String name, id, mobile, password, code, confirm, clgname;
    Dictionary<String, String> polytechnics = new Hashtable<>();
    private BroadcastReceiver broadcastReceiver = null;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile( "^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
        FirebaseApp.initializeApp( this );

        textInputEditTextFullName = findViewById( R.id.fullname );
        textInputEditTextAdminId = findViewById( R.id.adminid );
        textInputEditTextCollegeCode = findViewById( R.id.college );
        textInputEditTextEmail = findViewById( R.id.email1 );
        textInputEditTextEnterPassword = findViewById( R.id.enterpassword );
        textInputEditTextConfirmPassword = findViewById( R.id.confirmpassword );
        signbtn = findViewById( R.id.signbtn );
        login=findViewById( R.id.login );
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUp.this, Login.class);
                startActivity( i );
                finish();
            }
        } );

        polytechnics.put( "008", "Government Polytechnic, Srikakulam" );
        polytechnics.put( "009", "Government Polytechnic, Visakhapatnam" );
        polytechnics.put( "010", "Andhra Polytechnic, Kakinada" );
        polytechnics.put( "011", "Government Polytechnic For Women, Kakinada" );
        polytechnics.put( "013", "Government Polytechnic, Vijayawada" );
        polytechnics.put( "014", "Government Polytechnic, Guntur" );
        polytechnics.put( "015", "Government Polytechnic For Women, Guntur" );
        polytechnics.put( "016", "Government Polytechnic, Nellore" );
        polytechnics.put( "017", "Government Polytechnic, Gudur" );
        polytechnics.put( "018", "SV. Government Polytechnic, Tirupati" );
        polytechnics.put( "020", "Government Polytechnic, Anantapur" );
        polytechnics.put( "021", "Esc Government Polytechnic, Nandyal" );
        polytechnics.put( "022", "Government Polytechnic, Proddatur" );
        polytechnics.put( "038", "Mragr Government Polytechnic, Vizianagaram" );
        polytechnics.put( "039", "D.A Government Polytechnic, Ongole" );
        polytechnics.put( "043", "Government Model Residential Polytechnic, Paderu" );
        polytechnics.put( "045", "Government Polytechnic For Women, Bheemunipatnam" );
        polytechnics.put( "048", "Government Polytechnic For Women, Nellore" );
        polytechnics.put( "055", "Sri G P R Government Polytechnic, Kurnool" );
        polytechnics.put( "057", "Government Polytechnic For Women, Kadapa" );
        polytechnics.put( "058", "Government Polytechnic For Women, Hindupur" );
        polytechnics.put( "059", "Government Polytechnic For Women, Palamaneru" );
        polytechnics.put( "060", "Government Polytechnic, Narsipatnam" );
        polytechnics.put( "063", "Government Instt Of Textile Technology, Guntur" );
        polytechnics.put( "065", "Government Institute Of Chemical Engg., Visakhapatnam" );
        polytechnics.put( "067", "Government Model Residential Polytechnic, Yatapaka" );
        polytechnics.put( "068", "Government Model Residential Polytechnic, Srisailm" );
        polytechnics.put( "070", "Government Institute Of Ceramic Technology, Gudur" );
        polytechnics.put( "071", "Suvr And Sr Government Polytechnic For Women, Ethamukkala" );
        polytechnics.put( "072", "Dr. B.R. Ambedkar Government Model Residential Polytechnic, Rajahmundry" );
        polytechnics.put( "073", "Government Model Residential Polytechnic, Madanapalle" );
        polytechnics.put( "077", "Government Polytechnic For Women, Nandigama" );
        polytechnics.put( "088", "Government Polytechnic For Women, Srikakulam" );
        polytechnics.put( "096", "Government Polytechnic For Minorities, Guntur" );
        polytechnics.put( "098", "Government Polytechnic For Minorities, Kurnool" );
        polytechnics.put( "101", "Yc James Yen Government Polytechnic, Kuppam" );
        polytechnics.put( "154", "Government Polytechnic, Obulavaripally" );
        polytechnics.put( "155", "Government Polytechnic, Nagari" );
        polytechnics.put( "162", "Government Polytechnic, Jangareddygudem" );
        polytechnics.put( "163", "Government Polytechnic, Parvathipuram" );
        polytechnics.put( "164", "Government Polytechnic, Ponnur" );
        polytechnics.put( "165", "Government Polytechnic, Raidurg" );
        polytechnics.put( "166", "Government Polytechnic, Chandragir" );
        polytechnics.put( "170", "Government Polytechnic, Dharmavaram" );
        polytechnics.put( "171", "Government Polytechnic, Jammalamadugu" );
        polytechnics.put( "172", "Government Polytechnic, Vempalli" );
        polytechnics.put( "173", "Government Polytechnic, Anakapalli" );
        polytechnics.put( "175", "Government Polytechnic, Kadiri" );
        polytechnics.put( "176", "Government Polytechnic, Rajampeta" );
        polytechnics.put( "178", "Government Polytechnic, Tadepalligudem" );
        polytechnics.put( "183", "Government Polytechnic, Gannavaram" );
        polytechnics.put( "184", "Government Polytechnic, Simhadripuram" );
        polytechnics.put( "185", "Government Polytechnic, Satyavedu" );
        polytechnics.put( "188", "Government Polytechnic, Aluru" );
        polytechnics.put( "192", "Dr. YSR Government Polytechnic, Kalidindi" );
        polytechnics.put( "198", "Government Polytechnic, Tadipatri" );
        polytechnics.put( "199", "Government Polytechnic, Kamalapuram" );
        polytechnics.put( "200", "Government Polytechnic, Kalikiri" );
        polytechnics.put( "201", "Government Polytechnic, Kandukur" );
        polytechnics.put( "202", "Government Polytechnic, Addanki" );
        polytechnics.put( "203", "Government Polytechnic, Adoni" );
        polytechnics.put( "205", "Government Polytechnic, Uravakonda" );
        polytechnics.put( "206", "Government Polytechnic, Madakasira" );
        polytechnics.put( "207", "Government Polytechnic, Kalyandurg" );
        polytechnics.put( "208", "Government Polytechnic, Amudalavalasa" );
        polytechnics.put( "209", "Government Polytechnic, Kavali" );
        polytechnics.put( "212", "Government Polytechnic, Krosuru" );
        polytechnics.put( "213", "Government Polytechnic, Rayachoti" );
        polytechnics.put( "215", "Government Polytechnic, Machilapatnam" );
        polytechnics.put( "305", "Government Polytechnic, Atmakur" );
        polytechnics.put( "306", "Government Polytechnic, Repalle" );
        polytechnics.put( "332", "Smt. Satrucharla Sasikala Devi Government Polytechnic, Chinnamerangi" );
        polytechnics.put( "527", "Government Polytechnic, Narpala" );
        polytechnics.put( "528", "Government Polytechnic, Draksharamam" );
        polytechnics.put( "529", "Government Polytechnic, Anaparthi" );
        polytechnics.put( "530", "Government Polytechnic, Pithapuram" );
        polytechnics.put( "531", "Government Model Residential Polytechnic, Rampachodavaram" );
        polytechnics.put( "532", "Government Model Residential Polytechnic, K R Puram" );
        polytechnics.put( "533", "G.B.R Government Polytechnic, Chipurupalli" );
        polytechnics.put( "534", "Government Model Residential Polytechnic, Gummalaxmipuram" );
        polytechnics.put( "535", "Government Polytechnic, Tekkali" );
        polytechnics.put( "536", "Government Model Residential Polytechnic, Seetampet" );
        polytechnics.put( "635", "Government Polytechnic, Chodavaram" );
        polytechnics.put( "637", "Government Polytechnic, Pendurthi" );
        polytechnics.put( "012", "SMVM Polytechnic, Tanuku" );
        polytechnics.put( "019", "Sri Padmavati Women Polytechnic, Tirupati" );
        polytechnics.put( "028", "Sir CRR Polytechnic, Eluru" );
        polytechnics.put( "029", "Loyola Polytechnic, Pulivendula" );
        polytechnics.put( "030", "AANM and VVSR Polytechnic, Gudlavalleru" );
        polytechnics.put( "031", "VKR and VNB Polytechnic, Gudiwada" );
        polytechnics.put( "033", "Col. D S Raju Polytechnic, Poduru" );
        polytechnics.put( "036", "KES Polytechnic For Women, Vijayawada" );
        polytechnics.put( "037", "S V C M Polytechnic, Badvel" );
        polytechnics.put( "040", "C.R. Polytechnic, Chilakaluripet" );
        polytechnics.put( "056", "Vasavi Polytechnic, Banaganapalli" );
        polytechnics.put( "074", "Smt. TKR Polytechnic, Pamarru" );
        polytechnics.put( "089", "Al Huda Polytechnic, Nellore" );
        polytechnics.put( "091", "Tayyib Muslim Polytechnic, Kadapa" );
        polytechnics.put( "093", "Smt. B. Seetha Polytechnic, Bhimavaram" );
        polytechnics.put( "099", "T.P. Polytechnic, Bobbili" );
        polytechnics.put( "100", "Sri YVS And BRMM Polytechnic, Mukteshwaram" );
        polytechnics.put( "105", "Divi Seema Polytechnic, Avanigadda" );
        polytechnics.put( "106", "Bapatla Polytechnic, Bapatla" );
        polytechnics.put( "146", "Sai Ganapathi Polytechnic, Anandapuram" );
        polytechnics.put( "158", "Nuzvid Polytechnic, Nuzivid" );
        polytechnics.put( "159", "Shiridi Sai Dip. In Engg. and Tech, Maripivalasa" );
        polytechnics.put( "160", "A V N Polytechnic, Mudinepally" );
        polytechnics.put( "179", "Vikas Polytechnic College, Vissannapet" );
        polytechnics.put( "180", "Swamy Vivekananda Polytechnic, Bobbili" );
        polytechnics.put( "217", "Bellamkonda Polytechnic College Kambhalapadu, Kambhalapadu" );
        polytechnics.put( "218", "Akshara Polytechnic, Bondapalli" );
        polytechnics.put( "220", "The Rajiv Gandhi Recs Polytechnic, Kasimkota" );
        polytechnics.put( "221", "Narayana Polytechnic, Srikakulam" );
        polytechnics.put( "226", "Sri Chaitanya Polytechnic College, Panukulavalasa" );
        polytechnics.put( "227", "Chirala Engineering College, Chirala" );
        polytechnics.put( "229", "Kuppam Engineering College, Kuppam" );
        polytechnics.put( "240", "Malineni Suseelamma Womens Engg. College, Singarayakonda" );
        polytechnics.put( "242", "Sri Venkatesa Perumal College of Engg. and Tech, Puttur" );
        polytechnics.put( "243", "Sri Vasavi Engineering College, Tadepalligudem" );
        polytechnics.put( "245", "Sri Venkateswara College Of Engg.and Technology, Chittoor" );
        polytechnics.put( "247", "Avanthis St.Theressa Inst. Of Engg. and Tech., Cheepurupally" );
        polytechnics.put( "249", "Aditya College Of Engineering and Technology, Peddapuram" );
        polytechnics.put( "251", "Newton Institute of Engineering, Macherla" );
        polytechnics.put( "252", "Bonam Venkata Chalamaiah Inst. Of Tech. And Science, Amalapuram" );
        polytechnics.put( "255", "Aditya Engineering College, Peddapuram" );
        polytechnics.put( "258", "Swarnandhra College of Engg. and Tech, Narsapuram" );
        polytechnics.put( "260", "VRS and YRN College Of Engg. and Technology, Chirala" );
        polytechnics.put( "262", "Bit Institute Of Technology, Hindupur" );
        polytechnics.put( "263", "Gokula Krishna College Of Engineering, Sullurpet" );
        polytechnics.put( "270", "Siddharth Institute Of Engg. and Technology, Puttur" );
        polytechnics.put( "272", "Prakasam Engineering College, Kandukur" );
        polytechnics.put( "273", "Bapatla Engineering College, Bapatla" );
        polytechnics.put( "280", "Audhisankara College Of Engg. and Tech, Gudur" );
        polytechnics.put( "284", "Lenora College of Engineering, Rampachodavaram" );
        polytechnics.put( "286", "Aditya Institute Of Technology and MGMT, Tekkali" );
        polytechnics.put( "288", "Dr Samuel George Institute of Engg. and Technology, Markapur" );
        polytechnics.put( "289", "St. Anns College Of Engg. and Technology, Chirala" );
        polytechnics.put( "291", "Sri Vidya Niketan Engineering College, Rangampeta" );
        polytechnics.put( "295", "Godavari Institute Of Engg. and Tech, Rajahmundry" );
        polytechnics.put( "296", "Chaitanya Engg. College, Visakhapatnam" );
        polytechnics.put( "297", "Chaitanya Inst. Of Science and Tech, Kakinada" );
        polytechnics.put( "298", "Srinivasa Diploma In Engg. and Tech, Challam Naidu Valasa Bur" );
        polytechnics.put( "301", "Sraddha College Of Diploma In Engg.and Technology, Krishnuni Palem" );
        polytechnics.put( "302", "Aries Polytechnic College, Chittoor" );
        polytechnics.put( "307", "Sri Venkateswara Polytechnic, Srikakulam" );
        polytechnics.put( "314", "T V R Polytechnic, Mandasa" );
        polytechnics.put( "318", "Vikas Polytechnic College, Siddavatam" );
        polytechnics.put( "320", "Pydah College of Engineering, Kakinada" );
        polytechnics.put( "326", "Yalamarty College of Polytechnic, Anandapuram" );
        polytechnics.put( "327", "Balajee Polytechnic, Gajapathinagaram" );
        polytechnics.put( "328", "Behara Polytechnic, Visakhapatnam" );
        polytechnics.put( "329", "Satya Sree Parimala Polytechnic, Rajahmundry" );
        polytechnics.put( "331", "Mrs.A.V.N.College, Visakhapatnam" );
        polytechnics.put( "333", "A.M.Reddy Memorial Coll. Of Engineering, Narsaraopet" );
        polytechnics.put( "335", "Acharya College Of Engineering, Badvel" );
        polytechnics.put( "336", "Adarsh College Of Engineering, Gollaprolu" );
        polytechnics.put( "343", "Sai Rajeswari Inst Of Technology, Proddatur" );
        polytechnics.put( "345", "Bharath Coll Of Engg and Technology For Women, Kadapa" );
        polytechnics.put( "346", "Golden Valley Integrated Campus, Madanapalle" );
        polytechnics.put( "347", "BVC Engineering College, Rajahmundry" );
        polytechnics.put( "348", "Chaitanya Bharathi Institute of Technology, Pallavolu" );
        polytechnics.put( "349", "Dadi Instt. Of Engineering And Technology, Anakapalle" );
        polytechnics.put( "351", "DVR and Dr.Hs MIC College Of Technology, Kanchikacherla" );
        polytechnics.put( "352", "Dhanekula Inst Of Engg. Technology, Vijayawada" );
        polytechnics.put( "353", "Sri Chaitanya-Djr College of Engineering & Technology, Vijayawada" );
        polytechnics.put( "354", "DNR College of Engg and Tech, Bhimavaram" );
        polytechnics.put( "360", "Global College of Engg and Technology, Chenur" );
        polytechnics.put( "362", "Gokul Group Of Institutions, Bobbili" );
        polytechnics.put( "363", "Gonna Inst Of Info Technology Sciences, Visakhapatnam" );
        polytechnics.put( "366", "Guntur Engineering College, Guntur" );
        polytechnics.put( "367", "G.D.M.M. Coll Of Engg and Technology, Nandigama" );
        polytechnics.put( "371", "Kakinada Institute Of Engg. and Technology, Kakinada" );
        polytechnics.put( "372", "Kakinada Institute Of Engg. and Technology, Kakinada" );


        signbtn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                name = textInputEditTextFullName.getText().toString();
                id = textInputEditTextAdminId.getText().toString();
                code = textInputEditTextCollegeCode.getText().toString();
                mobile = textInputEditTextEmail.getText().toString();
                password = textInputEditTextEnterPassword.getText().toString();
                confirm = textInputEditTextConfirmPassword.getText().toString();
                clgname = polytechnics.get( code );
 checkpath();

            }

        } );
    }




    private boolean validatePassword() {
        String passwordInput = textInputEditTextEnterPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            Toast.makeText( SignUp.this, "Password is Empty", Toast.LENGTH_SHORT ).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher( passwordInput ).matches()) {
            textInputEditTextEnterPassword.setError( "Password is too weak" );
            Toast.makeText( SignUp.this, "Password can contain at least 1 special character, minimum 4 characters and Does not Contain any Whitespaces", Toast.LENGTH_SHORT ).show();
            return false;
        } else {
            textInputEditTextEnterPassword.setError( null );
            return true;
        }
    }

    public void Internetstatus() {
        registerReceiver( broadcastReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver( broadcastReceiver );
    }


    public void checkpath() {

        if (name.isEmpty() && id.isEmpty() && code.isEmpty() && mobile.isEmpty() && password.isEmpty() && confirm.isEmpty()) {

            Toast.makeText( SignUp.this, "All Fields are required", Toast.LENGTH_SHORT ).show();
        } else {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference( "admin" ).child(code);
            reference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText( SignUp.this, "Admin Exists", Toast.LENGTH_SHORT ).show();
                    } else {
                 insertdata();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors that may occur during the read operation
                    Toast.makeText( SignUp.this, " Database Error", Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }


    public void insertdata() {
        if (name.isEmpty() && id.isEmpty() && code.isEmpty() &&mobile.isEmpty() && password.isEmpty() && confirm.isEmpty()) {
            Toast.makeText( SignUp.this, "All fields are required", Toast.LENGTH_SHORT ).show();
        }
        else if(!isPhoneNumberValid(mobile) ){
            Toast.makeText( SignUp.this, "Please enter a valid phone number", Toast.LENGTH_SHORT ).show();

        }
           else if (password.equals( confirm )) {
                if (clgname == null) {
                    Toast.makeText( SignUp.this, "College Does not exists", Toast.LENGTH_SHORT ).show();

                } else if (validatePassword()) {

                    database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference( "admin" );
                    Admin admin = new Admin( name, id, code, mobile, password, clgname );
                    reference.child( code ).setValue( admin );
                    Toast.makeText( SignUp.this, "You have signed up successfully!", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent( SignUp.this, Login.class );
                    startActivity( intent );
                }
            }
          else{
            Toast.makeText( SignUp.this, "Password mismatch", Toast.LENGTH_SHORT ).show();
        }
    }
    public boolean isPhoneNumberValid(String phoneNumber) {
        String regexPattern = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    }

