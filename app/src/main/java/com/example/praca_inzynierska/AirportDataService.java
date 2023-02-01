package com.example.praca_inzynierska;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AirportDataService {
    Context context;
    String departureAirportCode;
    String departureAirportName;
    String departureAirportCity;
    String departureAirportCountry;
    String arrivalAirportCode;
    String arrivalAirportName;
    String arrivalAirportCity;
    String arrivalAirportCountry;
    String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMGE4N2Y1NDRhY2M2ZDUyOWY5MmY1NjEwZTU3YTMzODM1NDE1OTQ1MjI5NjFkNTZjNmMzYWFmZTE5NGM2NjYwNTRlNDYwNGNmNWJlYTZiZjEiLCJpYXQiOjE2NjcwNjg1MjgsIm5iZiI6MTY2NzA2ODUyOCwiZXhwIjoxNjk4NjA0NTI4LCJzdWIiOiIxNjQ3NCIsInNjb3BlcyI6W119.SR6i0lqjeBCKEFgsHKHMH3d44UAcc2TV7toHMj155uCuBisxjlpdzFD3UTDJbdI6OYgpaYQ7UBEGxLYOfCGVlQ";

    public AirportDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String departureAirportCode, String departureAirportName, String departureAirportCity, String Code);
    }

    public void getDepartureCode(String departureCountry, VolleyResponseListener volleyResponseListener) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + access_key + "&query=" + departureCountry;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                departureAirportCode = "";
                departureAirportName = "";
                departureAirportCity = "";
                departureAirportCountry = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    departureAirportCode = jsonArray.getJSONObject(1).getString("iata_code");
                    departureAirportName = jsonArray.getJSONObject(1).getString("name");
                    departureAirportCity = jsonArray.getJSONObject(1).getString("city");
                    departureAirportCountry = jsonArray.getJSONObject(1).getString("country");

                    volleyResponseListener.onResponse(departureAirportCode, departureAirportName, departureAirportCity, departureAirportCountry);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Error!");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getArrivalCode(String arrivalCountry, VolleyResponseListener volleyResponseListener) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + access_key + "&query=" + arrivalCountry;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                arrivalAirportCode = "";
                arrivalAirportName = "";
                arrivalAirportCity = "";
                arrivalAirportCountry = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    arrivalAirportCode = jsonArray.getJSONObject(1).getString("iata_code");
                    arrivalAirportName = jsonArray.getJSONObject(1).getString("name");
                    arrivalAirportCity = jsonArray.getJSONObject(1).getString("city");
                    arrivalAirportCountry = jsonArray.getJSONObject(1).getString("country");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(arrivalAirportCode, arrivalAirportName, arrivalAirportCity, arrivalAirportCountry);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Error!");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
