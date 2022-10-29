package com.example.praca_inzynierska;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SearchResultsActivity extends AppCompatActivity {
    TabLayout mTicketSearchResults_TabLayout;
    ViewPager2 mTicketSearchResults_ViewPager2;
    TicketSearchResultsViewPagerAdapter searchResultsViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_results);

        ImageButton btnPreviousActivity = findViewById(R.id.goToPreviousActivity_ImageButton);
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initTicketSearchResultsView();

    }

    private void initTicketSearchResultsView() {
        mTicketSearchResults_TabLayout = findViewById(R.id.ticketSearchResults_TabLayout);
        mTicketSearchResults_ViewPager2 = findViewById(R.id.ticketSearchResults_ViewPager2);
        searchResultsViewPagerAdapter = new TicketSearchResultsViewPagerAdapter(this);
        mTicketSearchResults_ViewPager2.setAdapter(searchResultsViewPagerAdapter);

        mTicketSearchResults_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTicketSearchResults_ViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTicketSearchResults_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTicketSearchResults_TabLayout.getTabAt(position).select();
            }
        });
    }

}
