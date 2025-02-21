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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class viewbypinno extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String br, branch, batch;
    FrameLayout frame;
    int choice;
    int f1fee, f2fee, f3fee;
    String onestatus, twostatus, threestatus;
    TextInputEditText enterpinno;
    Button view;
    String pinno;
    viewstudent viewstudent;
    String pinno1, branch1, name1, year1, clgname1, f1tution1, f1nongovt1, f2tution1, f2nongovt1, f3tution1, f3nongovt1;
    String code1;

    public viewbypinno() {
        // Required empty public constructor
    }

    public static viewbypinno newInstance(String param1, String param2) {
        viewbypinno fragment = new viewbypinno();
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
        View rootView = inflater.inflate(R.layout.fragment_viewbypinno, container, false);

        DataViewModel dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        code1 = dataViewModel.getCollegecode();
        enterpinno = rootView.findViewById(R.id.enterpinno);
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
                                    // Iterate through the results (although there should be only one)
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        viewstudent = dataSnapshot.getValue(viewstudent.class);
                                        pinno1 = dataSnapshot.child("pinno").getValue(String.class);


                                        if (pinno.equals(pinno1)) {
                                            branch1 = dataSnapshot.child("branch").getValue(String.class);
                                            name1 = dataSnapshot.child("name").getValue(String.class);
                                            year1 = dataSnapshot.child("year").getValue(String.class);
                                            clgname1 = dataSnapshot.child("clgname").getValue(String.class);

                                            viewstudent.FirstYearFees f1 = viewstudent.getFirstyearfees();
                                            f1nongovt1 = f1.getNongovtfee();
                                            f1tution1 = f1.getTutionfee();

                                            viewstudent.SecondYearFees f2 = viewstudent.getSecondyearfees();
                                            f2nongovt1 = f2.getNongovtfee();
                                            f2tution1 = f2.getTutionfee();

                                            viewstudent.ThirdYearFees f3 = viewstudent.getThirdyearfees();
                                            f3nongovt1 = f3.getNongovtfee();
                                            f3tution1 = f3.getTutionfee();

                                            TextView yearstu = rootView.findViewById(R.id.year);
                                            yearstu.setText(year1);

                                            TextView nameTextView = rootView.findViewById(R.id.name);
                                            nameTextView.setText(name1);

                                            TextView pinnoTextView = rootView.findViewById(R.id.pinno);
                                            pinnoTextView.setText(pinno1);

                                            TextView f1tution = rootView.findViewById(R.id.f1tution);
                                            f1tution.setText("2000");

                                            TextView f2tution = rootView.findViewById(R.id.f2tution);
                                            f2tution.setText("2000");

                                            TextView f3tution = rootView.findViewById(R.id.f3tution);
                                            f3tution.setText("2000");

                                            TextView f1nongovt = rootView.findViewById(R.id.f1nongovt);
                                            f1nongovt.setText("2700");

                                            TextView f2nongovt = rootView.findViewById(R.id.f2nongovt);
                                            f2nongovt.setText("2150");

                                            TextView f3nongovt = rootView.findViewById(R.id.f3nongovt);
                                            f3nongovt.setText("2150");

                                            int f1tutionfee, f1nongovtfee, f2tutionfee, f2nongovtfee, f3tutionfee, f3nongovtfee;

                                            f1tutionfee = Integer.parseInt(String.valueOf(f1tution1));
                                            f1nongovtfee = Integer.parseInt(String.valueOf(f1nongovt1));

                                            f2tutionfee = Integer.parseInt(String.valueOf(f2tution1));
                                            f2nongovtfee = Integer.parseInt(String.valueOf(f2nongovt1));

                                            f3tutionfee = Integer.parseInt(String.valueOf(f3tution1));
                                            f3nongovtfee = Integer.parseInt(String.valueOf(f3nongovt1));

                                            String Status;

                                            int Fees = f1tutionfee + f1nongovtfee + f2tutionfee + f3tutionfee + f3nongovtfee + f2nongovtfee;
                                            TextView FirstStatus = rootView.findViewById(R.id.f1status);
                                            TextView SecondStatus = rootView.findViewById(R.id.f2status);
                                            TextView ThirdStatus = rootView.findViewById(R.id.f3status);


                                            if (f1tution1.equals("0") && f1nongovt1.equals("0")) {
                                                onestatus = "Paid";
                                            } else {
                                                onestatus = "Not Paid";
                                            }
                                            if (f2tution1.equals("0") && f2nongovt1.equals("0")&&(year1.equals("2nd Year")||year1.equals("3rd Year"))) {
                                                twostatus = "Paid";
                                            } else {
                                                if(year1.equals("1st Year"))
                                                {
                                                    FirstStatus.setText("");
                                                }
                                                else {
                                                    twostatus = "Not Paid";
                                                }
                                            }
                                            if (f3tution1.equals("0") && f3nongovt1.equals("0")&&(year1.equals("3rd Year"))) {
                                                threestatus = "Paid";
                                            } else {
                                                if((year1.equals("1st Year")||year1.equals("2nd Year")))
                                                {
                                                    FirstStatus.setText("");
                                                }
                                                else {
                                                threestatus = "Not Paid";
                                                }
                                            }


                                            FirstStatus.setText(onestatus);
                                            SecondStatus.setText(twostatus);
                                            ThirdStatus.setText(threestatus);

                                            f1fee = f1tutionfee + f1nongovtfee;
                                            f2fee = f2tutionfee + f2nongovtfee;
                                            f3fee = f3tutionfee + f3nongovtfee;

                                            TextView f1due = rootView.findViewById(R.id.f1due);
                                            f1due.setText(String.valueOf(f1fee));

                                            TextView f2due = rootView.findViewById(R.id.f2due);
                                            f2due.setText(String.valueOf(f2fee));

                                            TextView f3due = rootView.findViewById(R.id.f3due);
                                            f3due.setText(String.valueOf(f3fee));

                                            int tdue = f1fee + f2fee + f3fee;

                                            TextView totalfee = rootView.findViewById(R.id.totalfee);
                                            totalfee.setText(String.valueOf(Fees));

                                            TextView totaldue = rootView.findViewById(R.id.totaldue);
                                            totaldue.setText(String.valueOf(tdue));

                                            frame = rootView.findViewById(R.id.studentdetail);
                                            frame.setVisibility(View.VISIBLE);
                                            choice = 1;
                                        }
                                    }
                                } else {
                                    if (choice == 1) {
                                        frame.setVisibility(View.INVISIBLE);
                                    }
                                    Toast.makeText(getContext(), "Student does not exist!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                if (choice == 1) {
                                    frame.setVisibility(View.INVISIBLE);
                                }
                                // Handle any errors that may occur during the read operation
                                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return rootView;
    }
}
