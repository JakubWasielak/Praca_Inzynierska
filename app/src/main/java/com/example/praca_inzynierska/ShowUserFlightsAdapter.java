package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowUserFlightsAdapter extends RecyclerView.Adapter<ShowUserFlightsAdapter.ShowUserFlightsAdapterHolder> {

    Context context;
    String[] Departure_airport_sc, Departure_cy;
    String[] Arrival_airport_sc , Arrival_cy;
    String[] Duration_flight;
    String[] Flight_d;
    String[] Flight_t;
    String[] Flight_num;

    public ShowUserFlightsAdapter(Context ct,
                                  String[] Departure_airport_shortcut, String[] Departure_city,
                                  String[] Arrival_airport_shortcut, String[] Arrival_city,
                                  String[] Duration_of_flight, String[] Flight_date, String[] Flight_time,String[] Flight_number  ) {
        context=ct;
        Departure_airport_sc =Departure_airport_shortcut;
        Departure_cy= Departure_city;
        Arrival_airport_sc = Arrival_airport_shortcut;
        Arrival_cy = Arrival_city;
        Duration_flight = Duration_of_flight;
        Flight_d= Flight_date;
        Flight_t = Flight_time;
        Flight_num = Flight_number;
    }

    @NonNull
    @Override
    public ShowUserFlightsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.air_ticket,parent,false);
        return new ShowUserFlightsAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUserFlightsAdapterHolder holder, int position) {
        holder.Departure_airport_sc.setText(Departure_airport_sc[position]);
        holder.Departure_cy.setText(Departure_cy[position]);
        holder.Arrival_airport_sc.setText(Arrival_airport_sc[position]);
        holder.Arrival_cy.setText(Arrival_cy[position]);
        holder.Duration_flight.setText(Duration_flight[position]);
        holder.Flight_d.setText(Flight_d[position]);
        holder.Flight_t.setText(Flight_t[position]);
        holder.Flight_num.setText(Flight_num[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ShowUserFlightsAdapterHolder extends RecyclerView.ViewHolder {

        TextView Departure_airport_sc, Departure_cy, Arrival_airport_sc , Arrival_cy, Duration_flight, Flight_d, Flight_t,  Flight_num;

        public ShowUserFlightsAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Departure_airport_sc = itemView.findViewById(R.id.Departure_airport);
            Departure_cy = itemView.findViewById(R.id.Departure_city);
            Arrival_airport_sc = itemView.findViewById(R.id.Arrival_airport);
            Arrival_cy = itemView.findViewById(R.id.Arrival_city);
            Duration_flight = itemView.findViewById(R.id.Duration_of_flight);
            Flight_d = itemView.findViewById(R.id.Flight_date);
            Flight_t = itemView.findViewById(R.id.Flight_time);
            Flight_num = itemView.findViewById(R.id.Flight_number);
        }
    }
}
