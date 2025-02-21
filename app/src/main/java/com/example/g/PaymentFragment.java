package com.example.g;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.g.StudentViewModel;
import com.razorpay.Checkout;
import org.json.JSONException;
import org.json.JSONObject;
public class PaymentFragment extends Fragment implements paymentresultlistener{

    private StudentViewModel studentViewModel;
    BroadcastReceiver broadcastReceiver = null;

    Button firstbutton, secondbutton, thirdbutton;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ViewModel
        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        broadcastReceiver = new InternetReceiver();
        Internetstatus();
    }

    @SuppressLint("MissingInflatedId")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        // Get data from the ViewModel
        String name = studentViewModel.getName();
        String pinno = studentViewModel.getPinno();
        String f1t=studentViewModel.getF1tution();
        String f1n=studentViewModel.getF1nongovt();
        String f2t=studentViewModel.getF2tution();
        String f2n=studentViewModel.getF2nongovt();
        String f3t=studentViewModel.getF3tution();
        String f3n=studentViewModel.getF3nongovt();
        String year=studentViewModel.getYear();

        // Set data to TextViews
        TextView nameTextView = rootView.findViewById(R.id.name);
        nameTextView.setText(name);

        TextView pinnoTextView = rootView.findViewById(R.id.pinno);
        pinnoTextView.setText(pinno);

       TextView f1tution = rootView.findViewById(R.id.f1tution);
        f1tution.setText(f1t);

        TextView f2tution = rootView.findViewById(R.id.f2tution);
        f2tution.setText(f2t);

        TextView f3tution = rootView.findViewById(R.id.f3tution);
       f3tution.setText(f3t);

        TextView f1nongovt = rootView.findViewById(R.id.f1nongovt);
       f1nongovt.setText(f1n);

        TextView f2nongovt = rootView.findViewById(R.id.f2nongovt);
        f2nongovt.setText(f2n);

        TextView f3nongovt = rootView.findViewById(R.id.f3nongovt);
        f3nongovt.setText(f3n);

        firstbutton = rootView.findViewById(R.id.firstbutton);
        secondbutton = rootView.findViewById(R.id.secondbutton);
        thirdbutton = rootView.findViewById(R.id.thirdbutton);

        String onestatus="", twostatus="", threestatus="";


        viewstudent user=new viewstudent();

        viewstudent.FirstYearFees f1 = user.getFirstyearfees();
        viewstudent.SecondYearFees f2 = user.getSecondyearfees();
        viewstudent.ThirdYearFees f3 = user.getThirdyearfees();




        int f1tutionfee,f1nongovtfee,f2tutionfee,f2nongovtfee,f3tutionfee,f3nongovtfee;

        f1tutionfee = Integer.parseInt( String.valueOf( f1t) );
        f1nongovtfee = Integer.parseInt( String.valueOf( f1n) );


        f2tutionfee = Integer.parseInt( String.valueOf( f2t ) );
        f2nongovtfee = Integer.parseInt( String.valueOf( f2n ) );

        f3tutionfee = Integer.parseInt( String.valueOf( f3t ) );
        f3nongovtfee = Integer.parseInt( String.valueOf( f3n ) );

        String Status;

        int Fees = f1tutionfee+f1nongovtfee+f2tutionfee+f3tutionfee+f3nongovtfee+f2nongovtfee;

        TextView SecondStatus = rootView.findViewById(R.id.f2status);
        TextView FirstStatus = rootView.findViewById(R.id.f1status);
        TextView ThirdStatus = rootView.findViewById(R.id.f3status);

        // Check the fees status and set button visibility
        if (f1t.equals("0")&&f1n.equals("0")) {
            onestatus = "Paid";
            firstbutton.setVisibility(View.GONE);
        } else {
            // first button payment
            firstbutton.setVisibility(View.VISIBLE);
            onestatus = "Not Paid";

        }
        if (f2t.equals("0")&&f2n.equals("0")) {
            secondbutton.setVisibility(View.GONE);
            if(year.equals("1st Year")||year.equals("2nd year")) {
                twostatus = "Paid";
            }
        } else {
            //second year fees
            secondbutton.setVisibility(View.VISIBLE);
            if(year.equals("3rd Year"))
            {
              SecondStatus.setText("");
            }
            else {
            twostatus = "Not Paid";
            }

        }
        if (f3t.equals("0")&&f3n.equals("0")) {
            thirdbutton.setVisibility(View.GONE);
            if(year.equals("3rd Year")) {
                threestatus = "Paid";
            }
        } else {
            thirdbutton.setVisibility(View.VISIBLE);
            if((year.equals("1st Year")||year.equals("2nd Year")))
            {
                ThirdStatus.setText("");
            }
            else {
                threestatus = "Not Paid";
            }

        }


        FirstStatus.setText(onestatus);

        SecondStatus.setText(twostatus);

        ThirdStatus.setText(threestatus);


        firstbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "rzp_test_TSwore7d5xkSZE";
                String amount = "4700";

                int value = Math.round(Float.parseFloat(amount) * 100);
                Checkout Checkout = new Checkout();
                Checkout.setKeyID(key);
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", "feeSavvy");
                    object.put("description", "1st Year fees"); // Corrected typo
                    object.put("currency", "INR");
                    object.put("amount", String.valueOf(value)); // Corrected value to use the calculated value
                    object.put("send_sms_hash", true); // Corrected to use a boolean value

                    JSONObject prefill = new JSONObject();
                    prefill.put("email", "dkirankumar617@gmail.com");
                    prefill.put("contact", "9392793217");

                    object.put("prefill", prefill);

                    Checkout.open(getActivity(), object); // Corrected to use MainActivity.this

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );


        secondbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "rzp_test_TSwore7d5xkSZE";
                String amount = "4150";

                int value = Math.round(Float.parseFloat(amount) * 100);
                Checkout Checkout = new Checkout();
                Checkout.setKeyID(key);
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", "feeSavvy");
                    object.put("description", "2nd Year fees"); // Corrected typo
                    object.put("currency", "INR");
                    object.put("amount", String.valueOf(value)); // Corrected value to use the calculated value
                    object.put("send_sms_hash", true); // Corrected to use a boolean value

                    JSONObject prefill = new JSONObject();
                    prefill.put("email", "dkirankumar617@gmail.com");
                    prefill.put("contact", "9392793217");

                    object.put("prefill", prefill);

                    Checkout.open(getActivity(), object); // Corrected to use MainActivity.this

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );
        thirdbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "rzp_test_TSwore7d5xkSZE";
                String amount = "4150";

                int value = Math.round(Float.parseFloat(amount) * 100);
                Checkout Checkout = new Checkout();
                Checkout.setKeyID(key);
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", "feeSavvy");
                    object.put("description", "3rd Year fees"); // Corrected typo
                    object.put("currency", "INR");
                    object.put("amount", String.valueOf(value)); // Corrected value to use the calculated value
                    object.put("send_sms_hash", true); // Corrected to use a boolean value

                    JSONObject prefill = new JSONObject();
                    prefill.put("email", "dkirankumar617@gmail.com");
                    prefill.put("contact", "9392793217");

                    object.put("prefill", prefill);

                    Checkout.open(getActivity(), object); // Corrected to use MainActivity.this

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );


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
    public void onPaymentSuccess (String s){
        Toast.makeText(getContext(), "payment success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError ( int i, String s){
        Toast.makeText(getContext(), "payment failed", Toast.LENGTH_SHORT).show();
    }


}
