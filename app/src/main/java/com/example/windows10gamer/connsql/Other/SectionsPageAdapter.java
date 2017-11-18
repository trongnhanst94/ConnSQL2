package com.example.windows10gamer.connsql.Other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EVRESTnhan on 11/8/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter{
    private final List<android.support.v4.app.Fragment> mFagment = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(android.support.v4.app.Fragment fragment, String title){
        mFagment.add(fragment);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFagment.get(position);
    }

    @Override
    public int getCount() {
        return mFagment.size();
    }
}
