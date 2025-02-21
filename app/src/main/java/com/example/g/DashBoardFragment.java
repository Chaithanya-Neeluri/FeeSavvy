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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    BroadcastReceiver broadcastReceiver = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String pinno,batch1;
    private String mParam2;
    StudentViewModel studentViewModel;
    TextInputEditText sub,feedback;
    Button submit;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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

            broadcastReceiver = new InternetReceiver();
            Internetstatus();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView= inflater.inflate(R.layout.fragment_dash_board, container, false);

         submit=rootView.findViewById(R.id.submit);

         sub=rootView.findViewById(R.id.sub);

         feedback=rootView.findViewById(R.id.feedback);


        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        String branch = studentViewModel.getBranch();

        String name = studentViewModel.getName();
        pinno= studentViewModel.getPinno();

        String year = studentViewModel.getYear();
        String batch = studentViewModel.getBatch();
        String code = studentViewModel.getCode();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                batch();

                String subject=sub.getText().toString().trim();
                String feed=feedback.getText().toString().trim();

                if(subject.isEmpty()&&feed.isEmpty())
                {
                    Toast.makeText(getActivity(), "All fields are required to send the feedback", Toast.LENGTH_SHORT).show();
                }
                else {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("admin").child(code).child("Student").child(branch).child(batch1).child(pinno);
                    reference.child("subject").setValue(subject)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    reference.child("feedback").setValue(feed)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    refreshFragment();
                                    Toast.makeText(getActivity(), "feedback sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Try again..1", Toast.LENGTH_SHORT).show();
                                }
                            });

                }


            }
        });


        return rootView;
    }
    void batch()
    {
        String bat=pinno.substring( 0,2 );
        batch1=bat+"-Batch";
    }

    public void Internetstatus() {
        // Register the BroadcastReceiver to monitor connectivity changes
        Context context = getContext();
        if (context != null) {
            context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    public void refreshFragment()
    {
        sub.setText("");
        feedback.setText("");

    }

  

}