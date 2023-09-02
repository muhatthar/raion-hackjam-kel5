package com.example.raionhackjamkel5.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.adapter.KatalogAdapter;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HomeSearchActivity extends AppCompatActivity {

    KatalogAdapter katalogAdapter;
    DatabaseReference database;
    ArrayList<KatalogModel> katalogModels;
    RecyclerView rv_KatalogSearch;
    ImageButton btn_Back;
    SearchView sv_HomeSearch;
    private List<KatalogModel> filterKatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        rv_KatalogSearch = findViewById(R.id.rvKatalogSearch);
        btn_Back = findViewById(R.id.btnPBackSearch);
        sv_HomeSearch = findViewById(R.id.svHomeSearch);
        database = FirebaseDatabase.getInstance().getReference();

        btn_Back.setOnClickListener(v -> {
            Intent backHomePage = new Intent(HomeSearchActivity.this, HomePageActivity.class);
            startActivity(backHomePage);
            finish();
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_KatalogSearch.setLayoutManager(layoutManager);

        showData();
        sv_HomeSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterHomePage(newText);
                return true;
            }
        });
    }

    private void showData() {
        filterKatalog = new ArrayList<>();

        if (katalogModels != null){
            filterKatalog.addAll(katalogModels);
        }

        database.child("SemuaProduk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                katalogModels = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()){
                    if (item.getValue() instanceof HashMap){
                        KatalogModel katalog = item.getValue(KatalogModel.class);
                        katalog.setKey(item.getKey());
                        katalogModels.add(katalog);
                    }
                }
                katalogAdapter = new KatalogAdapter(katalogModels, HomeSearchActivity.this);
                rv_KatalogSearch.setAdapter(katalogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterHomePage(String filterText) {
        filterKatalog.clear();

        if (filterText.isEmpty()) {
            filterKatalog.addAll(katalogModels);
        } else {
            String filterPattern = filterText.toLowerCase().trim();

            for (KatalogModel katalogModel : katalogModels){
                if (katalogModel.getNamaProduk().toLowerCase().contains(filterPattern)){
                    filterKatalog.add(katalogModel);
                } else if (katalogModel.getLokasiProduk().toLowerCase().contains(filterPattern)){
                    filterKatalog.add(katalogModel);
                } else if (katalogModel.getKategoriProduk().toLowerCase().contains(filterPattern)){
                    filterKatalog.add(katalogModel);
                } else if (katalogModel.getNamaPenjual().toLowerCase().contains(filterPattern)){
                    filterKatalog.add(katalogModel);
                }
            }
        }

        katalogAdapter.filterList(filterKatalog);
    }
}