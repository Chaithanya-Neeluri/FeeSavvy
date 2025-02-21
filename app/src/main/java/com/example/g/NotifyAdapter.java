package com.example.g;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    Context context;


    public NotifyAdapter(Context context, ArrayList<viewstudent> list) {
        this.context = context;
        this.list = list;



    }


    ArrayList<viewstudent> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notifyitem,parent,false);
        return  new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        viewstudent user = list.get(position);


        holder.Name.setText(user.getName());
        holder.Pinno.setText(user.getPinno());
        holder.subject.setText(user.getSubject());
        holder.feedback.setText(user.getFeedback());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Pinno,subject,feedback;


        public MyViewHolder(@NonNull View notifyitemView) {
            super(notifyitemView);

            Name=itemView.findViewById(R.id.name);
            Pinno=itemView.findViewById(R.id.pinno);
            subject=itemView.findViewById(R.id.subject);
            feedback=itemView.findViewById(R.id.feedback);


        }
    }
}
