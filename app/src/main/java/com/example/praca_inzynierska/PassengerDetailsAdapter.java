package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PassengerDetailsAdapter extends RecyclerView.Adapter<PassengerDetailsAdapter.PassengerDetailsViewHolder> {
    Context context;
    private ArrayList<Integer> passenger_id, passenger_age, passenge_ticketId;
    private ArrayList<String> passenger_name, passenger_lastName, passenger_gender, passenger_seat;


    public PassengerDetailsAdapter(Context context, ArrayList<Integer> passenger_id, ArrayList<String> passenger_name,
                                   ArrayList<String> passenger_lastName, ArrayList<Integer> passenger_age, ArrayList<String> passenger_gender,
                                   ArrayList<String> passenger_seat, ArrayList<Integer> passenge_ticketId) {
        this.context = context;
        this.passenger_id = passenger_id;
        this.passenger_name = passenger_name;
        this.passenger_lastName = passenger_lastName;
        this.passenger_age = passenger_age;
        this.passenger_gender = passenger_gender;
        this.passenger_seat = passenger_seat;
        this.passenge_ticketId = passenge_ticketId;
    }

    @NonNull
    @Override
    public PassengerDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.new_passenger_item, parent, false);
        return new PassengerDetailsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PassengerDetailsViewHolder holder, int position) {
        holder.tvPassengerName.setText(passenger_name.get(position));
        holder.tvPassengerLastName.setText(passenger_lastName.get(position));
        holder.tvPassengerAge.setText(passenger_age.get(position)+" l");
        holder.tvPassengerGender.setText(passenger_gender.get(position)+", ");
        holder.itemView.setTag(passenger_id.get(position));
    }

    @Override
    public int getItemCount() {
        return passenger_id.size();
    }

    public static class PassengerDetailsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout passengerItem;
        TextView tvPassengerName;
        TextView tvPassengerLastName;
        TextView tvPassengerAge;
        TextView tvPassengerGender;

        public PassengerDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassengerName = itemView.findViewById(R.id.passengerName_TextView);
            tvPassengerLastName = itemView.findViewById(R.id.passengerLastName_TextView);
            tvPassengerAge = itemView.findViewById(R.id.passengerAge_TextView);
            tvPassengerGender = itemView.findViewById(R.id.passengerGender_TextView);
            passengerItem =itemView.findViewById(R.id.passengerItem_mainLayout);

        }
    }
}
