package com.example.praca_inzynierska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FAT_RecyclerViewAdapter extends RecyclerView.Adapter<FAT_RecyclerViewAdapter.MyViewHolder> {
    RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<AirlineTicketModel> foundAirlineTicketsModels;
    int singleItem_selection_position = -1;

    public FAT_RecyclerViewAdapter(Context context, ArrayList<AirlineTicketModel> foundAirlineTicketsModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.foundAirlineTicketsModels = foundAirlineTicketsModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public FAT_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.found_ticket_item,parent,false);
        return new FAT_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FAT_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvDepartureAirportCode.setText(foundAirlineTicketsModels.get(position).getDepartureAirport().getAirportCode());
        holder.tvDepartureAirportName.setText(foundAirlineTicketsModels.get(position).getDepartureAirport().getAirportCity());
        holder.tvArrivalAirportCode.setText(foundAirlineTicketsModels.get(position).getArrivalAirport().getAirportCode());
        holder.tvArrivalAirportName.setText(foundAirlineTicketsModels.get(position).getArrivalAirport().getAirportCity());
        holder.tvFlightDuration.setText(foundAirlineTicketsModels.get(position).getFlightDuration());
        holder.tvDepartureDateAndTime.setText(String.format("%s, %s", foundAirlineTicketsModels.get(position).getDepartureDate(), foundAirlineTicketsModels.get(position).getDepartureTime()));
        holder.tvFlightNumber.setText(foundAirlineTicketsModels.get(position).getFlightNumber());
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(foundAirlineTicketsModels.get(position).getTicketPrice())+" zł";

        if(foundAirlineTicketsModels.get(position).getIsConnecting()==1){
            holder.icConnectingTicket.setVisibility(View.VISIBLE);
        }


        holder.tvTicketPrice.setText(PriceTicket);

        if(singleItem_selection_position == position)
        {
            holder.ivBackground.setBackgroundResource(R.drawable.bg_background_found_ticket_selected);
        }else {
            holder.ivBackground.setBackgroundResource(R.drawable.bg_background_found_ticket);
        }
    }

    @Override
    public int getItemCount() {
        return foundAirlineTicketsModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivBackground;
        TextView tvDepartureAirportCode;
        TextView tvDepartureAirportName;
        TextView tvArrivalAirportCode;
        TextView tvArrivalAirportName;
        TextView tvFlightDuration;
        TextView tvDepartureDateAndTime;
        TextView tvFlightNumber;
        TextView tvTicketPrice;
        ImageView icConnectingTicket;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvDepartureAirportCode = itemView.findViewById(R.id.departureCode_TextView);
            tvDepartureAirportName = itemView.findViewById(R.id.departureCity_TextView);
            tvArrivalAirportCode = itemView.findViewById(R.id.arrivalCode_TextView);
            tvArrivalAirportName = itemView.findViewById(R.id.arrivalCity_TextView);
            tvFlightDuration = itemView.findViewById(R.id.flightDuration_TextView);
            tvDepartureDateAndTime = itemView.findViewById(R.id.dateAndTimeOfDeparture_TextView);
            tvFlightNumber = itemView.findViewById(R.id.flightNumber_TextView);
            tvTicketPrice = itemView.findViewById(R.id.ticketPrice_TextView);
            icConnectingTicket= itemView.findViewById(R.id.connectingTicket_ImageView);
            ivBackground = itemView.findViewById(R.id.background_ImageView);

            ivBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSingleSelection(getAdapterPosition());

                    if(recyclerViewInterface != null);{
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){

                            recyclerViewInterface.onItemClick(pos);

                        }
                    }
                }
            });
        }
        private void setSingleSelection(int adapterPosition) {
            if(adapterPosition == RecyclerView.NO_POSITION) return;
            notifyItemChanged(singleItem_selection_position);
            singleItem_selection_position = adapterPosition;
            notifyItemChanged(singleItem_selection_position);
        }
    }

}
