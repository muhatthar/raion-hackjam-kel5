package com.example.raionhackjamkel5.upload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.raionhackjamkel5.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class UploadPageActivity extends AppCompatActivity {

    private static final int galleryCode = 1;
    EditText et_NamaProduk, et_HargaBeli, et_HargaJual, et_LokasiProduk, et_DeskripsiProduk;
    Spinner sKategoriProduk;
    ImageButton img_Produk;
    AppCompatButton btn_Simpan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    Uri imageUri = null;
    FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);

        et_NamaProduk = findViewById(R.id.etNamaProduk);
        et_HargaBeli = findViewById(R.id.etHargaBeli);
        et_HargaJual = findViewById(R.id.etHargaJual);
        et_LokasiProduk = findViewById(R.id.etLokasiProduk);
        et_DeskripsiProduk = findViewById(R.id.etDeskripsiProduk);
        sKategoriProduk = findViewById(R.id.sKategoriProduk);
        img_Produk = findViewById(R.id.imgProduk);
        btn_Simpan = findViewById(R.id.btnSimpanProduk);

        mStorage = FirebaseStorage.getInstance();

        btn_Simpan.setOnClickListener(v -> {
            Intent addImg = new Intent(Intent.ACTION_GET_CONTENT);
            addImg.setType("image/*");
            startActivityForResult(addImg, galleryCode);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryCode && resultCode == RESULT_OK) {
            imageUri = data.getData();
            img_Produk.setImageURI(imageUri);
        }
    }
}