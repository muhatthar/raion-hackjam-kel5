package com.example.raionhackjamkel5.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<Integer> selectedIconsList = new ArrayList<>();
    private List<Integer> unselectedIconsList = new ArrayList<>();

    public SectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public CharSequence getPageTitle (int position){
        return titleList.get(position);
    }

    public void addFragment(Fragment fragment, int selectedIcon, int unSelectedIcon, String title){
        fragmentList.add(fragment);
        selectedIconsList.add(selectedIcon);
        unselectedIconsList.add(unSelectedIcon);
        titleList.add(title);
    }
}
