package com.example.praca_inzynierska;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFlightsDataService {
    Context context;
    String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMzRiNTA3NjU1MWM1ODk2ZWIxMGE4MzVkMDlmZDE4ODIxNGRkMmVhOTgxMDFjYzUxOGMyOWE5MjIwMzNjNjcyOGRmNzI5ZjAyZDZhYTYzMGEiLCJpYXQiOjE2NjcyMjc5MTcsIm5iZiI6MTY2NzIyNzkxNywiZXhwIjoxNjk4NzYzOTE3LCJzdWIiOiIxNjU1NCIsInNjb3BlcyI6W119.KvFZdK5HzZ-d3iG8TNnVtM2ugGYKTRErYYTqoboLc-jpzly22y_HyGn_-5Z0JUbFzWwRTDZRu-BZwG1Cbw-JJg";


    public SearchFlightsDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(ArrayList<AirlineTicketModel> airlineTicketModel);
    }

    public void getCheapestTickets(String origin, String destination, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {
        ArrayList<AirlineTicketModel> airlineTicketModel = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate;

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
                        double ticketPrice = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("price").getString("raw"));
                        JSONArray segments =jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments");
                        int ticketConnecting;
                        if(segments.length() > 1){
                            ticketConnecting=1;
                        }else{
                            ticketConnecting=0;
                        }


                        AirlineTicketModel ticket = new AirlineTicketModel(departureCode, departureName, arrivalCode, arrivalName,
                                flightDuration, departureDate, departureTime, flightNumber, travelClass, ticketPrice, numberPassengersAdults, numberPassengersChildren, oneWayFlight,ticketConnecting);

                        airlineTicketModel.add(ticket);

                    }
                    volleyResponseListener.onResponse(airlineTicketModel);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Nie udało się znaleźć połączenia.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getFastestTickets(String origin, String destination, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {
        ArrayList<AirlineTicketModel> airlineTicketModel = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(1).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String departureCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("displayCode");
                        String departureName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("city");
                        String arrivalCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("displayCode");
                        String arrivalName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("city");
                        String flightDuration = setFlightDuration(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("durationInMinutes"));
                        String departureDate = setDepartureDate(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String departureTime = setDepartureTime(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String flightNumber = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments").getJSONObject(0).getString("flightNumber");
                        double ticketPrice = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("price").getString("raw"));
                        JSONArray segments =jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments");
                        int ticketConnecting;
                        if(segments.length() > 1){
                            ticketConnecting=1;
                        }else{
                            ticketConnecting=0;
                        }

                        AirlineTicketModel ticket = new AirlineTicketModel(departureCode, departureName, arrivalCode, arrivalName,
                                flightDuration, departureDate, departureTime, flightNumber, travelClass, ticketPrice, numberPassengersAdults, numberPassengersChildren, oneWayFlight,ticketConnecting);

                        airlineTicketModel.add(ticket);

                    }
                    volleyResponseListener.onResponse(airlineTicketModel);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Nie udało się znaleźć połączenia.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getBestTickets(String origin, String destination, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {
        ArrayList<AirlineTicketModel> airlineTicketModel = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(0).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String departureCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("displayCode");
                        String departureName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("origin").getString("city");
                        String arrivalCode = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("displayCode");
                        String arrivalName = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONObject("destination").getString("city");
                        String flightDuration = setFlightDuration(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("durationInMinutes"));
                        String departureDate = setDepartureDate(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String departureTime = setDepartureTime(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String flightNumber = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments").getJSONObject(0).getString("flightNumber");
                        double ticketPrice = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("price").getString("raw"));
                        JSONArray segments =jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments");
                        int ticketConnecting;
                        if(segments.length() > 1){
                            ticketConnecting=1;
                        }else{
                            ticketConnecting=0;
                        }

                        AirlineTicketModel ticket = new AirlineTicketModel(departureCode, departureName, arrivalCode, arrivalName,
                                flightDuration, departureDate, departureTime, flightNumber, travelClass, ticketPrice, numberPassengersAdults, numberPassengersChildren, oneWayFlight,ticketConnecting);

                        airlineTicketModel.add(ticket);

                    }
                    volleyResponseListener.onResponse(airlineTicketModel);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Nie udało się znaleźć połączenia.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
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


}
