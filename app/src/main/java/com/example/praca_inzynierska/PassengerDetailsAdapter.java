package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PassengerDetailsAdapter extends RecyclerView.Adapter<PassengerDetailsAdapter.PassengerDetailsViewHolder> {
    private Context context;
    private ArrayList<Integer> passenger_Id, passenger_Age, passenger_IsAdult, passenger_TicketId;
    private ArrayList<String> passenger_Name, passenger_LastName, passenger_Gender, passenger_SeatNumber;

    public PassengerDetailsAdapter(Context context, ArrayList<Integer> passenger_Id, ArrayList<String> passenger_Name,
                                   ArrayList<String> passenger_LastName, ArrayList<Integer> passenger_Age, ArrayList<String> passenger_Gender,
                                   ArrayList<String> passenger_SeatNumber, ArrayList<Integer> passenger_IsAdult, ArrayList<Integer> passenger_TicketId) {
        this.context = context;
        this.passenger_Id = passenger_Id;
        this.passenger_Name = passenger_Name;
        this.passenger_LastName = passenger_LastName;
        this.passenger_Age = passenger_Age;
        this.passenger_Gender = passenger_Gender;
        this.passenger_SeatNumber = passenger_SeatNumber;
        this.passenger_IsAdult = passenger_IsAdult;
        this.passenger_TicketId = passenger_TicketId;
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
    public void onBindViewHolder(@NonNull PassengerDetailsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvPassengerName.setText(passenger_Name.get(position));
        holder.tvPassengerLastName.setText(passenger_LastName.get(position));
        holder.tvPassengerAge.setText(passenger_Age.get(position) + " l");
        holder.tvPassengerGender.setText(passenger_Gender.get(position) + ", ");
        holder.itemView.setTag(passenger_Id.get(position));
        holder.passengerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] passengerGenderNames = context.getResources().getStringArray(R.array.passengerGender);
                BottomSheetDialog updatePassenger_BottomSheetDialog = new BottomSheetDialog(context);
                updatePassenger_BottomSheetDialog.setCanceledOnTouchOutside(false);
                updatePassenger_BottomSheetDialog.setContentView(R.layout.passenger_details_sheet_dialog);

                ImageButton btnConfirm = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_confirm_ImageButton);
                AutoCompleteTextView tvPassengerIsAdult = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_isAdult_AutoCompleteTextView);
                AutoCompleteTextView tvPassengerGender = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_gender_AutoCompleteTextView);
                TextInputEditText tvPassengerName = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_name_TextInputEditText);
                TextInputEditText tvPassengerLastName = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_lastName_TextInputEditText);
                TextInputEditText tvPassengerAge = updatePassenger_BottomSheetDialog.findViewById(R.id.addPassenger_age_TextInputEditText);
                btnConfirm.setBackgroundResource(R.drawable.animation_button_update_passager);

                updatePassenger_BottomSheetDialog.show();

                if (passenger_IsAdult.get(position) == 1) {
                    tvPassengerIsAdult.setText("Dorosły");
                    tvPassengerGender.setText(String.valueOf(passenger_Gender.get(position)));
                    tvPassengerName.setText(String.valueOf(passenger_Name.get(position)));
                    tvPassengerLastName.setText(String.valueOf(passenger_LastName.get(position)));
                    tvPassengerAge.setText(String.valueOf(passenger_Age.get(position)));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, passengerGenderNames);
                    tvPassengerGender.setAdapter(arrayAdapter);

                } else if (passenger_IsAdult.get(position) == 0) {
                    tvPassengerIsAdult.setText("Dziecko");
                    tvPassengerGender.setText(String.valueOf(passenger_Gender.get(position)));
                    tvPassengerName.setText(String.valueOf(passenger_Name.get(position)));
                    tvPassengerLastName.setText(String.valueOf(passenger_LastName.get(position)));
                    tvPassengerAge.setText(String.valueOf(passenger_Age.get(position)));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, passengerGenderNames);
                    tvPassengerGender.setAdapter(arrayAdapter);
                }

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FlyingApplicationDatabaseHelper databaseHelper = new FlyingApplicationDatabaseHelper(context);

                        String passengerId = String.valueOf(passenger_Id.get(position));
                        String passengerName = String.valueOf(tvPassengerName.getText());
                        String passengerLastName = String.valueOf(tvPassengerLastName.getText());
                        int passengerAge = Integer.parseInt(String.valueOf(tvPassengerAge.getText()));
                        String passengerGender = String.valueOf(tvPassengerGender.getText());
                        String passengerSeat = passenger_SeatNumber.get(position);
                        int passengerIsAdult = passenger_IsAdult.get(position);
                        int passengerTicketId = passenger_TicketId.get(position);
                        databaseHelper.updatePassenger(passengerId, passengerName, passengerLastName, passengerAge, passengerGender, passengerSeat, passengerIsAdult, passengerTicketId);
                        updateRecyclerView(passengerTicketId);
                        updatePassenger_BottomSheetDialog.dismiss();
                    }
                });

            }
        });
    }

    private void updateRecyclerView(int ticketId) {
        RecyclerView rvPassengerInformation = TicketInformationActivity.rvPassengerInformation;
        ArrayList<Integer> passenger_Id, passenger_Age, passenger_IsAdult, passenger_TicketId;
        ArrayList<String> passenger_Name, passenger_LastName, passenger_Gender, passenger_SeatNumber;
        FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(context);
        passenger_Id = new ArrayList<>();
        passenger_Name = new ArrayList<>();
        passenger_LastName = new ArrayList<>();
        passenger_Age = new ArrayList<>();
        passenger_Gender = new ArrayList<>();
        passenger_SeatNumber = new ArrayList<>();
        passenger_IsAdult = new ArrayList<>();
        passenger_TicketId = new ArrayList<>();

        Cursor cursor = flyingApplicationDatabaseHelper.getPassengersFromTicket(ticketId);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                passenger_Id.add(cursor.getInt(0));
                passenger_Name.add(cursor.getString(1));
                passenger_LastName.add(cursor.getString(2));
                passenger_Age.add(cursor.getInt(3));
                passenger_Gender.add(cursor.getString(4));
                passenger_SeatNumber.add(cursor.getString(5));
                passenger_IsAdult.add(cursor.getInt(6));
                passenger_TicketId.add(cursor.getInt(7));
            }
        }
        PassengerDetailsAdapter adapter = new PassengerDetailsAdapter(context, passenger_Id, passenger_Name,
                passenger_LastName, passenger_Age, passenger_Gender, passenger_SeatNumber,passenger_IsAdult, passenger_TicketId);
        rvPassengerInformation.setAdapter(adapter);
        rvPassengerInformation.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public int getItemCount() {
        return passenger_Id.size();
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
            passengerItem = itemView.findViewById(R.id.passengerItem_mainLayout);
        }
    }
}
