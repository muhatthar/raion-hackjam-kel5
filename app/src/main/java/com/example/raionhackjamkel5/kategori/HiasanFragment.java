package com.example.raionhackjamkel5.kategori;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.adapter.KatalogAdapter;
import com.example.raionhackjamkel5.kategoriAdapter.DapurAdapter;
import com.example.raionhackjamkel5.kategoriAdapter.ElektronikAdapter;
import com.example.raionhackjamkel5.kategoriAdapter.HiasanAdapter;
import com.example.raionhackjamkel5.model.HiasanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HiasanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HiasanFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HiasanModel> hiasanItems;
    HiasanAdapter hiasanAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HiasanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DapurFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HiasanFragment newInstance(String param1, String param2) {
        HiasanFragment fragment = new HiasanFragment();
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
        view = inflater.inflate(R.layout.fragment_hiasan, container, false);

        recyclerView = view.findViewById(R.id.rvHiasanKategori);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        showHiasanData();
        return view;
    }

    private void showHiasanData(){
        database.child("Kategori").child("Hiasan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hiasanItems = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    HiasanModel katalog = item.getValue(HiasanModel.class);
                    katalog.setKey(item.getKey());
                    hiasanItems.add(katalog);
                }
                Collections.reverse(hiasanItems);
                hiasanAdapter = new HiasanAdapter(hiasanItems, getContext());
                recyclerView.setAdapter(hiasanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hiasanItems = new ArrayList<>();
            }
        });
    }
}