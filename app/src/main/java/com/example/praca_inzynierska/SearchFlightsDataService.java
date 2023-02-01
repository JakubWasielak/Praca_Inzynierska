package com.example.praca_inzynierska;

import android.content.Context;

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
    String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMGE4N2Y1NDRhY2M2ZDUyOWY5MmY1NjEwZTU3YTMzODM1NDE1OTQ1MjI5NjFkNTZjNmMzYWFmZTE5NGM2NjYwNTRlNDYwNGNmNWJlYTZiZjEiLCJpYXQiOjE2NjcwNjg1MjgsIm5iZiI6MTY2NzA2ODUyOCwiZXhwIjoxNjk4NjA0NTI4LCJzdWIiOiIxNjQ3NCIsInNjb3BlcyI6W119.SR6i0lqjeBCKEFgsHKHMH3d44UAcc2TV7toHMj155uCuBisxjlpdzFD3UTDJbdI6OYgpaYQ7UBEGxLYOfCGVlQ";


    public SearchFlightsDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(ArrayList<AirlineTicketModel> airlineTicketModel);
    }

    public void getCheapestTickets(AirportModel departureAirport, AirportModel arrivalAirport, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {

        ArrayList<AirlineTicketModel> ticketsFoundList = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + departureAirport.getAirportCode() + "&destination=" + arrivalAirport.getAirportCode()  + "&departureDate=" + departureDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(2).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String flightDuration = setFlightDuration(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("durationInMinutes"));
                        String departureDate = setDepartureDate(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String departureTime = setDepartureTime(jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getString("departure"));
                        String flightNumber = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments").getJSONObject(0).getString("flightNumber");
                        double ticketPrice = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("price").getString("raw"));
                        JSONArray segments = jsonArray.getJSONObject(i).getJSONArray("legs").getJSONObject(0).getJSONArray("segments");
                        int ticketConnecting;
                        if(segments.length() > 1){
                            ticketConnecting=1;
                        }else{
                            ticketConnecting=0;
                        }
                        AirlineTicketModel foundTicket = new AirlineTicketModel(departureAirport,arrivalAirport,flightDuration,departureDate,departureTime,flightNumber,travelClass,ticketPrice,numberPassengersAdults,numberPassengersChildren,oneWayFlight,ticketConnecting);
                        ticketsFoundList.add(foundTicket);
                    }
                    volleyResponseListener.onResponse(ticketsFoundList);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Error.");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getFastestTickets(AirportModel departureAirport, AirportModel arrivalAirport, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {
        ArrayList<AirlineTicketModel> ticketsFoundList = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + departureAirport.getAirportCode() + "&destination=" + arrivalAirport.getAirportCode() + "&departureDate=" + departureDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(1).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
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
                        AirlineTicketModel foundTicket = new AirlineTicketModel(departureAirport,arrivalAirport,flightDuration,departureDate,departureTime,flightNumber,travelClass,ticketPrice,numberPassengersAdults,numberPassengersChildren,oneWayFlight,ticketConnecting);
                        ticketsFoundList.add(foundTicket);
                    }
                    volleyResponseListener.onResponse(ticketsFoundList);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Error.");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getBestTickets(AirportModel departureAirport, AirportModel arrivalAirport, String departureDate, String travelClass, int numberPassengersAdults, int numberPassengersChildren, boolean oneWayFlight, VolleyResponseListener volleyResponseListener) {
        ArrayList<AirlineTicketModel> ticketsFoundList = new ArrayList<>();
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + access_key + "&adults=1" + "&origin=" + departureAirport.getAirportCode() + "&destination=" + arrivalAirport.getAirportCode() + "&departureDate=" + departureDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("buckets").getJSONObject(0).getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
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
                        AirlineTicketModel foundTicket = new AirlineTicketModel(departureAirport,arrivalAirport,flightDuration,departureDate,departureTime,flightNumber,travelClass,ticketPrice,numberPassengersAdults,numberPassengersChildren,oneWayFlight,ticketConnecting);
                        ticketsFoundList.add(foundTicket);
                    }
                    volleyResponseListener.onResponse(ticketsFoundList);
                } catch (JSONException e) {
                    volleyResponseListener.onError("Error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Error.");
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