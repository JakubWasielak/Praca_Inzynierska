package com.example.praca_inzynierska;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class AddingTicketActivity extends AppCompatActivity {

    //data picker
    AutoCompleteTextView mDateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TextView Bilet_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_adding_ticket);

        Bilet_tab = findViewById(R.id.Bilet_tab);
        Bilet_tab.setOnClickListener(view -> openSearchResultsActivity());

        ImageButton imagebutton = findViewById(R.id.back_btn);
        imagebutton.setOnClickListener(view -> backMainActivity());

        imagebutton = findViewById(R.id.go_to_search_results_btn);
        imagebutton.setOnClickListener(view -> openSearchResultsActivity());


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateFormat);
        mDateFormat.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddingTicketActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener, day ,month, year);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        onDateSetListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = dayOfMonth+"/"+ month1 +"/" + year1;
            mDateFormat.setText(date);
        };
        String[] items =  {"Ekonomiczna","Biznesowa","Pierwsza"};
        AutoCompleteTextView autoCompleteTxt;
        ArrayAdapter<String> adapterItems;


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<>(this, R.layout.drop_down_menu_list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
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