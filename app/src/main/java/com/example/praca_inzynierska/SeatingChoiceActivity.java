package com.example.praca_inzynierska;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SeatingChoiceActivity extends AppCompatActivity {
    TextView tvDepartureAirportCode;
    TextView tvDepartureAirportName;
    TextView tvArrivalAirportCode;
    TextView tvArrivalAirportName;
    TextView tvFlightDuration;
    TextView tvDepartureDateAndTime;
    TextView tvFlightNumber;
    TextView tvNumberOfPassengers;
    TextView tvTravelClass;


    private int selectedSeats = 0;
    private final String[] seatNumberNames = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4", "E1", "E2", "E3", "E4", "F1", "F2", "F3", "F4", "G1", "G2", "G3", "G4"};
    private final int[] seatsID = {R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.C1, R.id.C2, R.id.C3, R.id.C4, R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.E1, R.id.E2, R.id.E3, R.id.E4, R.id.F1, R.id.F2, R.id.F3, R.id.F4, R.id.G1, R.id.G2, R.id.G3, R.id.G4};
    private ArrayList<String> reservedSeatsNames;
    private AirlineTicketModel selectedTicket;
    private GridLayout seatMap_GridLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_seating_choice);

        tvDepartureAirportCode = findViewById(R.id.departureAirportCode_TextView);
        tvDepartureAirportName = findViewById(R.id.departureCityName_TextView);
        tvArrivalAirportCode = findViewById(R.id.arrivalAirportCode_TextView);
        tvArrivalAirportName = findViewById(R.id.arrivalAirportCityName_TextView);
        tvFlightDuration = findViewById(R.id.flightDuration_TextView);
        tvDepartureDateAndTime = findViewById(R.id.departureDateAndTime_TextView);
        tvFlightNumber = findViewById(R.id.flightNumber_TextView);
        tvNumberOfPassengers = findViewById(R.id.numberOfPassengers_TextView);
        tvTravelClass = findViewById(R.id.travelClass_TextView);
        seatMap_GridLayout = findViewById(R.id.seatMap_GridLayout);

        reservedSeatsNames = new ArrayList<>();

        getIntentData();

        setReservedSeats(seatMap_GridLayout, (selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren()));
        setSeatReservation(seatMap_GridLayout, (selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren()));

        ImageButton btnOpenNextActivity = findViewById(R.id.goToPassengerDetailsActivity_ImageButton);
        btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reservedSeatsNames.size() == 0) {
                    automaticallySelectedDialog();
                } else {
                    reservedSeatsNames.size();
                    if (reservedSeatsNames.size() < selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren()) {
                        Toast.makeText(SeatingChoiceActivity.this, "Wybierz miejsce.", Toast.LENGTH_SHORT).show();
                    } else if (reservedSeatsNames.size() == (selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren())) {
                        Intent intent = new Intent(SeatingChoiceActivity.this, PassengerInformationActivity.class);
                        selectedTicket.setReservedSeatsNames(reservedSeatsNames);
                        intent.putExtra("SelectedTicket", selectedTicket);
                        startActivity(intent);
                    }
                }
            }
        });


        ImageButton btnPreviousActivity = findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("SelectedTicket")) {
            selectedTicket = getIntent().getParcelableExtra("SelectedTicket");
            setFlightInformation();
        } else {
            Toast.makeText(SeatingChoiceActivity.this, "Brak danych.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFlightInformation(){
        tvDepartureAirportCode.setText(selectedTicket.getDepartureAirport().airportCode);
        tvDepartureAirportName.setText(selectedTicket.getDepartureAirport().airportCity);
        tvArrivalAirportCode.setText(selectedTicket.getArrivalAirport().getAirportCode());
        tvArrivalAirportName.setText(selectedTicket.getArrivalAirport().getAirportCity());
        tvFlightDuration.setText(selectedTicket.getFlightDuration());
        tvDepartureDateAndTime.setText(String.format("%s, %s", selectedTicket.getDepartureDate(), selectedTicket.getDepartureTime()));
        tvFlightNumber.setText(selectedTicket.getFlightNumber());
        tvNumberOfPassengers.setText(String.valueOf((selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren())));
        tvTravelClass.setText("Klasa: " + selectedTicket.getTravelClass());
    }

    private void setReservedSeats(GridLayout gridLayout, int numberOfPassengers) {
        ImageButton imageButton;
        int[] a;
        if (numberOfPassengers == 16) {
            a = new int[24 - numberOfPassengers + 4];
        } else {
            a = new int[8];
        }

        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(gridLayout.getChildCount());

            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    i--;
                    break;
                }
            }
        }
        for (int j : a) {
            imageButton = (ImageButton) gridLayout.getChildAt(j);
            imageButton.setBackgroundResource(R.drawable.bg_seat_occupied);
        }
    }

    private void setSeatReservation(GridLayout gridLayout, int numberOfPassengers) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageButton imageButton = (ImageButton) gridLayout.getChildAt(i);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onClick(View view) {
                    if (selectedSeats < numberOfPassengers) {
                        if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_free).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_selected);
                            for (int i = 0; i < seatsID.length; i++) {
                                if (imageButton.getId() == seatsID[i]) {
                                    reservedSeatsNames.add(seatNumberNames[i]);
                                }
                            }
                            selectedSeats++;
                        } else if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
                            for (int i = 0; i < seatsID.length; i++) {
                                if (imageButton.getId() == seatsID[i]) {
                                    reservedSeatsNames.remove(seatNumberNames[i]);
                                }
                            }
                            selectedSeats--;
                        }
                    } else if (selectedSeats == numberOfPassengers) {
                        if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_selected).getConstantState()) {
                            imageButton.setBackgroundResource(R.drawable.bg_seat_free);
                            for (int i = 0; i < seatsID.length; i++) {
                                if (imageButton.getId() == seatsID[i]) {
                                    reservedSeatsNames.remove(seatNumberNames[i]);
                                }
                            }
                            selectedSeats--;
                        }
                    }
                }
            });

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setAutomaticallyReservedSeats(GridLayout gridLayout, int numberOfPassengers) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageButton imageButton = (ImageButton) gridLayout.getChildAt(i);
            if (selectedSeats < numberOfPassengers) {
                if (imageButton.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_seat_free).getConstantState()) {
                    imageButton.setBackgroundResource(R.drawable.bg_seat_selected);
                    for (int j = 0; j < seatsID.length; j++) {
                        if (imageButton.getId() == seatsID[j]) {
                            reservedSeatsNames.add(seatNumberNames[j]);
                        }
                    }
                    selectedSeats++;
                }
            }
        }
    }

    void automaticallySelectedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Automatyczny wybór miejsca?");
        builder.setMessage("Chcesz żeby miejsca zostały wybrane automatycznie?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setAutomaticallyReservedSeats(seatMap_GridLayout, (selectedTicket.getNumberPassengersAdults() + selectedTicket.getNumberPassengersChildren()));
                Intent intent = new Intent(SeatingChoiceActivity.this, PassengerInformationActivity.class);
                selectedTicket.setReservedSeatsNames(reservedSeatsNames);
                intent.putExtra("SelectedTicket", selectedTicket);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}