package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class SearchResultsActivity extends AppCompatActivity {
    TextView Lot_tab;
    ImageButton Imagebutton_return, Imagebutton_go_to_choice_seat ;


    RecyclerView Weekly_calendar, Flights_found;
    String[] Day_name;
    String[] Day_number;
    String[] Departure_airport_shortcut;
    String[] Departure_city;
    String[] Arrival_airport_shortcut;
    String[] Arrival_city;
    String[] Duration_of_flight;
    String[] Flight_date;
    String[] Flight_time;
    String[] Flight_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);

        //Przykładowe dane do czasu przejscia na baze danych
// ------------------------------------------------------------------------------------------------------
        Weekly_calendar = findViewById(R.id.Weekly_calendar_RecyclerView);
        Day_name = getResources().getStringArray(R.array.days_of_the_week);
        Day_number = getResources().getStringArray(R.array.days_of_the_week_number);

        Flights_found = findViewById(R.id.Found_flights_RecyclerView);
        Departure_airport_shortcut = getResources().getStringArray(R.array.Departure_airport_shortcut);
        Departure_city = getResources().getStringArray(R.array.Departure_city);
        Arrival_airport_shortcut = getResources().getStringArray(R.array.Arrival_airport_shortcut);
        Arrival_city = getResources().getStringArray(R.array.Arrival_city);
        Duration_of_flight = getResources().getStringArray(R.array.Duration_of_flight);
        Flight_date  = getResources().getStringArray(R.array.Flight_date);
        Flight_time = getResources().getStringArray(R.array. Flight_time);
        Flight_number = getResources().getStringArray(R.array.Flight_number);
//------------------------------------------------------------------------------------------------------

        ShowWeeklyCalendarAdapter search1 = new ShowWeeklyCalendarAdapter(this,Day_name,Day_number);
        Weekly_calendar.setAdapter(search1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Weekly_calendar.setLayoutManager(layoutManager);

        ShowUserFlightsAdapter user1 = new ShowUserFlightsAdapter(this, Departure_airport_shortcut, Departure_city, Arrival_airport_shortcut,
                Arrival_city, Duration_of_flight, Flight_date, Flight_time, Flight_number);
        Flights_found.setAdapter(user1);
        Flights_found.setLayoutManager(new LinearLayoutManager(this));

        Imagebutton_return = findViewById(R.id.back_to_Lot_ImageButton);
        Lot_tab = findViewById(R.id.back_to_Lot_TextView);
        Imagebutton_go_to_choice_seat = findViewById(R.id.go_to_choice_seat_btn);

        Imagebutton_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backAddingTicketActivity();
            }
        });

        Lot_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backAddingTicketActivity();
            }
        });
        Imagebutton_go_to_choice_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChoiceOfSeatActivity();
            }
        });

    }
    public void backAddingTicketActivity() {
        Intent intent = new Intent(this, AddingTicketActivity.class);
        startActivity(intent);
    }
    public void goToChoiceOfSeatActivity() {
        Intent intent = new Intent(this, ChoiceOfSeatActivity.class);
        startActivity(intent);
    }
}