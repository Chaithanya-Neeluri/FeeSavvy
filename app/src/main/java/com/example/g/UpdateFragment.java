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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String[] item1 = {"2nd Year", "3rd Year"};
    String[] items = {"DCME", "DECE", "DEEE", "DME"};
    BroadcastReceiver broadcastReceiver = null;
    FirebaseDatabase database;
    firstyearfees f1;
    secondyearfees f2;
    thirdyearfees f3;
    String it1, it2, it3;
    DatabaseReference reference;
    String code1;
    TextInputLayout textInput;
    String fees, yearname;
    int choice;
    Button bypinno,viewpinno,bybatch;
    TextInputEditText enterpinno;
    String Pinno;

    AutoCompleteTextView autoCompletetxt1, autoCompletetxt2, autoCompletetxt3;
    ArrayAdapter<String> adapterItems1, adapterItems2, adapterItems3;

    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_update, container, false );

        int currentYear = Calendar.getInstance().get( Calendar.YEAR );
        String lastTwoDigits = String.valueOf( currentYear % 100 );
        int curyear = Integer.parseInt( lastTwoDigits );
        ArrayList<String> stringList = new ArrayList<String>();
        for (int i = 10; i >= 0; i--) {
            stringList.add( curyear + "-Batch" );
            curyear--;
        }

        autoCompletetxt1 = rootView.findViewById( R.id.auto_complete_txt2 );

        adapterItems1 = new ArrayAdapter<>( requireActivity(), android.R.layout.simple_dropdown_item_1line, stringList );
        autoCompletetxt1.setAdapter( adapterItems1 );

        autoCompletetxt1.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it1 = parent.getItemAtPosition( position ).toString(); // Batch
            }
        } );

        autoCompletetxt2 = rootView.findViewById( R.id.auto_complete_txt1 );

        adapterItems2 = new ArrayAdapter<>( requireActivity(), android.R.layout.simple_dropdown_item_1line, item1 );
        autoCompletetxt2.setAdapter( adapterItems2 );

        autoCompletetxt2.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it2 = parent.getItemAtPosition( position ).toString(); // Year
            }
        } );

        autoCompletetxt3 = rootView.findViewById( R.id.auto_complete_txt3 );

        adapterItems3 = new ArrayAdapter<>( requireActivity(), android.R.layout.simple_dropdown_item_1line, items );
        autoCompletetxt3.setAdapter( adapterItems3 );

        autoCompletetxt3.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it3 = parent.getItemAtPosition( position ).toString(); // Branch
            }
        } );

        DataViewModel dataViewModel = new ViewModelProvider( requireActivity() ).get( DataViewModel.class );
        String name1 = dataViewModel.getName();
        code1 = dataViewModel.getCollegecode();


        Button viewButton = rootView.findViewById( R.id.view );
        viewButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                checkpath();
            }
        } );

        bypinno=rootView.findViewById(R.id.bypinno);
        viewpinno= rootView.findViewById(R.id.viewpinno);
        bybatch=rootView.findViewById(R.id.bybatch);
        enterpinno=rootView.findViewById(R.id.enterpinno);
        textInput=rootView.findViewById(R.id.textInput);


        bypinno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pinno=enterpinno.getText().toString().trim();
                // visible enterpinno,viewpinno,bybatch
                // and invisible the viewButton,bypinno
                textInput.setVisibility(View.VISIBLE);
                viewpinno.setVisibility(View.VISIBLE);
                bybatch.setVisibility(View.VISIBLE);
                viewButton.setVisibility(View.INVISIBLE);
                bypinno.setVisibility(View.INVISIBLE);

            }
        });


        bybatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // invisible enterpinno,viewpinno,bybatch
                // and visible the viewButton,bypinno
                textInput.setVisibility(View.INVISIBLE);
                viewpinno.setVisibility(View.INVISIBLE);
                bybatch.setVisibility(View.INVISIBLE);
                viewButton.setVisibility(View.VISIBLE);
                bypinno.setVisibility(View.VISIBLE);
            }
        });
        
     viewpinno.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //viewbypinno

        checkpath1();

    }
   });




        return rootView;
    }

    public void updateChild() {

     if(it2=="2nd Year")
        {
            f1=new firstyearfees("0","0");
            f2=new secondyearfees("2000","2150");
            f3=new thirdyearfees("0","0");
        }
        else
        {
            f1=new firstyearfees("0","0");
            f2=new secondyearfees("0","0");
            f3=new thirdyearfees("2000","2150");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( it3 ).child( it1 );

        reference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String childKey = childSnapshot.getKey();
                    String year = String.valueOf( childSnapshot.child( "year" ).getValue() );
                    int y2 = Integer.parseInt( year.substring( 0, 1 ) );
                    int y20 = Integer.parseInt( it2.substring( 0, 1 ) );
                    boolean isDeleted = (boolean) childSnapshot.child( "isDeleted" ).getValue();
                    if(!isDeleted) {
                        if (y20 <= y2) {
                            Toast.makeText(getActivity(), "You have selected an invalid year", Toast.LENGTH_SHORT).show();

                        } else {
                         if (it2 == "2nd Year") {
                                refreshFragment();
                                childSnapshot.getRef().child("secondyearfees").setValue(f2);
                                childSnapshot.getRef().child("year").setValue(it2);
                                Toast.makeText(getContext(),"Batch Updated to second year Successfully",Toast.LENGTH_SHORT).show();

                            } else {
                                refreshFragment();
                                childSnapshot.getRef().child("thirdyearfees").setValue(f3);
                                childSnapshot.getRef().child("year").setValue(it2);
                                Toast.makeText(getContext(),"Batch Updated to third year Successfully",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        } );

    }

    public void Internetstatus() {
        Context context = getContext();
        if (context != null) {
            context.registerReceiver( broadcastReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Context context = getContext();
        if (context != null) {
            context.unregisterReceiver( broadcastReceiver );
        }
    }

    public void checkpath() {
        if ((it1 == null || it1.isEmpty()) || (it2 == null || it2.isEmpty())) {

            Toast.makeText( getActivity(), "All fields are Required", Toast.LENGTH_SHORT ).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( it3 ).child( it1 );
            reference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        updateChild();
                    } else {
                        Toast.makeText( getContext(), "No Such students present", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText( getContext(), "Database Error", Toast.LENGTH_SHORT ).show();
                }
            } );
        }


        }


    public void checkpath1() {

        Pinno=enterpinno.getText().toString().trim();
        if ((it1 == null || it1.isEmpty()) || (it2 == null || it2.isEmpty())||(Pinno==null||Pinno.isEmpty())) {
            Toast.makeText( getActivity(), "All fields are Required", Toast.LENGTH_SHORT ).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( it3 ).child( it1 ).child(Pinno);
            reference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        updateChild1();
                    } else {
                        Toast.makeText( getContext(), "Pin Number does not Exits", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText( getContext(), "Database Error", Toast.LENGTH_SHORT ).show();
                }
            } );
        }

    }

    public void refreshFragment()
    {
        autoCompletetxt1.setText("");
        autoCompletetxt2.setText("");
        autoCompletetxt3.setText("");
        enterpinno.setText("");

    }

    public void updateChild1() {


      if(it2=="2nd Year")
        {
            f1=new firstyearfees("0","0");
            f2=new secondyearfees("2000","2150");
            f3=new thirdyearfees("0","0");
        }
        else
        {
            f1=new firstyearfees("0","0");
            f2=new secondyearfees("0","0");
            f3=new thirdyearfees("2000","2150");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference( "admin" ).child( code1 ).child( "Student" ).child( it3 ).child( it1 );

        reference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String childKey = childSnapshot.getKey();
                    String pinno1 =  String.valueOf( childSnapshot.child( "pinno" ).getValue() );
                    boolean isDeleted =(boolean) childSnapshot.child( "isDeleted" ).getValue();

                    if (Pinno.equals(pinno1)) {
                    if(!isDeleted) {
                        String year = String.valueOf(childSnapshot.child("year").getValue());
                        int y2 = Integer.parseInt(year.substring(0, 1));
                        int y20 = Integer.parseInt(it2.substring(0, 1));

                        if (y20 <= y2) {
                            Toast.makeText(getActivity(), "You have selected an invalid year", Toast.LENGTH_SHORT).show();

                        } else {
                          if (it2 == "2nd Year") {
                                refreshFragment();
                                childSnapshot.getRef().child("secondyearfees").setValue(f2);
                                childSnapshot.getRef().child("year").setValue(it2);
                            } else {
                                refreshFragment();
                                childSnapshot.getRef().child("thirdyearfees").setValue(f3);
                                childSnapshot.getRef().child("year").setValue(it2);
                            }

                        }
                    }
                    else {
                        Toast.makeText(getContext(),"Cannot update a Detained Student",Toast.LENGTH_SHORT).show();
                    }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        } );

    }


}
