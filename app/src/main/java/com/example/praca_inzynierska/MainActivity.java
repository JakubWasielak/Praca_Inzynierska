package com.example.praca_inzynierska;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvUserTickets;
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


        LoadDataFromDatabase();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                FlyingApplicationDatabaseHelper flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(MainActivity.this);
                flyingApplicationDatabaseHelper.deleteTicket(String.valueOf(viewHolder.itemView.getTag()));
                LoadDataFromDatabase();
                System.out.println(ticket_id.size());
                LoadDataFromDatabase();
            }
        }).attachToRecyclerView(rvUserTickets);


        ImageButton btnAddTicked = findViewById(R.id.AddTicked_ImageButton);
        btnAddTicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchingForTicketActivity.class);
                startActivity(intent);
            }
        });
    }

    private void storeDataInArrays() {
        Cursor cursor = flyingApplicationDatabaseHelper.readAllData();
        if (cursor.getCount() == 0) {
            ivEmptyData.setVisibility(View.VISIBLE);
            tvEmptyData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                ticket_id.add(cursor.getString(0));
                ticket_depCode.add(cursor.getString(1));
                ticket_depName.add(cursor.getString(2));
                ticket_arrCode.add(cursor.getString(3));
                ticket_arrName.add(cursor.getString(4));
                ticket_flightDuration.add(cursor.getString(5));
                ticket_departureDate.add(cursor.getString(6));
                ticket_departureTime.add(cursor.getString(7));
                ticket_travelClass.add(cursor.getString(8));
                ticket_flightNumber.add(cursor.getString(9));
                ticket_price.add(cursor.getString(10));
                ticket_numberPassengers.add(cursor.getString(11));
            }
        }
    }

    private void LoadDataFromDatabase() {
        flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(MainActivity.this);
        ticket_id = new ArrayList<>();
        ticket_depCode = new ArrayList<>();
        ticket_depName = new ArrayList<>();
        ticket_arrCode = new ArrayList<>();
        ticket_arrName = new ArrayList<>();
        ticket_flightDuration = new ArrayList<>();
        ticket_departureDate = new ArrayList<>();
        ticket_departureTime = new ArrayList<>();
        ticket_travelClass = new ArrayList<>();
        ticket_flightNumber = new ArrayList<>();
        ticket_price = new ArrayList<>();
        ticket_numberPassengers = new ArrayList<>();
        storeDataInArrays();
        ShowUserTicketsAdapter showUserTicketsAdapter = new ShowUserTicketsAdapter(MainActivity.this, ticket_id, ticket_depCode, ticket_depName, ticket_arrCode, ticket_arrName, ticket_flightDuration, ticket_departureDate, ticket_departureTime, ticket_travelClass, ticket_flightNumber, ticket_price, ticket_numberPassengers);
        rvUserTickets.setAdapter(showUserTicketsAdapter);
        rvUserTickets.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
}