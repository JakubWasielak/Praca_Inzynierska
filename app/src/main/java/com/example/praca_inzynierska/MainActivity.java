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
                flyingApplicationDatabaseHelper.removeTicket((Integer) viewHolder.itemView.getTag());
                LoadDataFromArrays();
            }
        }).attachToRecyclerView(rvUserTickets);


        ImageButton btnAddTicked = findViewById(R.id.AddTicked_ImageButton);
        btnAddTicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                flyingApplicationDatabaseHelper = new FlyingApplicationDatabaseHelper(MainActivity.this);
//                AirportModel airportModel1 = new AirportModel("WAW","Warsaw Chopin","Warsaw","Poland");
//                AirportModel airportModel2 = new AirportModel("BER","Berlin Brandenburg","Berlin","Germany");
//                flyingApplicationDatabaseHelper.addTicket(airportModel1,airportModel2,"2022-11-30","17:00","2h:30m","2000","ekonomiczna",170.00,0);
//                flyingApplicationDatabaseHelper.addPassenger("Adam","Nowak",33,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Jan","Kowalski",40,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Marek","Tomaczak",50,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Darek","Kowalczyk",60,"Mężczyzna","A1",1);
//
//                flyingApplicationDatabaseHelper.addTicket(airportModel1,airportModel2,"2022-11-27","16:00","1h:30m","1212","ekonomiczna",155.00,1);
//                flyingApplicationDatabaseHelper.addPassenger("Adam","Nowak",33,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Jan","Kowalski",40,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Marek","Tomaczak",50,"Mężczyzna","A1",1);
//                flyingApplicationDatabaseHelper.addPassenger("Darek","Kowalczyk",60,"Mężczyzna","A1",1);



                Intent intent = new Intent(MainActivity.this, SearchingForTicketActivity.class);
                startActivity(intent);
            }
        });
    }

    private void storeDataInArrays() {
        Cursor cursor = flyingApplicationDatabaseHelper.getUserTickets();
        if (cursor.getCount() == 0) {
            ivEmptyData.setVisibility(View.VISIBLE);
            tvEmptyData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                ticket_id.add(cursor.getInt(0));
                ticket_depName.add(cursor.getString(1));
                ticket_depCode.add(cursor.getString(2));
                ticket_arrName.add(cursor.getString(3));
                ticket_arrCode.add(cursor.getString(4));
                ticket_departureDate.add(cursor.getString(5));
                ticket_departureTime.add(cursor.getString(6));
                ticket_flightDuration.add(cursor.getString(7));
                ticket_flightNumber.add(cursor.getString(8));
                ticket_travelClass.add(cursor.getString(9));
                ticket_price.add(cursor.getDouble(10));
                ticket_isConnecting.add(cursor.getInt(11));
                ticket_adults_passengers.add(flyingApplicationDatabaseHelper.getNumberAdultPassengers(cursor.getInt(0)));
                ticket_children_passengers.add(flyingApplicationDatabaseHelper.getNumberChildrenPassengers(cursor.getInt(0)));
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
        ticket_isConnecting = new ArrayList<>();
        ticket_adults_passengers = new ArrayList<>();
        ticket_children_passengers = new ArrayList<>();

        storeDataInArrays();
        UserTicketsAdapter userTicketsAdapter= new UserTicketsAdapter(MainActivity.this, ticket_id, ticket_depCode, ticket_depName, ticket_arrCode, ticket_arrName, ticket_flightDuration, ticket_departureDate, ticket_departureTime, ticket_flightNumber, ticket_travelClass, ticket_price, ticket_adults_passengers, ticket_children_passengers, ticket_isConnecting);
        rvUserTickets.setAdapter(userTicketsAdapter);
        rvUserTickets.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
}