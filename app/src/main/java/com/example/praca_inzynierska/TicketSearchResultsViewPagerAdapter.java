package com.example.praca_inzynierska;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.praca_inzynierska.TicketSearchResults.BestTicketSearchResultsFragment;
import com.example.praca_inzynierska.TicketSearchResults.CheapestTicketSearchResultsFragment;
import com.example.praca_inzynierska.TicketSearchResults.FastestTicketSearchResultsFragment;


public class TicketSearchResultsViewPagerAdapter extends FragmentStateAdapter {
    public TicketSearchResultsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new FastestTicketSearchResultsFragment();
            case 2:
                return new BestTicketSearchResultsFragment();
            default:
                return new CheapestTicketSearchResultsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
