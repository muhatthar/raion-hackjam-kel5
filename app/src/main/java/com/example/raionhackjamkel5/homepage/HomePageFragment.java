package com.example.raionhackjamkel5.homepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raionhackjamkel5.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        viewPager = view.findViewById(R.id.vpKatalog);
        tabLayout = view.findViewById(R.id.tlKategori);

        for (int i = 0; i < 6; i++){
            TabLayout.Tab tab = tabLayout.newTab().setText(getTextResourceId(i));
            tab.setIcon(getIconResourceId(i));
            tabLayout.addTab(tab);
        }

        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.setIcon(getSelectedIconResourceId(0));
            firstTab.setText(getTextResourceId(0));
        }

        return view;
    }

    private int getIconResourceId(int position) {
        switch (position){
            case 0:
                return R.drawable.ic_popular;
            case 1:
                return R.drawable.ic_dapur;
            case 2:
                return R.drawable.ic_perabot;
            case 3:
                return R.drawable.ic_kamar;
            case 4:
                return R.drawable.ic_elektronik;
            case 5:
                return R.drawable.ic_hiasan;
            default:
                return R.drawable.ic_popular;
        }
    }

    private int getSelectedIconResourceId(int position) {
        switch (position){
            case 0:
                return R.drawable.ic_popular_solid;
            case 1:
                return R.drawable.ic_dapur_solid;
            case 2:
                return R.drawable.ic_perabot_solid;
            case 3:
                return R.drawable.ic_kamar_solid;
            case 4:
                return R.drawable.ic_elektronik_solid;
            case 5:
                return R.drawable.ic_hiasan_solid;
            default:
                return R.drawable.ic_popular_solid;
        }
    }

    private String getTextResourceId(int position) {
        switch (position){
            case 0:
                return "Popular";
            case 1:
                return "Dapur";
            case 2:
                return "Perabot";
            case 3:
                return "Kamar";
            case 4:
                return "Elektronik";
            case 5:
                return "Hiasan";
            default:
                return "Popular";
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(getSelectedIconResourceId(tab.getPosition()));
                tabLayout.getTabAt(tab.getPosition()).setIcon(getSelectedIconResourceId(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(getIconResourceId(tab.getPosition()));
                tabLayout.getTabAt(tab.getPosition()).setIcon(getIconResourceId(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}