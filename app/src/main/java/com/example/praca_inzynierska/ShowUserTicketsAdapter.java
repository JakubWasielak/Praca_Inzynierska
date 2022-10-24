package com.example.praca_inzynierska;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ShowUserTicketsAdapter extends RecyclerView.Adapter<ShowUserTicketsAdapter.ShowUserTicketsViewHolder>{
    private final Context context;
    private final ArrayList ticket_id;
    private final ArrayList ticket_depCode;
    private final ArrayList ticket_depName;
    private final ArrayList ticket_arrCode;
    private final ArrayList ticket_arrName;
    private final ArrayList ticket_flightDuration;
    private final ArrayList ticket_departureDate;
    private final ArrayList ticket_departureTime;
    private final ArrayList ticket_travelClass;
    private final ArrayList ticket_flightNumber;
    private final ArrayList ticket_price;
    private final ArrayList ticket_numberPassengers;

    public ShowUserTicketsAdapter(Context context, ArrayList ticket_id, ArrayList ticket_depCode, ArrayList ticket_depName, ArrayList ticket_arrCode, ArrayList ticket_arrName, ArrayList ticket_flightDuration, ArrayList ticket_departureDate, ArrayList ticket_departureTime, ArrayList ticket_travelClass, ArrayList ticket_flightNumber, ArrayList ticket_price, ArrayList ticket_numberPassengers) {
        this.context = context;
        this.ticket_id = ticket_id;
        this.ticket_depCode = ticket_depCode;
        this.ticket_depName = ticket_depName;
        this.ticket_arrCode = ticket_arrCode;
        this.ticket_arrName = ticket_arrName;
        this.ticket_flightDuration = ticket_flightDuration;
        this.ticket_departureDate = ticket_departureDate;
        this.ticket_departureTime = ticket_departureTime;
        this.ticket_travelClass = ticket_travelClass;
        this.ticket_flightNumber = ticket_flightNumber;
        this.ticket_price = ticket_price;
        this.ticket_numberPassengers = ticket_numberPassengers;
    }

    @NonNull
    @Override
    public ShowUserTicketsAdapter.ShowUserTicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_tickets_item, parent, false);
        return new ShowUserTicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUserTicketsAdapter.ShowUserTicketsViewHolder holder, int position) {
        holder.ticket_depCode_txt.setText(String.valueOf(ticket_depCode.get(position)));
        holder.ticket_depName_txt.setText(String.valueOf(ticket_depName.get(position)));
        holder.ticket_arrCode_txt.setText(String.valueOf(ticket_arrCode.get(position)));
        holder.ticket_arrName_txt.setText(String.valueOf(ticket_arrName.get(position)));
        holder.ticket_flightDuration_txt.setText(String.valueOf(ticket_flightDuration.get(position)));
        holder.ticket_departureDateAndTime_txt.setText(String.format("%s, %s", ticket_departureDate.get(position), ticket_departureTime.get(position)));
        holder.ticket_flightNumber_txt.setText(String.valueOf(ticket_flightNumber.get(position)));
        holder.itemView.setTag(ticket_id.get(position));

    }

    @Override
    public int getItemCount() {
        return ticket_id.size();
    }

    public class ShowUserTicketsViewHolder extends RecyclerView.ViewHolder {
        TextView  ticket_depCode_txt,  ticket_depName_txt,  ticket_arrCode_txt,  ticket_arrName_txt,  ticket_flightDuration_txt,  ticket_departureDateAndTime_txt,  ticket_flightNumber_txt;

        public ShowUserTicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            ticket_depCode_txt = itemView.findViewById(R.id.departureAirport_TextView);
            ticket_depName_txt= itemView.findViewById(R.id.departureCity_TextView);
            ticket_arrCode_txt= itemView.findViewById(R.id.arrivalAirport_TextView);
            ticket_arrName_txt= itemView.findViewById(R.id.arrivalCity_TextView);
            ticket_flightDuration_txt= itemView.findViewById(R.id.durationOfFlight_TextView);
            ticket_departureDateAndTime_txt= itemView.findViewById(R.id.flightDate_TextView);
            ticket_flightNumber_txt= itemView.findViewById(R.id.flightNumber_TextView);
        }
    }
}
