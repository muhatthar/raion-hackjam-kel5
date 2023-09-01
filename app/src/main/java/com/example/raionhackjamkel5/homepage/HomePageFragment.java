package com.example.raionhackjamkel5.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.adapter.SectionPagerAdapter;
import com.example.raionhackjamkel5.kategori.DapurFragment;
import com.example.raionhackjamkel5.kategori.ElektronikFragment;
import com.example.raionhackjamkel5.kategori.HiasanFragment;
import com.example.raionhackjamkel5.kategori.KamarFragment;
import com.example.raionhackjamkel5.kategori.PerabotFragment;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    View view;
    ImageButton btnAllKatalog, btnDapurKatalog, btnPerabotKatalog, btnKamarKatalog, btnElektronikKatalog, btnHiasanKatalog, btnSearch;
    TextView tvAllKatalog, tvDapurKatalog, tvPerabotKatalog, tvKamarKatalog, tvElektronikKatalog, tvHiasanKatalog;
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

        btnAllKatalog = view.findViewById(R.id.btn_allKatalog);
        btnDapurKatalog = view.findViewById(R.id.btn_DapurKatalog);
        btnPerabotKatalog = view.findViewById(R.id.btn_PerabotKatalog);
        btnKamarKatalog = view.findViewById(R.id.btn_KamarKatalog);
        btnElektronikKatalog = view.findViewById(R.id.btn_ElektronikKatalog);
        btnHiasanKatalog = view.findViewById(R.id.btn_HiasanKatalog);
        tvAllKatalog = view.findViewById(R.id.tv_AllKatalog);
        tvDapurKatalog = view.findViewById(R.id.tv_DapurKatalog);
        tvPerabotKatalog = view.findViewById(R.id.tv_PerabotKatalog);
        tvHiasanKatalog = view.findViewById(R.id.tv_HiasanKatalog);
        tvKamarKatalog = view.findViewById(R.id.tv_KamarKatalog);
        tvElektronikKatalog = view.findViewById(R.id.tv_ElektronikKatalog);
        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            Intent search = new Intent(getContext(), HomeSearchActivity.class);
            startActivity(search);
        });

        btnAllKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular_solid);
                tvAllKatalog.setTextColor(Color.parseColor("#3C8918"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot);
                tvPerabotKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur);
                tvDapurKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan);
                tvHiasanKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik);
                tvElektronikKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar);
                tvKamarKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                replaceFragment(new KatalogFragment());
            }
        });

        btnDapurKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular);
                tvAllKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot);
                tvPerabotKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur_solid);
                tvDapurKatalog.setTextColor(Color.parseColor("#3C8918"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan);
                tvHiasanKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik);
                tvElektronikKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar);
                tvKamarKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                replaceFragment(new DapurFragment());
            }
        });

        btnPerabotKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular);
                tvAllKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot_solid);
                tvPerabotKatalog.setTextColor(Color.parseColor("#3C8918"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur);
                tvDapurKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan);
                tvHiasanKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik);
                tvElektronikKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar);
                tvKamarKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                replaceFragment(new PerabotFragment());
            }
        });

        btnKamarKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular);
                tvAllKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot);
                tvPerabotKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur);
                tvDapurKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan);
                tvHiasanKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik);
                tvElektronikKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar_solid);
                tvKamarKatalog.setTextColor(Color.parseColor("#3C8918"));

                replaceFragment(new KamarFragment());
            }
        });

        btnElektronikKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular);
                tvAllKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot);
                tvPerabotKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur);
                tvDapurKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan);
                tvHiasanKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik_solid);
                tvElektronikKatalog.setTextColor(Color.parseColor("#3C8918"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar);
                tvKamarKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                replaceFragment(new ElektronikFragment());
            }
        });

        btnHiasanKatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllKatalog.setImageResource(R.drawable.ic_popular);
                tvAllKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnPerabotKatalog.setImageResource(R.drawable.ic_perabot);
                tvPerabotKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnDapurKatalog.setImageResource(R.drawable.ic_dapur);
                tvDapurKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnHiasanKatalog.setImageResource(R.drawable.ic_hiasan_solid);
                tvHiasanKatalog.setTextColor(Color.parseColor("#3C8918"));

                btnElektronikKatalog.setImageResource(R.drawable.ic_elektronik);
                tvElektronikKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                btnKamarKatalog.setImageResource(R.drawable.ic_kamar);
                tvKamarKatalog.setTextColor(Color.parseColor("#A6A1A1"));

                replaceFragment(new HiasanFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameKatalog, fragment);
        fragmentTransaction.commit();
    }
}