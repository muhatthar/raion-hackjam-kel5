package com.example.raionhackjamkel5.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.homepage.HomePageActivity;

public class PengaturanAkunPage extends AppCompatActivity {

    ImageButton btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_akun_page);

        btn_Back = findViewById(R.id.btnBackPengaturan);

        btn_Back.setOnClickListener(v -> {
            Intent backProfil = new Intent(PengaturanAkunPage.this, HomePageActivity.class);
            startActivity(backProfil);
            finish();
        });
    }
}