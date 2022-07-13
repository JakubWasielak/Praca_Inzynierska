package com.example.praca_inzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddingTicketActivity extends AppCompatActivity {
    private ImageButton Imagebutton;

    //data picker
    AutoCompleteTextView mDateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TextView Bilet_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_adding_ticket);

        Bilet_tab = findViewById(R.id.Bilet_tab);
        Bilet_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchResultsActivity();
            }
        });

        Imagebutton = findViewById(R.id.back_btn);
        Imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backMainActivity();
            }
        });

        Imagebutton = findViewById(R.id.go_to_search_results_btn);
        Imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchResultsActivity();
            }
        });


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateFormat);
        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddingTicketActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, day ,month, year);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = dayOfMonth+"/"+month+"/" +year;
                mDateFormat.setText(date);
            }
        };
        String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
        AutoCompleteTextView autoCompleteTxt;
        ArrayAdapter<String> adapterItems;


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_down_menu_list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void backMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSearchResultsActivity() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }






}