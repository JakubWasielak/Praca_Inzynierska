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

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvUserTickets ;
    private ImageView ivEmptyData;
    private TextView tvEmptyData;

    private FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper;
    private ArrayList<String> ticket_id, ticket_depCode, ticket_depName, ticket_arrCode, ticket_arrName, ticket_flightDuration, ticket_departureDate, ticket_departureTime, ticket_travelClass, ticket_flightNumber, ticket_price, ticket_numberPassengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        rvUserTickets = findViewById(R.id.UserTickets_RecyclerView);
        ivEmptyData = findViewById(R.id.emptyData_ImageView);
        tvEmptyData = findViewById(R.id.emptyData_TextView);

        ImageButton btnAddTicked = findViewById(R.id.AddTicked_ImageButton);
        btnAddTicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchingForTicketActivity.class);
                startActivity(intent);
            }
        });

    }
}