package com.example.praca_inzynierska;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchResultsActivity extends AppCompatActivity implements RecyclerViewInterface {
    private TextView tvSearchedNumberTicketsFound;
    private TextView tvSearchedDepartureCode;
    private TextView tvSearchedArrivalCode;
    private RecyclerView rvTicketsFound;
    private ImageButton btnOpenNextActivity;

    private ArrayList<AirlineTicketModel> airlineTicketModel;
    private boolean oneWayFlight;
    private LocalDate selectedDateReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);


        String departureCode = getIntent().getStringExtra("DepartureCode");
        String arrivalCode = getIntent().getStringExtra("ArrivalCode");
        LocalDate selectedDate = (LocalDate) getIntent().getSerializableExtra("SelectedDepartureDate");
        int numberPassengers = Integer.parseInt(getIntent().getStringExtra("NumberPassengers"));
        String travelClass = getIntent().getStringExtra("TravelClass");
        oneWayFlight = getIntent().getExtras().getBoolean("OneWayFlight");

        if(!oneWayFlight){
            selectedDateReturn = (LocalDate) getIntent().getSerializableExtra("SelectedDepartureDateReturn");
        }

        rvTicketsFound = findViewById(R.id.foundFlights_RecyclerView);
        tvSearchedNumberTicketsFound = findViewById(R.id.numberOfTicketsFound_TextView);
        tvSearchedDepartureCode = findViewById(R.id.searchedDeparture_TextView);
        tvSearchedArrivalCode = findViewById(R.id.searchedArrival_TextView);
        btnOpenNextActivity = findViewById(R.id.goToSeatingChoiceActivity_ImageButton);

        rvTicketsFound.setLayoutManager(new LinearLayoutManager(this));
        storeDataInRecyclerView(departureCode, arrivalCode, selectedDate, numberPassengers, travelClass, oneWayFlight);

        ImageButton btnPreviousActivity = findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void storeDataInRecyclerView(String departureCode, String arrivalCode, LocalDate selectedDate, int numberPassengers, String travelClass, Boolean oneWayFlight) {
        airlineTicketModel = new ArrayList<>();
        String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNDMyNDYwODY5ODdmZDUxZWQ0OWFhYzM5N2U3ZmNlMDcyYzUwNTIyZDFhYmNmNzg3NDY3OWJmNjRlNzAzMGJhOTQ1ZjQ2NDE2NzllNWI2M2QiLCJpYXQiOjE2NjYzNTgwNjAsIm5iZiI6MTY2NjM1ODA2MCwiZXhwIjoxNjk3ODk0MDYwLCJzdWIiOiIxNTcyNCIsInNjb3BlcyI6W119.n-qCwXT6OuyD6B1bQ-sPqElGEx96y_8GiYuIee29pFKPT7HNzymQuf7Ov9UVTjERwJr3AUVwBUEhSiBHqu6J7w";
        String url = "https://app.goflightlabs.com/routes?access_key=" + access_key + "&dep_iata=" + departureCode + "&arr_iata=" + arrivalCode;

        RequestQueue queue = Volley.newRequestQueue(SearchResultsActivity.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject jsonObject_departure = jsonObject.getJSONObject("departure");
                        JSONObject jsonObject_arrival = jsonObject.getJSONObject("arrival");
                        JSONObject jsonObject_flight = jsonObject.getJSONObject("flight");

                        String departureName = jsonObject_departure.getString("airport");
                        String arrivalName = jsonObject_arrival.getString("airport");
                        String flightDuration = setFlightDuration(jsonObject_departure.getString("time"), jsonObject_arrival.getString("time"));
                        String departureDate = selectedDate.getDayOfMonth() + " " + selectedDate.getMonth() + " " + selectedDate.getYear();
                        String departureTime = setDepartureTime(jsonObject_departure.getString("time"));
                        String flightNumber = jsonObject_flight.getString("number");
                        double ticketPrice = setTicketPrice();

                        airlineTicketModel.add(new AirlineTicketModel(departureCode, departureName, arrivalCode, arrivalName, flightDuration, departureDate,
                                departureTime, flightNumber, travelClass, ticketPrice, numberPassengers,oneWayFlight));
                    }
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(SearchResultsActivity.this, airlineTicketModel, SearchResultsActivity.this);
                    rvTicketsFound.setAdapter(FAT_RecyclerViewAdapter);
                    tvSearchedNumberTicketsFound.setText(String.valueOf(response.length()));
                    tvSearchedDepartureCode.setText(departureCode);
                    tvSearchedArrivalCode.setText(arrivalCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchResultsActivity.this, "Nie udało się znaleźć połączenia.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


    private String setDepartureTime(String depTime) {
        String[] departureTimeSplit = depTime.split(":");
        return departureTimeSplit[0]+ ":" + departureTimeSplit[1];
    }

    private String setFlightDuration(String depTime, String arrTime) {
        String[] departureTimeSplit = depTime.split(":");
        String[] arrivalTimeSplit = arrTime.split(":");
        int hours = Integer.parseInt(departureTimeSplit[0]);
        int minutes = Integer.parseInt(departureTimeSplit[1]);
        int seconds = Integer.parseInt(departureTimeSplit[2]);
        LocalTime departureTime = LocalTime.of(hours, minutes, seconds);
        hours = Integer.parseInt(arrivalTimeSplit[0]);
        minutes = Integer.parseInt(arrivalTimeSplit[1]);
        seconds = Integer.parseInt(arrivalTimeSplit[2]);
        LocalTime arrivalTime = LocalTime.of(hours, minutes, seconds);
        hours = (int) (departureTime.until(arrivalTime, ChronoUnit.MINUTES) / 60);
        minutes = (int) (departureTime.until(arrivalTime, ChronoUnit.MINUTES) - 60 * hours);
        String hoursString = String.valueOf(hours);
        String minutesString = String.valueOf(minutes);

        return hoursString + "h " + minutesString + "min";
    }

    private double setTicketPrice() {
        Random r = new Random();
        return 50 + (200 - 50) * r.nextDouble();
    }

    @Override
    public void onItemClick(int position) {
        btnOpenNextActivity.setVisibility(View.VISIBLE);
        btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultsActivity.this, SeatingChoiceActivity.class);
                if(!oneWayFlight){
                    airlineTicketModel.get(position).setDepartureDateReturn(selectedDateReturn.getDayOfMonth() + " " + selectedDateReturn.getMonthValue() + " " + selectedDateReturn.getYear());
                }
                intent.putExtra("AirlineTicketsModels", airlineTicketModel.get(position));
                startActivity(intent);
            }
        });

    }
}
