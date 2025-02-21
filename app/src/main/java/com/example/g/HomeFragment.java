package com.example.g;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.time.LocalDate;
import java.time.Month;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Objects;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private DataViewModel dataViewModel;

    FirebaseDatabase database;
    String code1;
    String pinno2;
    boolean isCheckBoxChecked = false;


    BroadcastReceiver broadcastReceiver = null;
    DatabaseReference reference;
    Button button;
    private TextInputEditText name,pinno;
    String[] items = {"DCME", "DECE", "DEEE", "DME"};
    String[] item1 = {"1st Year", "2nd Year", "3rd Year"};
    String[] item2 = {"Normal Admission","Spot Admission"};
    String it1,it2,it3;
    String batch;
    String name2;
    String clgname;
    int choice;
    Calendar calendar = Calendar.getInstance();
    String Br;
    firstyearfees f1;
    secondyearfees f2;
    thirdyearfees f3;
    CheckBox myCheckBox;
    String pinnumber;

    AutoCompleteTextView autoCompletetxt1, autoCompletetxt2,autoCompletetxt3;
    ArrayAdapter<String> adapterItems1, adapterItems2,adapterItems3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        broadcastReceiver = new InternetReceiver();
        Internetstatus();

        name = rootView.findViewById(R.id.name);
        pinno = rootView.findViewById(R.id.Pinno);

        button=rootView.findViewById(R.id.button2);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        String name1 = dataViewModel.getName();
        code1 = dataViewModel.getCollegecode();
        String id1 = dataViewModel.getId();
        String password1= dataViewModel.getPassword();
        String mobile1 = dataViewModel.getMobile();
        clgname=dataViewModel.getClgname();


        autoCompletetxt1 = rootView.findViewById(R.id.auto_complete_txt1);
        autoCompletetxt2 = rootView.findViewById(R.id.auto_complete_txt2);
        autoCompletetxt3 = rootView.findViewById(R.id.auto_complete_txt3);
        adapterItems1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, item1);
        adapterItems2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, items);
        adapterItems3 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, item2);
        autoCompletetxt1.setAdapter(adapterItems1);
        autoCompletetxt2.setAdapter(adapterItems2);
        autoCompletetxt3.setAdapter(adapterItems3);




        autoCompletetxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it1 = parent.getItemAtPosition(position).toString();

            }
        });

        autoCompletetxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it2= parent.getItemAtPosition(position).toString();

            }
        });
        autoCompletetxt3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it3= parent.getItemAtPosition(position).toString();
            }
        });




        // Set an OnClickListener for the CheckBox
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name2 = name.getText().toString();
                pinno2 = pinno.getText().toString();
                checkUser();

            }
        });


        return rootView;
    }
    void batch() {
        if (pinno2 != null && pinno2.length() >= 2) {
            batch = pinno2.substring(0, 2) + "-Batch";
        }
    }
    void Insertdata()

    {

        if(it3.equals("Normal Admission")) {
            if (it1.equals("1st Year")) {
                f1 = new firstyearfees("2000", "2700");
                f2 = new secondyearfees("0", "0");
                f3 = new thirdyearfees("0", "0");
            } else if (it1.equals("2nd Year")) {
                f1 = new firstyearfees("0", "0");
                f2 = new secondyearfees("2000", "2150");
                f3 = new thirdyearfees("0", "0");
            } else if (it1.equals("3rd Year")) {
                f1 = new firstyearfees("0", "0");
                f2 = new secondyearfees("0", "0");
                f3 = new thirdyearfees("2000", "2150");
            }
        }
        else if(it3.equals("Spot Admission"))
        {
            if (it1.equals("1st Year")) {
                f1 = new firstyearfees("3000", "2700");
                f2 = new secondyearfees("0", "0");
                f3 = new thirdyearfees("0", "0");
            } else if (it1.equals("2nd Year")) {
                f1 = new firstyearfees("0", "0");
                f2 = new secondyearfees("3000", "2150");
                f3 = new thirdyearfees("0", "0");
            } else if (it1.equals("3rd Year")) {
                f1 = new firstyearfees("0", "0");
                f2 = new secondyearfees("0", "0");
                f3 = new thirdyearfees("3000", "2150");
            }
        }

        if (it2.equals("DCME")) {
            if (pinno2.length() == 12) {
                Br = "-CM-";
                pinnumber=pinno2.substring(5,9);
            }
        } else if (pinno2.length() == 11) {
            if (pinno2.length() == 11) {
                Br = "-M-";
                pinnumber=pinno2.substring(5,8);
            }
        } else if (it2.equals("DECE")) {
            if (pinno2.length() == 12) {
                Br = "-EC-";
                pinnumber=pinno2.substring(5,9);
            }
        }
        else {
            if (pinno2.length() == 12) {
                Br = "-EE-";
                pinnumber=pinno2.substring(5,9);
            }
        }

        String pincode=pinno2.substring(2,5);


        if(!pincode.equals(code1))
        {
            Toast.makeText( getActivity(),"This Pin Number is not belongs to this College",Toast.LENGTH_SHORT ).show();
        }
        else if(!isValidPin(pinno2)) {
            pinno.setError("Format of Pinno is not valid. Syn:YYClC-BR-NUM Eg:21101-CM-065" );
        }
        else if(name2.length()<3){
            name.setError("Name must Contain minimum 3 Characters");
        }

        else if(!pinnumber.contains(Br)){

            pinno.setError("Pin Number should be matched with Branch");
            autoCompletetxt2.setError("Branch should be matched with Pin Number");
        }

        else {
            pinno.setError(null);
            autoCompletetxt2.setError(null);

            int currentYear = calendar.get(Calendar.YEAR);

            String firstyearfees="0",secondyearfees="0",thirdyearfees="0";
            String subject="0";
            String feedback="0";




            String notification = "";

            database = FirebaseDatabase.getInstance();
            reference = database.getReference( "admin" ).child( code1 );

            Student student = new Student( name2, pinno2, it2, it1, firstyearfees, secondyearfees, thirdyearfees ,subject,feedback,clgname);
            reference.child( "Student" ).child( it2 ).child( batch ).child( pinno2 ).setValue( student );

            reference.child( "Student" ).child( it2 ).child( batch ).child( pinno2 ).child("firstyearfees").setValue(f1);
            reference.child( "Student" ).child( it2 ).child( batch ).child( pinno2 ).child("secondyearfees").setValue(f2);
            reference.child( "Student" ).child( it2 ).child( batch ).child( pinno2 ).child("thirdyearfees").setValue(f3);
            reference.child( "Student" ).child( it2 ).child( batch ).child( pinno2 ).child("isDeleted").setValue(false);

            Toast.makeText( getActivity(), " Student Successfully Added", Toast.LENGTH_SHORT ).show();
            refreshFragment();

        }


    }
    public static boolean isValidPin(String pin) {
        // Define a regular expression pattern to match the PIN format
        String pattern = "\\d{5}-[A-Z]{1,2}-\\d{3}";


        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(pin);
        return matcher.matches();
    }
    public void refreshFragment()
    {
        name.setText("");
        pinno.setText("");
        autoCompletetxt1.setText("");
        autoCompletetxt2.setText("");

    }
    public void checkUser() {
        if ((name2 == null || name2.isEmpty()) || (pinno2 == null || pinno2.isEmpty()) || (it1 == null || it1.isEmpty()) || (it2 == null || it2.isEmpty()) || (it3 == null || it3.isEmpty())) {
            Toast.makeText(getActivity(), "All fields are Required", Toast.LENGTH_SHORT).show();
        }else {
            batch();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "admin" ).child( code1 ).child( "Student" ).child( it2 ).child( batch );

            Query checkUserDatabase = reference.orderByChild( "pinno" ).equalTo( pinno2 );

            checkUserDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Iterate through the results (although there should be only one)
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String pinno = dataSnapshot.child( "pinno" ).getValue( String.class );
                            if (pinno != null && pinno.equals( pinno2 )) {
                                Toast.makeText( getActivity(), "Pin Number Exists", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } else {
                        Insertdata();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    Toast.makeText( requireContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }

    public void Internetstatus() {
        // Register the BroadcastReceiver to monitor connectivity changes
        Context context = getContext();
        if (context != null) {
            context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver when the fragment is paused
        Context context = getContext();
        if (context != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }



}




