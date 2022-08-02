package com.example.praca_inzynierska;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.praca_inzynierska.typeOfTravelFragments.OneWayFlightFragment;
import com.example.praca_inzynierska.typeOfTravelFragments.RoundTripFlightFragment;

public class typeOfTravelViewPagerAdapter extends FragmentStateAdapter {
    public typeOfTravelViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new OneWayFlightFragment();
            case 1:
                return new RoundTripFlightFragment();
            default:
                return new OneWayFlightFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
