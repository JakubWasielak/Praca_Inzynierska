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
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchResultsActivity extends AppCompatActivity {
    RecyclerView Weekly_calendar, Flights_found;
    String[] Departure_airport_shortcut;
    String[] Departure_city;
    String[] Arrival_airport_shortcut;
    String[] Arrival_city;
    String[] Duration_of_flight;
    String[] Flight_date;
    String[] Flight_time;
    String[] Flight_number;
    LocalDate selectedDate = LocalDate.of(2022,8,03);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);

        //Przykładowe dane do czasu przejscia na baze danych
// ------------------------------------------------------------------------------------------------------
        Weekly_calendar = findViewById(R.id.weeklyCalendar_RecyclerView);
        ArrayList<String> dayNumberView = numberDayToView(selectedDate);
        ArrayList<String> dayNameView = nameDayToView(selectedDate);

        Flights_found = findViewById(R.id.foundFlights_RecyclerView);
        Departure_airport_shortcut = getResources().getStringArray(R.array.Departure_airport_shortcut);
        Departure_city = getResources().getStringArray(R.array.Departure_city);
        Arrival_airport_shortcut = getResources().getStringArray(R.array.Arrival_airport_shortcut);
        Arrival_city = getResources().getStringArray(R.array.Arrival_city);
        Duration_of_flight = getResources().getStringArray(R.array.Duration_of_flight);
        Flight_date  = getResources().getStringArray(R.array.Flight_date);
        Flight_time = getResources().getStringArray(R.array. Flight_time);
        Flight_number = getResources().getStringArray(R.array.Flight_number);
//------------------------------------------------------------------------------------------------------

        ShowWeeklyCalendarAdapter search1 = new ShowWeeklyCalendarAdapter(this,dayNameView,dayNumberView);
        Weekly_calendar.setAdapter(search1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Weekly_calendar.setLayoutManager(layoutManager);

        ShowUserFlightsAdapter user1 = new ShowUserFlightsAdapter(this, Departure_airport_shortcut, Departure_city, Arrival_airport_shortcut,
                Arrival_city, Duration_of_flight, Flight_date, Flight_time, Flight_number);
        Flights_found.setAdapter(user1);
        Flights_found.setLayoutManager(new LinearLayoutManager(this));

    }

    private static ArrayList<String> numberDayToView(LocalDate selecteddate)
    {
        ArrayList<String> NumberOfdays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);

        if(!selecteddate.equals(today))
        {
            if(daysBetween<7)
            {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++)
                {

                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            }
            else {
                int j =6;
                for(int i=0;i<7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.minusDays(j).getDayOfMonth()));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
                }
            }
        }
        else
        {
            for(int i=0;i<=14;i++)
            {
                NumberOfdays.add(String.valueOf(selecteddate.plusDays(i).getDayOfMonth()));
            }
        }

        return NumberOfdays;
    }
    private static ArrayList<String> nameDayToView(LocalDate selecteddate)
    {
        ArrayList<String> NameOfdays = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int daysBetween = (int) DAYS.between(today, selecteddate);

        if(!selecteddate.equals(today))
        {
            if(daysBetween<7)
            {
                int j =daysBetween-1;
                for(int i=0;i<daysBetween;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            }
            else {
                int j =6;
                for(int i=0;i<7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(j)));
                    j--;
                }
                for(int i=1;i<=7;i++)
                {
                    NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
                }
            }
        }
        else
        {
            for(int i=0;i<=14;i++)
            {
                NameOfdays.add(nameDayOfWeek(selecteddate.minusDays(i)));
            }
        }

        return NameOfdays;
    }
    private static String nameDayOfWeek(LocalDate selecteddate)
    {
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

    public void backToSearchingForTicketAcitivity(View view) {
        Intent intent = new Intent(this, SearchingForTicketActivity.class);
        startActivity(intent);
    }

    public void goToSeatingChoiceAcitivity(View view) {
        Intent intent = new Intent(this, SeatingChoiceActivity.class);
        startActivity(intent);
    }
}
