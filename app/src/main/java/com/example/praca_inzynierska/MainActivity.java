package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String[] Departure_airport_shortcut;
    String[] Departure_city;
    String[] Arrival_airport_shortcut;
    String[] Arrival_city;
    String[] Duration_of_flight;
    String[] Flight_date;
    String[] Flight_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        RecyclerView user_Flights = findViewById(R.id.UserFlights_RecyclerView);
        ImageButton addNewTicket_ImageButton = findViewById(R.id.addNewTicked_ImageButton);

//Przykładowe dane do czasu przejscia na baze danych
// ------------------------------------------------------------------------------------------------------
        Departure_airport_shortcut = getResources().getStringArray(R.array.Departure_Code);
        Departure_city = getResources().getStringArray(R.array.Departure_City);
        Arrival_airport_shortcut = getResources().getStringArray(R.array.Arrival_Code);
        Arrival_city = getResources().getStringArray(R.array.Arrival_City);
        Duration_of_flight = getResources().getStringArray(R.array.Duration_Flight);
        Flight_date  = getResources().getStringArray(R.array.Flight_Date);
        Flight_number = getResources().getStringArray(R.array.Flight_Number);

        ShowUserFlightsAdapter user1 = new ShowUserFlightsAdapter(this, Departure_airport_shortcut, Departure_city, Arrival_airport_shortcut, Arrival_city, Duration_of_flight, Flight_date, Flight_number);
        user_Flights.setAdapter(user1);
        user_Flights.setLayoutManager(new LinearLayoutManager(this));
//------------------------------------------------------------------------------------------------------

        addNewTicket_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchingForTicketActivity.class);
                startActivity(intent);
            }
        });
    }
}