package com.example.g;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detainstudent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detainstudent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String br, branch, batch;
    String pinno1;
    TextInputEditText enterpinno;
    Button view;
    String pinno;
    viewstudent viewstudent;
    String code1;
    private String mParam2;

    public detainstudent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detainstudent.
     */
    // TODO: Rename and change types and number of parameters
    public static detainstudent newInstance(String param1, String param2) {
        detainstudent fragment = new detainstudent();
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
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_detainstudent, container, false);

        DataViewModel dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        code1 = dataViewModel.getCollegecode();
        enterpinno = rootView.findViewById(R.id.enterpinno);
      //detain a student button
        view = rootView.findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinno = enterpinno.getText().toString();

                if (TextUtils.isEmpty(pinno)) {
                    Toast.makeText(getContext(), "Please enter Student Pin Number", Toast.LENGTH_SHORT).show();
                } else {
                    if (pinno.length() == 12) {
                        br = pinno.substring(6, 8);
                    } else if (pinno.length() == 11) {
                        br = pinno.substring(6, 7);
                    }
                    if ("CM".equals(br)) {
                        branch = "DCME";
                    } else if ("EC".equals(br)) {
                        branch = "DECE";
                    } else if ("EE".equals(br)) {
                        branch = "DEEE";
                    } else if ("M".equals(br)) {
                        branch = "DME";
                    }
                    batch = pinno.substring(0, 2) + "-Batch";

                    if (!TextUtils.isEmpty(code1) && !TextUtils.isEmpty(branch) && !TextUtils.isEmpty(batch)) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("admin").child(code1).child("Student").child(branch).child(batch);
                        Query checkUserDatabase = reference.orderByChild("pinno").equalTo(pinno);

                        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        viewstudent = dataSnapshot.getValue(viewstudent.class);
                                        pinno1 = dataSnapshot.child("pinno").getValue(String.class);
                                        boolean isDeleted = dataSnapshot.child("isDeleted").getValue(Boolean.class);
                                        if (pinno.equals(pinno1)) {
                                            if (!isDeleted) {
                                                // Update the 'isDeleted' field to true
                                                DatabaseReference studentRef = dataSnapshot.getRef();
                                                studentRef.child("isDeleted").setValue(true);
                                            } else {
                                                Toast.makeText(getContext(), "Student is already deleted.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Student does not exist!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Student does not exist!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                                Toast.makeText(getContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "error in pin number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });






    return rootView;
    }
}