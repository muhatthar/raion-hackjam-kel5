package com.example.raionhackjamkel5.profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.adapter.BookmarkAdapter;
import com.example.raionhackjamkel5.homepage.HomePageActivity;
import com.example.raionhackjamkel5.model.BookmarkModel;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookmarkPageActivity extends AppCompatActivity {

    private ImageButton btnBack;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    RecyclerView.LayoutManager layoutManager;
    ArrayList<KatalogModel> bookmarkItems;
    BookmarkAdapter bookmarkAdapter;
    private RecyclerView recyclerView;
    private List<KatalogModel> filterKatalog;
    SearchView svBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_page);

        btnBack = findViewById(R.id.btnBackBookmark);

        recyclerView = findViewById(R.id.rvBookmark);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        svBookmark = findViewById(R.id.svBookmark);

        btnBack.setOnClickListener(v -> {
            Intent backProfil = new Intent(BookmarkPageActivity.this, HomePageActivity.class);
            startActivity(backProfil);
            finish();
        });

        svBookmark.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBookmarkPage(newText);
                return true;
            }
        });

        showData();
    }

    private void filterBookmarkPage(String filterText) {
        filterKatalog.clear();

        if (filterText.isEmpty()) {
            filterKatalog.addAll(bookmarkItems);
        } else {
            String filterPattern = filterText.toLowerCase().trim();

            for (KatalogModel katalogModel : bookmarkItems){
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

        bookmarkAdapter.filterList(filterKatalog);
    }

    private void showData(){
        filterKatalog = new ArrayList<>();

        if (bookmarkItems != null){
            filterKatalog.addAll(bookmarkItems);
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.child("Users").child(userId).child("Bookmark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookmarkItems = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()){
                    KatalogModel produk = item.getValue(KatalogModel.class);
                    produk.setKey(item.getKey());
                    bookmarkItems.add(produk);
                }
                Collections.reverse(bookmarkItems);
                bookmarkAdapter = new BookmarkAdapter(bookmarkItems, getApplicationContext());
                recyclerView.setAdapter(bookmarkAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}