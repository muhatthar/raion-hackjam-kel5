package com.example.raionhackjamkel5.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageButton;

import com.example.raionhackjamkel5.R;

public class DetailProdukActivity extends AppCompatActivity {

    AppCompatButton btn_HubungiPenjual;
    ImageButton btn_BookmarkDetail;
    ViewPager vp_Detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        btn_BookmarkDetail = findViewById(R.id.btnBookmarkDetail);
        btn_HubungiPenjual = findViewById(R.id.btnHubungiPenjual);
        vp_Detail = findViewById(R.id.vpDetail);

        Intent intent = getIntent();

        DetailAdapter adapter = new DetailAdapter(this, intent);
        vp_Detail.setAdapter(adapter);
    }
}