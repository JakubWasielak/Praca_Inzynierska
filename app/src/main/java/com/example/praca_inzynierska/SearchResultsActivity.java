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
    private ArrayList<FoundAirlineTicketsModel> foundAirlineTicketsModels;
    private ImageButton goToNextActivity_ImageButton;
    private TextView numberOfTicketsFound_TextView;
    private TextView searchedDeparture_TextView;
    private TextView searchedArrival_TextView;
    private RecyclerView foundFlightsRecyclerView;
    private int numberPassengers;
    private String travelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);

        //Reading the transmitted value
        LocalDate selectedDate = (LocalDate) getIntent().getSerializableExtra("SelectedDate");
        String departureCode = getIntent().getStringExtra("CodeDeparture");
        String arrivalCode = getIntent().getStringExtra("CodeArrival");
        numberPassengers = Integer.parseInt(getIntent().getStringExtra("NumberPassengers"));
        travelClass = getIntent().getStringExtra("travelClass");

        //Search Results
        foundFlightsRecyclerView = findViewById(R.id.foundFlights_RecyclerView);
        numberOfTicketsFound_TextView = findViewById(R.id.numberOfTicketsFound_TextView);
        searchedDeparture_TextView = findViewById(R.id.searchedDeparture_TextView);
        searchedArrival_TextView = findViewById(R.id.searchedArrival_TextView);
        foundFlightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addFoundTickets(departureCode, arrivalCode, selectedDate);


        //Open next Activity
        goToNextActivity_ImageButton = findViewById(R.id.goToSeatingChoiceActivity_ImageButton);
        ImageButton goToPreviousActivity_ImageButton = findViewById(R.id.goToPreviousActivity_ImageButton);
        goToPreviousActivity_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addFoundTickets(String departureCode, String arrivalCode, LocalDate selectedDate) {
        foundAirlineTicketsModels = new ArrayList<>();
        String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMTE2MDNkZjUwYmE4MjFlYTdmOTBiOTRiM2ZiZGZlZjkyMjFkNTkwMGYxMWUzNGIyMDNmYmZlM2MwNTY3MzY4NmRlN2QzZDBlMTNhYmI5NmUiLCJpYXQiOjE2NjExODg5OTcsIm5iZiI6MTY2MTE4ODk5NywiZXhwIjoxNjkyNzI0OTk3LCJzdWIiOiIxMTA1NCIsInNjb3BlcyI6W119.l3CEK2HL21-jP-dkjSe07jAUCBzCREbbZcnBzIpGp_0D3ytduROOTweAnkSd3o6u12lJtHvgubYnVIzvl--E2g";
        String url = "https://app.goflightlabs.com/routes?access_key=" + access_key + "&dep_iata=" + departureCode + "&arr_iata=" + arrivalCode;

        RequestQueue queue = Volley.newRequestQueue(SearchResultsActivity.this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String departureCode = "";
                String departureName = "";
                String arrivalCode = "";
                String arrivalName = "";
                String flightDuration = "";
                String departureDateAndTime = "";
                String flightNumber = "";
                double ticketPrice;

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject jsonObject_departure = jsonObject.getJSONObject("departure");
                        JSONObject jsonObject_arrival = jsonObject.getJSONObject("arrival");
                        JSONObject jsonObject_flight = jsonObject.getJSONObject("flight");

                        departureCode = jsonObject_departure.getString("iata");
                        departureName = jsonObject_departure.getString("airport");
                        arrivalCode = jsonObject_arrival.getString("iata");
                        arrivalName = jsonObject_arrival.getString("airport");
                        flightDuration = setFlightTime(jsonObject_departure.getString("time"), jsonObject_arrival.getString("time"));
                        departureDateAndTime = setDepartureDate(selectedDate, jsonObject_departure.getString("time"));
                        flightNumber = jsonObject_flight.getString("number");
                        ticketPrice = setTicketPrice();

                        foundAirlineTicketsModels.add(new FoundAirlineTicketsModel(departureCode,departureName,arrivalCode,arrivalName,flightDuration,departureDateAndTime,
                                flightNumber,ticketPrice,numberPassengers,travelClass));

                    }
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(SearchResultsActivity.this, foundAirlineTicketsModels, SearchResultsActivity.this);
                    foundFlightsRecyclerView.setAdapter(FAT_RecyclerViewAdapter);
                    String numberOfTickets = String.valueOf(response.length());
                    numberOfTicketsFound_TextView.setText(numberOfTickets);
                    searchedDeparture_TextView.setText(departureCode);
                    searchedArrival_TextView.setText(arrivalCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchResultsActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private String setDepartureDate(LocalDate selectedDate, String depTime) {
        String[] departureTimeSplit = depTime.split(":");
        int hours = Integer.parseInt(departureTimeSplit[0]);
        int minutes = Integer.parseInt(departureTimeSplit[1]);
        int seconds = Integer.parseInt(departureTimeSplit[2]);
        LocalTime departureTime = LocalTime.of(hours, minutes, seconds);
        String month = String.valueOf(selectedDate.getMonth());

        switch (month) {
            case "JANUARY":
                month = "Styczeń";
                break;
            case "FEBRUARY":
                month = "Luty";
                break;
            case "MARCH":
                month = "Marzec";
                break;
            case "APRIL":
                month = "Kwiecień";
                break;
            case "MAY":
                month = "Maj";
                break;
            case "JUNE":
                month = "Czerwiec";
                break;
            case "JULY":
                month = "Lipiec";
                break;
            case "AUGUST":
                month = "Sierpień";
                break;
            case "SEPTEMBER":
                month = "Wrzesień";
                break;
            case "OCTOBER":
                month = "Październik";
                break;
            case "NOVEMBER":
                month = "Listopad";
                break;
            case "DECEMBER":
                month = "Grudzień";
                break;
        }

        return selectedDate.getDayOfMonth() + " " + month + ", " + departureTime;
    }

    private String setFlightTime(String depTime, String arrTime) {
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
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double random = 50 + (200 - 50) * r.nextDouble();
        return random;
    }

    @Override
    public void onItemClick(int position) {
        goToNextActivity_ImageButton.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SearchResultsActivity.this, SeatingChoiceActivity.class);


        goToNextActivity_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultsActivity.this, SeatingChoiceActivity.class);
                intent.putExtra("AirlineTicketsModels",foundAirlineTicketsModels.get(position));
                startActivity(intent);
            }
        });

    }
}
