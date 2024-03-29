package com.example.praca_inzynierska;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SearchingForTicketActivity extends AppCompatActivity {
    TabLayout mTypeOfTravel_TabLayout;
    ViewPager2 mTypeOfTravel_ViewPager2;
    com.example.praca_inzynierska.typeOfTravelViewPagerAdapter typeOfTravelViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_searching_for_ticket);
        initTypeOfTravelView();
        ImageButton btnCloseActivity = findViewById(R.id.closeActivity_ImageView);
        btnCloseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initTypeOfTravelView() {
        mTypeOfTravel_TabLayout = findViewById(R.id.typeOfTravel_TabLayout);
        mTypeOfTravel_ViewPager2 = findViewById(R.id.typeOfTravel_ViewPager2);
        typeOfTravelViewPagerAdapter = new typeOfTravelViewPagerAdapter(this);
        mTypeOfTravel_ViewPager2.setAdapter(typeOfTravelViewPagerAdapter);

        mTypeOfTravel_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTypeOfTravel_ViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTypeOfTravel_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTypeOfTravel_TabLayout.getTabAt(position).select();
            }
        });
    }
}