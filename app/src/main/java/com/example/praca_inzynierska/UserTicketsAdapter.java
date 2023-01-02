package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserTicketsAdapter extends RecyclerView.Adapter<UserTicketsAdapter.UserTicketsViewHolder> {
    private final Context context;
    private final ArrayList<Integer> ticket_id;
    private final ArrayList<String> ticket_depCode;
    private final ArrayList<String> ticket_depName;
    private final ArrayList<String> ticket_arrCode;
    private final ArrayList<String> ticket_arrName;
    private final ArrayList<String> ticket_flightDuration;
    private final ArrayList<String> ticket_departureDate;
    private final ArrayList<String> ticket_departureTime;
    private final ArrayList<String> ticket_flightNumber;

    public UserTicketsAdapter(Context context, ArrayList<Integer> ticket_id, ArrayList<String> ticket_depCode, ArrayList<String> ticket_depName, ArrayList<String> ticket_arrCode, ArrayList<String> ticket_arrName, ArrayList<String> ticket_flightDuration, ArrayList<String> ticket_departureDate, ArrayList<String> ticket_departureTime, ArrayList<String> ticket_flightNumber, ArrayList<String> ticket_travelClass, ArrayList<Double> ticket_price, ArrayList<Integer> ticket_adults_passengers, ArrayList<Integer> ticket_children_passengers, ArrayList<Integer> ticket_isConnecting) {
        this.context = context;
        this.ticket_id = ticket_id;
        this.ticket_depCode = ticket_depCode;
        this.ticket_depName = ticket_depName;
        this.ticket_arrCode = ticket_arrCode;
        this.ticket_arrName = ticket_arrName;
        this.ticket_flightDuration = ticket_flightDuration;
        this.ticket_departureDate = ticket_departureDate;
        this.ticket_departureTime = ticket_departureTime;
        this.ticket_flightNumber = ticket_flightNumber;
    }

    @NonNull
    @Override
    public UserTicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_tickets_item, parent, false);
        return new UserTicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTicketsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ticket_depCode_txt.setText(String.valueOf(ticket_depCode.get(position)));
        holder.ticket_depName_txt.setText(String.valueOf(ticket_depName.get(position)));
        holder.ticket_arrCode_txt.setText(String.valueOf(ticket_arrCode.get(position)));
        holder.ticket_arrName_txt.setText(String.valueOf(ticket_arrName.get(position)));
        holder.ticket_flightDuration_txt.setText(String.valueOf(ticket_flightDuration.get(position)));
        holder.ticket_departureDateAndTime_txt.setText(String.format("%s, %s", ticket_departureDate.get(position), ticket_departureTime.get(position)));
        holder.ticket_flightNumber_txt.setText(String.valueOf(ticket_flightNumber.get(position)));
        holder.itemView.setTag(ticket_id.get(position));

        holder.userTickets_MainRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketInformationActivity.class);
                FlyingApplicationDatabaseHelper db = new FlyingApplicationDatabaseHelper(context);
                Cursor cursor = db.getSelectedTickets(ticket_id.get(position));
                AirlineTicketModel ticketModel = null;
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        AirportModel departureAirport = new AirportModel(cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                        AirportModel arrivalAirport = new AirportModel(cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13));
                        ticketModel = new AirlineTicketModel(departureAirport,arrivalAirport,cursor.getString(2),cursor.getString(0),
                                cursor.getString(1),cursor.getString(3),cursor.getString(14),cursor.getDouble(4),
                                db.getNumberAdultPassengers(ticket_id.get(position)),db.getNumberChildrenPassengers(ticket_id.get(position)),
                                false,cursor.getInt(5));
                    }
                }
                intent.putExtra("ticketModel", ticketModel);
                intent.putExtra("ticket_id", ticket_id.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticket_id.size();
    }

    public static class UserTicketsViewHolder extends RecyclerView.ViewHolder {
        TextView ticket_depCode_txt, ticket_depName_txt, ticket_arrCode_txt, ticket_arrName_txt, ticket_flightDuration_txt, ticket_departureDateAndTime_txt, ticket_flightNumber_txt;
        RelativeLayout userTickets_MainRelativeLayout;

        public UserTicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            ticket_depCode_txt = itemView.findViewById(R.id.departureAirport_TextView);
            ticket_depName_txt = itemView.findViewById(R.id.departureCity_TextView);
            ticket_arrCode_txt = itemView.findViewById(R.id.arrivalAirport_TextView);
            ticket_arrName_txt = itemView.findViewById(R.id.arrivalCity_TextView);
            ticket_flightDuration_txt = itemView.findViewById(R.id.durationOfFlight_TextView);
            ticket_departureDateAndTime_txt = itemView.findViewById(R.id.flightDate_TextView);
            ticket_flightNumber_txt = itemView.findViewById(R.id.flightNumber_TextView);

            userTickets_MainRelativeLayout = itemView.findViewById(R.id.userTickets_MainRelativeLayout);
        }
    }
}
