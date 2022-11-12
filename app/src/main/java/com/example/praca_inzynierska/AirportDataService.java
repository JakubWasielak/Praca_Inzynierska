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
    String departureCode;
    String arrivalCode;
    String access_key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMzRiNTA3NjU1MWM1ODk2ZWIxMGE4MzVkMDlmZDE4ODIxNGRkMmVhOTgxMDFjYzUxOGMyOWE5MjIwMzNjNjcyOGRmNzI5ZjAyZDZhYTYzMGEiLCJpYXQiOjE2NjcyMjc5MTcsIm5iZiI6MTY2NzIyNzkxNywiZXhwIjoxNjk4NzYzOTE3LCJzdWIiOiIxNjU1NCIsInNjb3BlcyI6W119.KvFZdK5HzZ-d3iG8TNnVtM2ugGYKTRErYYTqoboLc-jpzly22y_HyGn_-5Z0JUbFzWwRTDZRu-BZwG1Cbw-JJg";


    public AirportDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String Code);
    }

    public void getDepartureCode(String departureCountry, VolleyResponseListener volleyResponseListener) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + access_key + "&query=" + departureCountry;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                departureCode = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    int random = (int )(Math.random() * 4 + 1);
                    departureCode = jsonArray.getJSONObject(random).getString("iata_code");
                    volleyResponseListener.onResponse(departureCode);

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

    public void getArrivalCode(String arrivalCountry,VolleyResponseListener volleyResponseListener) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + access_key + "&query=" + arrivalCountry;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                arrivalCode = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    int random = (int )(Math.random() * 4 + 1);
                    arrivalCode = jsonArray.getJSONObject(random).getString("iata_code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(arrivalCode);
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
