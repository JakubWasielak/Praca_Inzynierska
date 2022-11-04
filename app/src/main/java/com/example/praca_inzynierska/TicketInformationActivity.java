package com.example.praca_inzynierska;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class TicketInformationActivity extends AppCompatActivity {
    private TextView tvTitle_departureCode, tvTitle_arrivalCode;
    private TextView tvDepartureCode, tvDepartureCity, tvFlightDuration;
    private TextView tvArrivalCode, tvArrivalCity, tvDepartureDate;
    private TextView tvTravelClass, tvFlightNumber, tvDepartureTime;
    private TextView tvNumberPassengers, tvSeatNumber, tvTicketPrice;
    private RecyclerView rvPassengerInformation;

    private AirlineTicketModel ticketModel;
    private int ticketModel_TicketID;

    private FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper;
    private ArrayList<Integer> passenger_id, passenger_age, passenger_ticketId;
    private ArrayList<String> passenger_name, passenger_lastName, passenger_gender, passenger_seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_ticket_information);

        tvTitle_departureCode = findViewById(R.id.title_departureCode_TextView);
        tvTitle_arrivalCode = findViewById(R.id.title_arrivalCode_TextView);
        tvDepartureCode = findViewById(R.id.TicketInfo_departureCode_TextView);
        tvDepartureCity = findViewById(R.id.TicketInfo_departureCity_TextView);
        tvFlightDuration = findViewById(R.id.TicketInfo_flightDuration_TextView);
        tvArrivalCode = findViewById(R.id.TicketInfo_arrivalCode_TextView);
        tvArrivalCity = findViewById(R.id.TicketInfo_arrivalCity_TextView);
        tvDepartureDate = findViewById(R.id.TicketInfo_departureDate_TextView);
        tvTravelClass = findViewById(R.id.TicketInfo_travelClass_TextView);
        tvFlightNumber = findViewById(R.id.TicketInfo_flightNumber_TextView);
        tvDepartureTime = findViewById(R.id.TicketInfo_departureTime_TextView);
        tvNumberPassengers = findViewById(R.id.TicketInfo_numberPassengers_TextView);
        tvSeatNumber = findViewById(R.id.TicketInfo_seatNumber_TextView);
        tvTicketPrice = findViewById(R.id.TicketInfo_ticketPrice_TextView);

        rvPassengerInformation = findViewById(R.id.TicketInfo_passengerInformation_RecyclerView);

        getIntentData();
        loadPassengersDataFromArrays(ticketModel_TicketID);
        setTicketInformation(ticketModel);
        removePassenger();


        ImageButton btnCloseActivity = findViewById(R.id.TicketInfo_closeActivity_ImageButton);
        btnCloseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("ticketModel")) {
            ticketModel = getIntent().getParcelableExtra("ticketModel");
            ticketModel_TicketID = getIntent().getIntExtra("ticket_id", 0);

        } else {
            Toast.makeText(this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    private void storePassengersDataInArrays(int ticketId) {
        Cursor cursor = flyingApplicationDatabaseHelper.readAllPassengers(ticketId);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                passenger_id.add(cursor.getInt(0));
                passenger_name.add(cursor.getString(1));
                passenger_lastName.add(cursor.getString(2));
                passenger_age.add(cursor.getInt(3));
                passenger_gender.add(cursor.getString(4));
                passenger_seat.add(cursor.getString(5));
                passenger_ticketId.add(cursor.getInt(6));
            }
        }
    }

    private void loadPassengersDataFromArrays(int ticketId) {
        flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(TicketInformationActivity.this);
        passenger_id = new ArrayList<>();
        passenger_name = new ArrayList<>();
        passenger_lastName = new ArrayList<>();
        passenger_age = new ArrayList<>();
        passenger_gender = new ArrayList<>();
        passenger_seat = new ArrayList<>();
        passenger_ticketId = new ArrayList<>();

        storePassengersDataInArrays(ticketId);
        PassengerDetailsAdapter adapter = new PassengerDetailsAdapter(TicketInformationActivity.this, passenger_id, passenger_name,
                passenger_lastName, passenger_age, passenger_gender, passenger_seat, passenger_ticketId);
        rvPassengerInformation.setAdapter(adapter);
        rvPassengerInformation.setLayoutManager(new LinearLayoutManager(TicketInformationActivity.this));
    }

    private void setTicketInformation(AirlineTicketModel ticketModel) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(ticketModel.getTicketPrice() * (ticketModel.getNumberPassengersAdults() + ticketModel.getNumberPassengersChildren())) + " zł";

        tvTitle_departureCode.setText(ticketModel.getDepartureAirportCode());
        tvTitle_arrivalCode.setText(ticketModel.getArrivalAirportCode());
        tvDepartureCode.setText(ticketModel.getDepartureAirportCode());
        tvDepartureCity.setText(ticketModel.getDepartureAirportName());
        tvFlightDuration.setText(ticketModel.getFlightDuration());
        tvArrivalCode.setText(ticketModel.getArrivalAirportCode());
        tvArrivalCity.setText(ticketModel.getArrivalAirportName());
        tvDepartureDate.setText(ticketModel.getDepartureDate());
        tvTravelClass.setText(ticketModel.getTravelClass());
        tvFlightNumber.setText(ticketModel.getFlightNumber());
        tvDepartureTime.setText(ticketModel.getDepartureTime());
        tvNumberPassengers.setText(String.valueOf(ticketModel.getNumberPassengersAdults()));
        tvTicketPrice.setText(PriceTicket);
        tvSeatNumber.setText(String.valueOf(passenger_seat));
    }

    private void removePassenger() {
        if(Integer.parseInt(String.valueOf(tvNumberPassengers.getText())) == 1 ){
            lastPassengerConfirmDialog();
        }
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(TicketInformationActivity.this);
                    if(Integer.parseInt(String.valueOf(tvNumberPassengers.getText())) > 1){
                        flyingApplicationDatabaseHelper.updateSelectedTicket(flyingApplicationDatabaseHelper.getTickedIDFromPassenger((Integer) viewHolder.itemView.getTag()),Integer.parseInt(String.valueOf(ticketModel.getNumberPassengersAdults()-1)));
                        flyingApplicationDatabaseHelper.deleteSelectedPassenger((Integer) viewHolder.itemView.getTag());

                        tvNumberPassengers.setText(String.valueOf(ticketModel.getNumberPassengersAdults()-1));
                        System.out.println("Usunięto pasażera!");
                        Toast.makeText(TicketInformationActivity.this, "Usunięto pasażera!", Toast.LENGTH_SHORT).show();
                        removePassenger();
                        loadPassengersDataFromArrays(ticketModel_TicketID);
                    }else{
                        flyingApplicationDatabaseHelper.deleteSelectedTicket(flyingApplicationDatabaseHelper.getTickedIDFromPassenger((Integer) viewHolder.itemView.getTag()));
                        Intent intent = new Intent(TicketInformationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }).attachToRecyclerView(rvPassengerInformation);
    }

    private void lastPassengerConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ostatni pasażer");
        builder.setMessage("Usunięcie pasażera spowoduje usunięcie biletu.");
        builder.setPositiveButton("Potwierdzam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

}