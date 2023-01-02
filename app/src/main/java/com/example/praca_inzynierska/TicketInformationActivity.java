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
    public static RecyclerView rvPassengerInformation;

    private AirlineTicketModel ticketModel;
    private int ticketModel_TicketID;

    private FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper;
    private ArrayList<Integer> passenger_Id, passenger_Age,passenger_IsAdult, passenger_TicketId;
    private ArrayList<String> passenger_Name, passenger_LastName, passenger_Gender, passenger_SeatNumber;


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
    }

    private void loadPassengersDataFromArrays(int ticketId) {
        flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(TicketInformationActivity.this);
        passenger_Id = new ArrayList<>();
        passenger_Name = new ArrayList<>();
        passenger_LastName = new ArrayList<>();
        passenger_Age = new ArrayList<>();
        passenger_Gender = new ArrayList<>();
        passenger_SeatNumber = new ArrayList<>();
        passenger_IsAdult = new ArrayList<>();
        passenger_TicketId = new ArrayList<>();

        storePassengersDataInArrays(ticketId);
        PassengerDetailsAdapter adapter = new PassengerDetailsAdapter(TicketInformationActivity.this, passenger_Id, passenger_Name,
                passenger_LastName, passenger_Age, passenger_Gender, passenger_SeatNumber,passenger_IsAdult, passenger_TicketId);
        rvPassengerInformation.setAdapter(adapter);
        rvPassengerInformation.setLayoutManager(new LinearLayoutManager(TicketInformationActivity.this));
    }

    private void setTicketInformation(AirlineTicketModel ticketModel) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String PriceTicket = formatter.format(ticketModel.getTicketPrice() * (ticketModel.getNumberPassengersAdults() + ticketModel.getNumberPassengersChildren())) + " zł";
        tvTitle_departureCode.setText(ticketModel.getDepartureAirport().getAirportCode());
        tvTitle_arrivalCode.setText(ticketModel.getArrivalAirport().getAirportCode());
        tvDepartureCode.setText(ticketModel.getDepartureAirport().getAirportCode());
        tvDepartureCity.setText(ticketModel.getDepartureAirport().getAirportCity());
        tvFlightDuration.setText(ticketModel.getFlightDuration());
        tvArrivalCode.setText(ticketModel.getArrivalAirport().getAirportCode());
        tvArrivalCity.setText(ticketModel.getArrivalAirport().getAirportCity());
        tvDepartureDate.setText(ticketModel.getDepartureDate());
        tvTravelClass.setText(ticketModel.getTravelClass());
        tvFlightNumber.setText(ticketModel.getFlightNumber());
        tvDepartureTime.setText(ticketModel.getDepartureTime());
        tvNumberPassengers.setText(String.valueOf(ticketModel.getNumberPassengersAdults()));
        tvTicketPrice.setText(PriceTicket);
        tvSeatNumber.setText(String.valueOf(passenger_SeatNumber));
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
                        flyingApplicationDatabaseHelper.removePassenger((Integer) viewHolder. itemView.getTag());
                        loadPassengersDataFromArrays(ticketModel_TicketID);
                        setTicketInformation(ticketModel);
                        int numberPassengers=flyingApplicationDatabaseHelper.getNumberAdultPassengers(ticketModel_TicketID)+flyingApplicationDatabaseHelper.getNumberChildrenPassengers(ticketModel_TicketID);
                        DecimalFormat formatter = new DecimalFormat("#0.00");
                        tvNumberPassengers.setText(String.valueOf(numberPassengers));
                        tvTicketPrice.setText(formatter.format(ticketModel.getTicketPrice() * numberPassengers));
                        Toast.makeText(TicketInformationActivity.this, "Usunięto pasażera!", Toast.LENGTH_SHORT).show();
                        removePassenger();
                        loadPassengersDataFromArrays(ticketModel_TicketID);
                    }else{
                        flyingApplicationDatabaseHelper.removeTicket(ticketModel_TicketID);
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