package com.example.praca_inzynierska;

import static java.time.temporal.ChronoUnit.DAYS;

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
    public ArrayList<FoundAirlineTicketsModel> foundAirlineTicketsModels = new ArrayList<>();
    private ImageButton goToNextActivity_ImageButton;
    RecyclerView foundFlightsRecyclerView;
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

        //Weekly Calendar
        RecyclerView weeklyCalendar_RecyclerView = findViewById(R.id.weeklyCalendar_RecyclerView);
        ArrayList<String> dayNumberView = getNumberOfDay(selectedDate);
        ArrayList<String> dayNameView = getNameOfDay(selectedDate);
        ShowWeeklyCalendarAdapter showWeeklyCalendarAdapter = new ShowWeeklyCalendarAdapter(this,dayNameView,dayNumberView);
        weeklyCalendar_RecyclerView.setAdapter(showWeeklyCalendarAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        weeklyCalendar_RecyclerView.setLayoutManager(layoutManager);

        //Search Results
        foundFlightsRecyclerView = findViewById(R.id.foundFlights_RecyclerView);
        foundFlightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addFoundTickets(departureCode,arrivalCode, selectedDate);


        //Open next Activity
        goToNextActivity_ImageButton = findViewById(R.id.goToNextActivity_ImageButton);


    }

    private static ArrayList<String> getNumberOfDay(LocalDate selecteddate) {
        ArrayList<String> NumberOfdays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);

        if(!selecteddate.equals(today)) {
            if(daysBetween<7) {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++) {

                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++) {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            } else {
                int j =6;
                for(int i=0;i<7;i++) {
                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++) {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            }
        } else {
            for(int i=0;i<=7;i++) {
                NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
            }
        }
        return NumberOfdays;
    }

    private static ArrayList<String> getNameOfDay(LocalDate selecteddate) {
        ArrayList<String> NameOfdays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);
        if(!selecteddate.equals(today)) {
            if(daysBetween<7) {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++) {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++) {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            } else {
                int j =6;
                for(int i=0;i<7;i++) {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++) {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            }
        } else {
            for(int i=0;i<=14;i++) {
                NameOfdays.add(nameDayOfWeek(selecteddate.plusDays(i)));
            }
        }
        return NameOfdays;
    }

    private static String nameDayOfWeek(LocalDate selecteddate) {
        switch (String.valueOf(selecteddate.getDayOfWeek())) {
            case "MONDAY":
                return "Pon";
            case "TUESDAY":
                return "Wto";
            case "WEDNESDAY":
                return "Śro";
            case "THURSDAY":
                return "Czw";
            case "FRIDAY":
                return "Pią";
            case "SATURDAY":
                return "Sob";
            case "SUNDAY":
                return "Nie";
            default:
                System.out.println("Błąd!!");
        }
        return null;
    }


    public void addFoundTickets(String departureCode, String arrivalCode, LocalDate selectedDate){

        String access_key="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMTE2MDNkZjUwYmE4MjFlYTdmOTBiOTRiM2ZiZGZlZjkyMjFkNTkwMGYxMWUzNGIyMDNmYmZlM2MwNTY3MzY4NmRlN2QzZDBlMTNhYmI5NmUiLCJpYXQiOjE2NjExODg5OTcsIm5iZiI6MTY2MTE4ODk5NywiZXhwIjoxNjkyNzI0OTk3LCJzdWIiOiIxMTA1NCIsInNjb3BlcyI6W119.l3CEK2HL21-jP-dkjSe07jAUCBzCREbbZcnBzIpGp_0D3ytduROOTweAnkSd3o6u12lJtHvgubYnVIzvl--E2g";
        String url="https://app.goflightlabs.com/routes?access_key="+access_key+"&dep_iata="+departureCode+"&arr_iata="+arrivalCode;

        RequestQueue queue = Volley.newRequestQueue(SearchResultsActivity.this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String departureCode="";
                String departureName="";
                String arrivalCode="";
                String arrivalName="";
                String flightDuration="";
                String departureDateAndTime="";
                String flightNumber="";
                String ticketPrice="";

                try {
                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject= response.getJSONObject(i);
                        JSONObject jsonObject_departure = jsonObject.getJSONObject("departure");
                        JSONObject jsonObject_arrival = jsonObject.getJSONObject("arrival");
                        JSONObject jsonObject_flight = jsonObject.getJSONObject("flight");

                        departureCode = jsonObject_departure.getString("iata");
                        departureName = jsonObject_departure.getString("airport");
                        arrivalCode = jsonObject_arrival.getString("iata");
                        arrivalName = jsonObject_arrival.getString("airport");
                        flightDuration = setFlightTime(jsonObject_departure.getString("time"),jsonObject_arrival.getString("time"));
                        departureDateAndTime = setDepartureDate(selectedDate,jsonObject_departure.getString("time"));
                        flightNumber =jsonObject_flight.getString("number");
                        ticketPrice=setTicketPrice();

                        foundAirlineTicketsModels.add(new FoundAirlineTicketsModel(departureCode,departureName,
                                arrivalCode,arrivalName,flightDuration,departureDateAndTime,flightNumber,ticketPrice));
                    }
                    FAT_RecyclerViewAdapter  FAT_RecyclerViewAdapter = new FAT_RecyclerViewAdapter(SearchResultsActivity.this,foundAirlineTicketsModels,SearchResultsActivity.this);
                    foundFlightsRecyclerView.setAdapter(FAT_RecyclerViewAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
                String odp="Departure Name: "+departureName+" -/- Arrival Name: "+arrivalName;
                Toast.makeText(SearchResultsActivity.this,odp,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchResultsActivity.this,"Error!!!",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public String setDepartureDate(LocalDate selectedDate, String depTime){
        String[] departureTimeSplit = depTime.split(":");
        int hours = Integer.parseInt(departureTimeSplit[0]);
        int minutes = Integer.parseInt(departureTimeSplit[1]);
        int seconds = Integer.parseInt(departureTimeSplit[2]);
        LocalTime departureTime= LocalTime.of(hours, minutes,seconds);
        String month= String.valueOf(selectedDate.getMonth());

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

        return selectedDate.getDayOfMonth()+" "+month+", "+departureTime;
    }

    public String setFlightTime(String depTime, String arrTime){
        String[] departureTimeSplit = depTime.split(":");
        String[] arrivalTimeSplit = arrTime.split(":");
        int hours = Integer.parseInt(departureTimeSplit[0]);
        int minutes = Integer.parseInt(departureTimeSplit[1]);
        int seconds = Integer.parseInt(departureTimeSplit[2]);
        LocalTime departureTime= LocalTime.of(hours, minutes,seconds);
        hours = Integer.parseInt(arrivalTimeSplit[0]);
        minutes = Integer.parseInt(arrivalTimeSplit[1]);
        seconds = Integer.parseInt(arrivalTimeSplit[2]);
        LocalTime arrivalTime= LocalTime.of(hours, minutes,seconds);
        hours = (int) (departureTime.until(arrivalTime, ChronoUnit.MINUTES) / 60);
        minutes = (int) (departureTime.until(arrivalTime, ChronoUnit.MINUTES)-60*hours);
        String hoursString = String.valueOf(hours);
        String minutesString = String.valueOf(minutes);

        return hoursString+"h "+minutesString+"min";
    }

    public String setTicketPrice(){
        Random r = new Random();
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double random = 50 + (200 - 50) * r.nextDouble();
        return formatter.format(random)+" zł";
    }


    @Override
    public void onItemClick(int position) {
        goToNextActivity_ImageButton.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SearchResultsActivity.this,SeatingChoiceActivity.class);
        intent.putExtra("departureAirportCode",foundAirlineTicketsModels.get(position).getDepartureAirportCode());
        intent.putExtra("departureAirportCityName",foundAirlineTicketsModels.get(position).getDepartureAirportCityName());
        intent.putExtra("arrivalAirportCode",foundAirlineTicketsModels.get(position).getArrivalAirportCode());
        intent.putExtra("arrivalAirportCityName",foundAirlineTicketsModels.get(position).getArrivalAirportCityName());
        intent.putExtra("flightDuration",foundAirlineTicketsModels.get(position).getFlightDuration());
        intent.putExtra("departureDateAndTime",foundAirlineTicketsModels.get(position).getDepartureDateAndTime());
        intent.putExtra("flightNumber",foundAirlineTicketsModels.get(position).getFlightNumber());
        goToNextActivity_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
}
