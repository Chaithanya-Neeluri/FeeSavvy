package com.example.g;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;




import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    BroadcastReceiver broadcastReceiver = null;
    String selectedOption;

    int choice;
    String branch="DCME";
    String code1;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    FirebaseDatabase database;
    DatabaseReference reference;




    AutoCompleteTextView autoCompletetxt1;
    String it1;
    Button view;


    ArrayAdapter<String> adapterItems1;

    RecyclerView recyclerView;

    MyAdapter myAdapter;

    ArrayList<viewstudent> list;
    int selectedRadioButtonId;
    private String mParam2;
    String[] item1 = {"1st Year", "2nd Year", "3rd year"};

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String lastTwoDigits = String.valueOf(currentYear % 100);
        int curyear=Integer.parseInt(lastTwoDigits);
        ArrayList<String> stringList = new ArrayList<String>();
        for(int i=5;i>=0;i--)
        {
            stringList.add(curyear+"-Batch");
            curyear--;

        }

        autoCompletetxt1 = rootView.findViewById(R.id.auto_complete_txt2);

        adapterItems1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, stringList);
        autoCompletetxt1.setAdapter(adapterItems1);


        autoCompletetxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it1 = parent.getItemAtPosition(position).toString();

            }
        });

        recyclerView = rootView.findViewById(R.id.userList);
        view=rootView.findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup);
                    selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                    if (selectedRadioButtonId != -1) {
                        RadioButton selectedRadioButton = rootView.findViewById(selectedRadioButtonId);
                        selectedOption = selectedRadioButton.getText().toString();

                    }


        DataViewModel dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        String name1 = dataViewModel.getName();
        code1 = dataViewModel.getCollegecode();
        String id1 = dataViewModel.getId();
        String password1= dataViewModel.getPassword();
        String mobile1 = dataViewModel.getMobile();

                list = new ArrayList<>();
                myAdapter = new MyAdapter(getActivity(), list);
                myAdapter.setStringData(selectedOption);
                recyclerView.setAdapter(myAdapter);

        checkpath();

            }
        });



        return rootView;
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
    public void checkpath()
    {
        if(it1==null)
        {
            Toast.makeText( getContext(), "please select the batch", Toast.LENGTH_SHORT ).show();

        }
        else if (selectedRadioButtonId != -1)
        {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( branch ).child( it1 );
                reference.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // onDataChange is called when data at the specified path exists
                        if (dataSnapshot.exists()) {
                            viewdata();
                        } else {
                            if (list == null) {
                                list.clear();
                                myAdapter.notifyDataSetChanged();

                            }
                            Toast.makeText( getContext(), "There are no such specified students found", Toast.LENGTH_SHORT ).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that may occur during the read operation
                        Toast.makeText( getContext(), " Database Error", Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        else {
            Toast.makeText( getContext(), "Please check the radio button", Toast.LENGTH_SHORT ).show();

        }
        }


    public void viewdata()
    {


        if(it1.isEmpty()&&selectedOption.isEmpty())
        {
            Toast.makeText(getContext(),"All Fields are required",Toast.LENGTH_SHORT).show();
        }  else {

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("admin").child(code1);
            reference = reference.child("Student").child(branch).child(it1);


            // Initialize the list and adapter


            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Set up ValueEventListener to fetch user data
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Clear the list before adding new data

                    // Iterate through the data snapshot and populate the list with user objects
                    for (DataSnapshot DS : snapshot.getChildren()) {
                        viewstudent viewstudent = DS.getValue(viewstudent.class);

                        viewstudent.FirstYearFees f1 = viewstudent.getFirstyearfees();
                        String f1nongovt = f1.getNongovtfee();
                        String f1tution = f1.getTutionfee();

                        viewstudent.SecondYearFees f2 = viewstudent.getSecondyearfees();
                        String f2nongovt = f2.getNongovtfee();
                        String f2tution = f2.getTutionfee();

                        viewstudent.ThirdYearFees f3 = viewstudent.getThirdyearfees();
                        String f3nongovt = f3.getNongovtfee();
                        String f3tution = f3.getTutionfee();



                        if (selectedOption.equals("Paid")) {
                            if ((f1tution.equals("0")||f1nongovt.equals("0")) && (f2tution.equals("0")||f2nongovt.equals("0")) && (f3tution.equals("0")||f3nongovt.equals("0"))) {
                                list.add(viewstudent);
                            }
                        } else if (selectedOption.equals("NotPaid")) {
                            if ((f1tution.equals("0")||f1nongovt.equals("0")) && (f2tution.equals("0")||f2nongovt.equals("0")) && (f3tution.equals("0")||f3nongovt.equals("0"))) {

                            } else {
                                list.add(viewstudent);
                            }
                        } else if (selectedOption.equals("All")) {
                            list.add(viewstudent);
                        }
                    }
                    // Notify the adapter of changes in the data so that the list is updated
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    // Handle database error, if needed
                }
            });
        }

    }


}