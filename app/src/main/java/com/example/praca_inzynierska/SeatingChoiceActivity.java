package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class SeatingChoiceActivity extends AppCompatActivity {
     private TextView showNumberOfSeats;
    private ImageButton A1, A2, A3, A4,
            B1, B2, B3, B4,
            C1, C2, C3, C4,
            D1, D2, D3, D4,
            E1, E2, E3, E4,
            F1, F2, F3, F4;
    private  String previousSelectedSeat="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_seating_choice);

        showNumberOfSeats = findViewById(R.id.seatNumber_TextView);
        A1 = findViewById(R.id.seatA1_ImageButton);
        A2 = findViewById(R.id.seatA2_ImageButton);
        A3 = findViewById(R.id.seatA3_ImageButton);
        A4 = findViewById(R.id.seatA4_ImageButton);
        B1 = findViewById(R.id.seatB1_ImageButton);
        B2 = findViewById(R.id.seatB2_ImageButton);
        B3 = findViewById(R.id.seatB3_ImageButton);
        B4 = findViewById(R.id.seatB4_ImageButton);
        C1 = findViewById(R.id.seatC1_ImageButton);
        C2 = findViewById(R.id.seatC2_ImageButton);
        C3 = findViewById(R.id.seatC3_ImageButton);
        C4 = findViewById(R.id.seatC4_ImageButton);
        D1 = findViewById(R.id.seatD1_ImageButton);
        D2 = findViewById(R.id.seatD2_ImageButton);
        D3 = findViewById(R.id.seatD3_ImageButton);
        D4 = findViewById(R.id.seatD4_ImageButton);
        E1 = findViewById(R.id.seatE1_ImageButton);
        E2 = findViewById(R.id.seatE2_ImageButton);
        E3 = findViewById(R.id.seatE3_ImageButton);
        E4 = findViewById(R.id.seatE4_ImageButton);
        F1 = findViewById(R.id.seatF1_ImageButton);
        F2 = findViewById(R.id.seatF2_ImageButton);
        F3 = findViewById(R.id.seatF3_ImageButton);
        F4 = findViewById(R.id.seatF4_ImageButton);

        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("A1");
                A1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "A1";
            }
        });
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("A2");
                A2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "A2";
            }
        });
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("A3");
                A3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "A3";
            }
        });
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("A4");
                A4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "A4";
            }
        });

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("B1");
                B1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "B1";
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("B2");
                B2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "B2";
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("B3");
                B3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "B3";
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("B4");
                B4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "B4";
            }
        });

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("C1");
                C1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "C1";
            }
        });
        C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("C2");
                C2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "C2";
            }
        });
        C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("C3");
                C3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "C3";
            }
        });
        C4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("C4");
                C4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "C4";
            }
        });

        D1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("D1");
                D1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "D1";
            }
        });
        D2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("D2");
                D2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "D2";
            }
        });
        D3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("D3");
                D3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "D3";
            }
        });
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("D4");
                D4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "D4";
            }
        });

        E1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("E1");
                E1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "E1";
            }
        });
        E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("E2");
                E2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "E2";
            }
        });
        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("E3");
                E3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "E3";
            }
        });
        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("E4");
                E4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "E4";
            }
        });

        F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("F1");
                F1.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "F1";
            }
        });
        F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("F2");
                F2.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "F2";
            }
        });
        F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("F3");
                F3.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "F3";
            }
        });
        F4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectedPreviousSeat(previousSelectedSeat);
                showNumberOfSeats.setText("F4");
                F4.setBackgroundResource(R.drawable.bg_seat_selected);
                previousSelectedSeat = "F4";
            }
        });
    }
    private void unselectedPreviousSeat(String previous_seat) {
        switch (previous_seat) {
            case "A1":
                A1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "A2":
                A2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "A3":
                A3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "A4":
                A4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "B1":
                B1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "B2":
                B2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "B3":
                B3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "B4":
                B4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "C1":
                C1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "C2":
                C2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "C3":
                C3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "C4":
                C4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "D1":
                D1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "D2":
                D2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "D3":
                D3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "D4":
                D4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "E1":
                E1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "E2":
                E2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "E3":
                E3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "E4":
                E4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "F1":
                F1.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "F2":
                F2.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "F3":
                F3.setBackgroundResource(R.drawable.bg_seat_free);
                break;
            case "F4":
                F4.setBackgroundResource(R.drawable.bg_seat_free);
                break;
        }
    }
}