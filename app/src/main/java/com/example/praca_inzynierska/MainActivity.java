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
    private ArrayList<Integer> ticket_id, ticket_adults_passengers, ticket_children_passengers;
    private ArrayList<String> ticket_depCode, ticket_depName, ticket_arrCode, ticket_arrName, ticket_flightDuration, ticket_departureDate, ticket_departureTime, ticket_travelClass, ticket_flightNumber;
    private ArrayList<Double> ticket_price;
    private ArrayList<Integer> ticket_isConnecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        rvUserTickets = findViewById(R.id.UserTickets_RecyclerView);
        ivEmptyData = findViewById(R.id.emptyData_ImageView);
        tvEmptyData = findViewById(R.id.emptyData_TextView);

        LoadDataFromArrays();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(MainActivity.this);
                flyingApplicationDatabaseHelper.deleteAllPassengers((Integer) viewHolder.itemView.getTag());
                flyingApplicationDatabaseHelper.deleteSelectedTicket((Integer) viewHolder.itemView.getTag());
                LoadDataFromArrays();
            }
        }).attachToRecyclerView(rvUserTickets);


        ImageButton btnAddTicked = findViewById(R.id.AddTicked_ImageButton);
        btnAddTicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchingForTicketActivity.class);
//                startActivity(intent);
                flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(MainActivity.this);
                System.out.println(flyingApplicationDatabaseHelper.getNextTickedID());
                DanDoTestowania();
            }
        });
    }

    private void storeDataInArrays() {
        Cursor cursor = flyingApplicationDatabaseHelper.readAllTickets();
        if (cursor.getCount() == 0) {
            ivEmptyData.setVisibility(View.VISIBLE);
            tvEmptyData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                ticket_id.add(cursor.getInt(0));

                ticket_depCode.add(cursor.getString(1));
                ticket_depName.add(cursor.getString(2));
                ticket_arrCode.add(cursor.getString(3));
                ticket_arrName.add(cursor.getString(4));

                ticket_flightDuration.add(cursor.getString(5));
                ticket_departureDate.add(cursor.getString(6));
                ticket_departureTime.add(cursor.getString(7));

                ticket_flightNumber.add(cursor.getString(8));
                ticket_travelClass.add(cursor.getString(9));
                ticket_price.add(cursor.getDouble(10));

                ticket_adults_passengers.add(cursor.getInt(11));
                ticket_children_passengers.add(cursor.getInt(12));
                ticket_isConnecting.add(cursor.getInt(13));
            }
        }
    }

    private void LoadDataFromArrays() {
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
        ticket_adults_passengers = new ArrayList<>();
        ticket_children_passengers = new ArrayList<>();
        ticket_isConnecting = new ArrayList<>();

        storeDataInArrays();
        ShowUserTicketsAdapter showUserTicketsAdapter = new ShowUserTicketsAdapter(MainActivity.this, ticket_id, ticket_depCode, ticket_depName, ticket_arrCode, ticket_arrName, ticket_flightDuration, ticket_departureDate, ticket_departureTime, ticket_flightNumber, ticket_travelClass, ticket_price, ticket_adults_passengers, ticket_children_passengers, ticket_isConnecting);
        rvUserTickets.setAdapter(showUserTicketsAdapter);
        rvUserTickets.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void DanDoTestowania() {
        flyingApplicationDatabaseHelper.addNewTicket("MUC", "Monachium", "WAW", "Warszawa", "1h:45m", "2022-11-11",
                "20:30", "2115", "first", 205.99, 3, 0, 0);
        flyingApplicationDatabaseHelper.addNewPassager("Kuba", "Wasielak", 23, "mężczyzna", "A1", 1);
        flyingApplicationDatabaseHelper.addNewPassager("Adam", "Nowak", 33, "mężczyzna", "A2", 1);
        flyingApplicationDatabaseHelper.addNewPassager("Jan", "Kowalski", 43, "mężczyzna", "A3", 1);

        flyingApplicationDatabaseHelper.addNewTicket("MAD", "Madryt", "WAW", "Warszawa", "2h:45m", "2022-11-12",
                "21:30", "2114", "first", 215.99, 2, 0, 0);
        flyingApplicationDatabaseHelper.addNewPassager("Kuba", "Wasielak", 23, "mężczyzna", "A4", 2);
        flyingApplicationDatabaseHelper.addNewPassager("Adam", "Nowak", 33, "mężczyzna", "B1", 2);

        flyingApplicationDatabaseHelper.addNewTicket("BER", "Berlin", "WAW", "Warszawa", "3h:45m", "2022-11-14",
                "22:30", "2113", "first", 235.99, 1, 0, 0);
        flyingApplicationDatabaseHelper.addNewPassager("Kuba", "Wasielak", 23, "mężczyzna", "B2", 3);
    }
}