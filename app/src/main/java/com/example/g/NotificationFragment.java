package com.example.g;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    BroadcastReceiver broadcastReceiver = null;
    DatabaseReference reference;
    AutoCompleteTextView autoCompletetxt1, autoCompletetxt2;

    String it1, it2;
    Button view;
    ArrayAdapter<String> adapterItems1, adapterItems2;

    RecyclerView recyclerView;

    NotifyAdapter notifyAdapter;
    String code1;

    ArrayList<viewstudent> list;
    private String mParam2;
    FirebaseDatabase database;

    int choice;
    String[] item = {"DCME", "DECE", "DEEE", "DME"};
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);


        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String lastTwoDigits = String.valueOf(currentYear % 100);
        int curyear = Integer.parseInt(lastTwoDigits);
        ArrayList<String> stringList = new ArrayList<String>();
        for (int i = 10; i >= 0; i--) {
            stringList.add(curyear + "-Batch");
            curyear--;
        }

        autoCompletetxt2 = rootView.findViewById(R.id.auto_complete_txt2);

        adapterItems2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, stringList);
        autoCompletetxt2.setAdapter(adapterItems2);

        autoCompletetxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it1 = parent.getItemAtPosition(position).toString(); // Batch
            }
        });

        autoCompletetxt1 = rootView.findViewById(R.id.auto_complete_txt1);

        adapterItems1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, item);
        autoCompletetxt1.setAdapter(adapterItems1);

        autoCompletetxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it2 = parent.getItemAtPosition(position).toString(); // Branch
            }
        });

        recyclerView = rootView.findViewById(R.id.userList);
        view = rootView.findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataViewModel dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
                String name1 = dataViewModel.getName();
                code1 = dataViewModel.getCollegecode();
                String id1 = dataViewModel.getId();
                String password1 = dataViewModel.getPassword();
                String mobile1 = dataViewModel.getMobile();
                list = new ArrayList<>();
                notifyAdapter = new NotifyAdapter(getActivity(), list);
                recyclerView.setAdapter(notifyAdapter);
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
            Toast.makeText( getContext(), "Please select the batch", Toast.LENGTH_SHORT ).show();

        }
        else if(it2==null){
            Toast.makeText( getContext(), "please select the branch", Toast.LENGTH_SHORT ).show();

        }
      else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( it2).child( it1 );
            reference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // onDataChange is called when data at the specified path exists
                    if (dataSnapshot.exists()) {
                        viewdata();
                    } else {
                        if (list == null) {
                            list.clear();
                            notifyAdapter.notifyDataSetChanged();

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
    }



    public void viewdata()
    {



        if (it1.isEmpty() && it2.isEmpty()) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();

        } else {

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("admin").child(code1);
            reference = reference.child("Student").child(it2).child(it1);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Set up ValueEventListener to fetch user data
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Clear the list before adding new data

                    // Iterate through the data snapshot and populate the list with user objects
                    for (DataSnapshot DS : snapshot.getChildren()) {

                        viewstudent student = DS.getValue(viewstudent.class);
                        String subject, feedback;
                        subject = student.getSubject();
                        feedback = student.getFeedback();
                        if (subject.equals("0") && feedback.equals("0")) {
                            if(list.isEmpty())
                            {
                                Toast.makeText(getContext(),"No Notifications Found",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            list.add( student );
                        }

                    }
                    notifyAdapter.notifyDataSetChanged();
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