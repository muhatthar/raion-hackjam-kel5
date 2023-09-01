package com.example.raionhackjamkel5.profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.adapter.BookmarkAdapter;
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

public class BookmarkPageActivity extends AppCompatActivity {

    private ImageButton btnBack;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    RecyclerView.LayoutManager layoutManager;
    ArrayList<KatalogModel> bookmarkItems;
    BookmarkAdapter bookmarkAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_page);

        btnBack = findViewById(R.id.btnBackBookmark);

        recyclerView = findViewById(R.id.rvBookmark);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        showData();
    }

    private void showData(){
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