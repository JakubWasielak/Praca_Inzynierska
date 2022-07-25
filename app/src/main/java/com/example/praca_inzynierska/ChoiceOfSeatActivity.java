package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class ChoiceOfSeatActivity extends AppCompatActivity {
    TextView show_number_of_seats;
    ImageButton A1, A2, A3, A4,
            B1, B2, B3, B4,
            C1, C2, C3, C4,
            D1, D2, D3, D4,
            E1, E2, E3, E4,
            F1, F2, F3, F4;
    String previous_selected_seat= " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_choice_of_seat);

        show_number_of_seats = findViewById(R.id.Seat_number_TextView);
        A1=findViewById(R.id.Seat_A1); A2=findViewById(R.id.Seat_A2); A3=findViewById(R.id.Seat_A3); A4=findViewById(R.id.Seat_A4);
        B1=findViewById(R.id.Seat_B1); B2=findViewById(R.id.Seat_B2); B3=findViewById(R.id.Seat_B3); B4=findViewById(R.id.Seat_B4);
        C1=findViewById(R.id.Seat_C1); C2=findViewById(R.id.Seat_C2); C3=findViewById(R.id.Seat_C3); C4=findViewById(R.id.Seat_C4);
        D1=findViewById(R.id.Seat_D1); D2=findViewById(R.id.Seat_D2); D3=findViewById(R.id.Seat_D3); D4=findViewById(R.id.Seat_D4);
        E1=findViewById(R.id.Seat_E1); E2=findViewById(R.id.Seat_E2); E3=findViewById(R.id.Seat_E3); E4=findViewById(R.id.Seat_E4);
        F1=findViewById(R.id.Seat_F1); F2=findViewById(R.id.Seat_F2); F3=findViewById(R.id.Seat_F3); F4=findViewById(R.id.Seat_F4);

        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("A1");
                A1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "A1";

            }
        });
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("A2");
                A2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "A2";

            }
        });
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("A3");
                A3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "A3";
            }
        });
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("A4");
                A4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "A4";
            }
        });

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("B1");
                B1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "B1";

            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("B2");
                B2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "B2";

            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("B3");
                B3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "B3";
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("B4");
                B4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "B4";
            }
        });

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("C1");
                C1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "C1";

            }
        });
        C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("C2");
                C2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "C2";

            }
        });
        C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("C3");
                C3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "C3";
            }
        });
        C4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("C4");
                C4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "C4";
            }
        });

        D1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("D1");
                D1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "D1";

            }
        });
        D2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("D2");
                D2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "D2";

            }
        });
        D3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("D3");
                D3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "D3";
            }
        });
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("D4");
                D4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "D4";
            }
        });

        E1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("E1");
                E1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "E1";

            }
        });
        E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("E2");
                E2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "E2";

            }
        });
        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("E3");
                E3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "E3";
            }
        });
        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("E4");
                E4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "E4";
            }
        });

        F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("F1");
                F1.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "F1";

            }
        });
        F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("F2");
                F2.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "F2";

            }
        });
        F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("F3");
                F3.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "F3";
            }
        });
        F4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unselected_previous_seat(previous_selected_seat);
                show_number_of_seats.setText("F4");
                F4.setBackgroundResource(R.drawable.selected_seats_background);
                previous_selected_seat = "F4";
            }
        });

    }
    public void Unselected_previous_seat(String previous_seat) {
        switch (previous_seat) {
            case "A1":
                A1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "A2":
                A2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "A3":
                A3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "A4":
                A4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "B1":
                B1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "B2":
                B2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "B3":
                B3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "B4":
                B4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "C1":
                C1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "C2":
                C2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "C3":
                C3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "C4":
                C4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "D1":
                D1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "D2":
                D2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "D3":
                D3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "D4":
                D4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "E1":
                E1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "E2":
                E2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "E3":
                E3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "E4":
                E4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "F1":
                F1.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "F2":
                F2.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "F3":
                F3.setBackgroundResource(R.drawable.custom_seat_background);
                break;
            case "F4":
                F4.setBackgroundResource(R.drawable.custom_seat_background);
                break;
        }
    }

}