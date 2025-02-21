package com.example.g;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    String myString;
    Context context;


    public MyAdapter(Context context, ArrayList<viewstudent> list) {
        this.context = context;
        this.list = list;



    }
    public void setStringData(String data) {
        this.myString = data;
    }

    ArrayList<viewstudent> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        viewstudent user = list.get(position);
        viewstudent.FirstYearFees f1 = user.getFirstyearfees();
        viewstudent.SecondYearFees f2 = user.getSecondyearfees();
        viewstudent.ThirdYearFees f3 = user.getThirdyearfees();
        String f1tution,f1nongovt,f2tution,f2nongovt,f3tution,f3nongovt;

        f1tution=f1.getTutionfee();
        f1nongovt=f1.getNongovtfee();

        f2tution=f2.getTutionfee();
        f2nongovt=f2.getNongovtfee();

        f3tution=f3.getTutionfee();
        f3nongovt=f3.getNongovtfee();


        int f1tutionfee,f1nongovtfee,f2tutionfee,f2nongovtfee,f3tutionfee,f3nongovtfee;

        f1tutionfee = Integer.parseInt(f1tution);
        f1nongovtfee = Integer.parseInt(f1nongovt);

        f2tutionfee = Integer.parseInt(f2tution);
        f2nongovtfee = Integer.parseInt(f2nongovt);

        f3tutionfee = Integer.parseInt(f3tution);
        f3nongovtfee = Integer.parseInt(f3nongovt);

        String Status;

        int Fees = f1tutionfee+f1nongovtfee+f2tutionfee+f3tutionfee+f3nongovtfee+f2nongovtfee;

        String finalfees = Integer.toString(Fees);

        if ((f1tution.equals("0")||f1nongovt.equals("0")) && (f2tution.equals("0")||f2nongovt.equals("0")) && (f3tution.equals("0")||f3nongovt.equals("0"))) {

            Status = "Paid";
        } else {
            Status = "NotPaid";
        }


        holder.Name.setText(user.getName());
        holder.Pinno.setText(user.getPinno());
        holder.Year.setText(user.getYear());
        holder.fees.setText(finalfees);
        holder.status.setText(Status);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Year,Name,Pinno,fees,status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Year=itemView.findViewById(R.id.year);
            Name=itemView.findViewById(R.id.name);
            Pinno=itemView.findViewById(R.id.pinno);
            fees=itemView.findViewById(R.id.fees);
            status=itemView.findViewById(R.id.status);


        }
    }
}
