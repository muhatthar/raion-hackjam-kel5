package com.example.raionhackjamkel5.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.profil.ProfilePageFragment;
import com.example.raionhackjamkel5.upload.UploadPageActivity;
import com.example.raionhackjamkel5.upload.UploadPageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePageActivity extends AppCompatActivity {

    private int selectedTab = 1;
    private LinearLayout navBarLayout;
    private int selectedTextColor;
    FloatingActionButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final LinearLayout homePageLayout = findViewById(R.id.homePageLayout);
        final LinearLayout produkPageLayout = findViewById(R.id.produkPageLayout);
        final LinearLayout profilePageLayout = findViewById(R.id.profilePageLayout);

        final ImageView homePageImage = findViewById(R.id.ivHomePage);
        final ImageView profilePageImage = findViewById(R.id.ivProfilePage);
        final ImageView produkPageImage = findViewById(R.id.ivProdukPage);

        final TextView homePageText = findViewById(R.id.tvHomePage);
        final TextView produkPageText = findViewById(R.id.tvProdukPage);
        final TextView profilePageText = findViewById(R.id.tvProfilePage);

        final int selectedTabAfter = getIntent().getIntExtra("selectedTab", 1);

        navBarLayout = findViewById(R.id.navbarLayout);

        selectedTextColor = homePageText.getCurrentTextColor();

        if (selectedTabAfter == 1) {
            replaceFragment(new HomePageFragment());
            produkPageImage.setImageResource(R.drawable.ic_produk);
            profilePageImage.setImageResource(R.drawable.ic_profile);

            produkPageText.setTextColor(Color.parseColor("#A6A1A1"));
            profilePageText.setTextColor(Color.parseColor("#A6A1A1"));

            homePageText.setTextColor(selectedTextColor);
            homePageImage.setImageResource(R.drawable.ic_home_solid);
            selectedTab = 1;
        } else if (selectedTabAfter == 2) {
            replaceFragment(new UploadPageFragment());
            homePageImage.setImageResource(R.drawable.ic_home);
            profilePageImage.setImageResource(R.drawable.ic_profile);

            homePageText.setTextColor(Color.parseColor("#A6A1A1"));
            profilePageText.setTextColor(Color.parseColor("#A6A1A1"));

            produkPageText.setTextColor(selectedTextColor);
            produkPageImage.setImageResource(R.drawable.ic_produk_solid);
            selectedTab = 2;
        } else if (selectedTabAfter == 3) {
            replaceFragment(new ProfilePageFragment());
            produkPageImage.setImageResource(R.drawable.ic_produk);
            homePageImage.setImageResource(R.drawable.ic_home);

            produkPageText.setTextColor(Color.parseColor("#A6A1A1"));
            homePageText.setTextColor(Color.parseColor("#A6A1A1"));

            profilePageText.setTextColor(selectedTextColor);
            profilePageImage.setImageResource(R.drawable.ic_profile_solid);
            selectedTab = 3;
        }

        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(v -> {
            Intent tambahProduk = new Intent(this, UploadPageActivity.class);
            startActivity(tambahProduk);
        });

        homePageLayout.setOnClickListener(v -> {
            if (selectedTab != 1){
                btn_add.setVisibility(View.GONE);

                produkPageImage.setImageResource(R.drawable.ic_produk);
                profilePageImage.setImageResource(R.drawable.ic_profile);

                produkPageText.setTextColor(Color.parseColor("#A6A1A1"));
                profilePageText.setTextColor(Color.parseColor("#A6A1A1"));

                homePageText.setTextColor(selectedTextColor);
                homePageImage.setImageResource(R.drawable.ic_home_solid);

                replaceFragment(new HomePageFragment());

                selectedTab = 1;
            }
        });

        produkPageLayout.setOnClickListener(v -> {
            if (selectedTab != 2){
                btn_add.setVisibility(View.VISIBLE);

                homePageImage.setImageResource(R.drawable.ic_home);
                profilePageImage.setImageResource(R.drawable.ic_profile);

                homePageText.setTextColor(Color.parseColor("#A6A1A1"));
                profilePageText.setTextColor(Color.parseColor("#A6A1A1"));

                produkPageText.setTextColor(selectedTextColor);
                produkPageImage.setImageResource(R.drawable.ic_produk_solid);

                replaceFragment(new UploadPageFragment());

                selectedTab = 2;
            }
        });

        profilePageLayout.setOnClickListener(v -> {
            if (selectedTab != 3){
                btn_add.setVisibility(View.GONE);

                produkPageImage.setImageResource(R.drawable.ic_produk);
                homePageImage.setImageResource(R.drawable.ic_home);

                produkPageText.setTextColor(Color.parseColor("#A6A1A1"));
                homePageText.setTextColor(Color.parseColor("#A6A1A1"));

                profilePageText.setTextColor(selectedTextColor);
                profilePageImage.setImageResource(R.drawable.ic_profile_solid);

                replaceFragment(new ProfilePageFragment());

                selectedTab = 3;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}