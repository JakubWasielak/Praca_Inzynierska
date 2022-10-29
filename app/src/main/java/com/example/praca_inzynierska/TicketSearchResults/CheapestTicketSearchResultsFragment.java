package com.example.praca_inzynierska.TicketSearchResults;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.praca_inzynierska.AirlineTicketModel;
import com.example.praca_inzynierska.FAT_RecyclerViewAdapter;
import com.example.praca_inzynierska.R;
import com.example.praca_inzynierska.RecyclerViewInterface;
import com.example.praca_inzynierska.SeatingChoiceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheapestTicketSearchResultsFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView rvCheapestTicketsFound;
    private ArrayList<AirlineTicketModel> airlineTicketModel;
    private ImageButton btnOpenNextActivity;
    private String selectedDateReturn;
    private boolean oneWayFlight;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cheapest_ticket_search_results, container, false);

        Intent intent = getActivity().getIntent();
        String departureCode = intent.getStringExtra("DepartureCode");
        String arrivalCode = intent.getStringExtra("ArrivalCode");
        String selectedDate = intent.getStringExtra("SelectedDepartureDate");
        String travelClass = intent.getStringExtra("TravelClass");
        int numberPassengersAdults = intent.getIntExtra("NumberPassengersAdults",0);
        int numberPassengersChildren = intent.getIntExtra("NumberPassengersChildren",0);
        oneWayFlight = intent.getExtras().getBoolean("OneWayFlight");

        if(!oneWayFlight){
            selectedDateReturn = intent.getStringExtra("SelectedDepartureDateReturn");
        }
        btnOpenNextActivity = view.findViewById(R.id.btnOpenNexttActivity3_ImageButton);
        rvCheapestTicketsFound = view.findViewById(R.id.foundCheapestFlights_RecyclerView);
        rvCheapestTicketsFound.setLayoutManager(new LinearLayoutManager(getActivity()));
        storeRequestInRecyclerView(departureCode, arrivalCode, selectedDate, numberPassengersAdults, numberPassengersChildren, travelClass, oneWayFlight);

        return view;
    }
    private void storeRequestInRecyclerView(String origin, String destination, String departureDate, int adults, int children, String travelClass, boolean oneWayFlight) {
        airlineTicketModel = new ArrayList<>();
        String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiYzIzZjk2YjFiZDc5MWQ5ZGJhODNiOTc3M2NkNzNmNjFiMTc3OGViNDQwNzA3NjIyMTMyNTFkZmU1OGQ1MjhhZjc4Y2Y0MTYyODY1MGJlYjYiLCJpYXQiOjE2NjcwNDY5NDIsIm5iZiI6MTY2NzA0Njk0MiwiZXhwIjoxNjk4NTgyOTQyLCJzdWIiOiIxNjQ0NCIsInNjb3BlcyI6W119.cgYZWh-wsV5kTpyqsQ0QWepzxXLHqkmrw9LbBZdaQr6cDgjQ0dWuB7irUbYm8ribn6h1KdC9bgKzaC6grUGnHA";

        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1"+ "&origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate + "&cabinClass" + travelClass;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(2).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String departureCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("displayCode");
                        String departureName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("city");

                        String arrivalCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("displayCode");
                        String arrivalName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("city");

                        String flightDuration = setFlightDuration(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("durationInMinutes"));
                        String departureDate = setDepartureDate(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String departureTime = setDepartureTime(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String flightNumber = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments").getJSONObject(0).getString("flightNumber");
                        double ticketPrice = jsonArray.getJSONObject(i).getJSONObject("price").getDouble("raw");

                        airlineTicketModel.add(new AirlineTicketModel(departureCode, departureName, arrivalCode, arrivalName, flightDuration, departureDate,
                                departureTime, flightNumber, travelClass, ticketPrice, adults,children, oneWayFlight));
                    }
                    FAT_RecyclerViewAdapter FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(getActivity(), airlineTicketModel, CheapestTicketSearchResultsFragment.this);
                    rvCheapestTicketsFound.setAdapter(FAT_RecyclerViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Nie udało się znaleźć połączenia.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private String setDepartureTime(String departureDate) {
        String[] departureTimeSplit = departureDate.split("T");
        String[] TimeSplit = departureTimeSplit[1].split(":");
        return TimeSplit[0] + ":" + TimeSplit[1];
    }

    private String setDepartureDate(String departureDate) {
        String[] departureDateSplit = departureDate.split("T");
        return departureDateSplit[0];
    }

    private String setFlightDuration(String flightDuration) {
        int duration = Integer.parseInt(flightDuration);
        int hours = duration / 60;
        int minutes = (duration % 60);
        return hours + "h " + minutes + "m";
    }

    @Override
    public void onItemClick(int position) {
        btnOpenNextActivity.setVisibility(View.VISIBLE);
        btnOpenNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeatingChoiceActivity.class);
                if(!oneWayFlight){
                    airlineTicketModel.get(position).setDepartureDateReturn(selectedDateReturn);
                }
                intent.putExtra("AirlineTicketsModels", airlineTicketModel.get(position));
                startActivity(intent);
            }
        });
    }
}