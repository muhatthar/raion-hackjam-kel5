package com.example.raionhackjamkel5.upload;

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
import com.example.raionhackjamkel5.adapter.ProdukSayaAdapter;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPageFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    RecyclerView.LayoutManager layoutManager;
    ArrayList<KatalogModel> produkItems;
    ProdukSayaAdapter produkSayaAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPageFragment newInstance(String param1, String param2) {
        UploadPageFragment fragment = new UploadPageFragment();
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
        view = inflater.inflate(R.layout.fragment_upload_page, container, false);

        recyclerView = view.findViewById(R.id.rvProdukSaya);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        showData();
        return view;
    }

    private void showData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.child("Users").child(userId).child("ProdukSaya").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produkItems = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    KatalogModel produk = item.getValue(KatalogModel.class);
                    produk.setKey(item.getKey());
                    produkItems.add(produk);
                }
                Collections.reverse(produkItems);
                produkSayaAdapter = new ProdukSayaAdapter(produkItems, getContext());
                recyclerView.setAdapter(produkSayaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
