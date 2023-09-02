package com.example.raionhackjamkel5.upload;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.homepage.HomePageActivity;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.example.raionhackjamkel5.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class UploadPageActivity extends AppCompatActivity {

    private static final int galleryCode = 1;
    private EditText et_NamaProduk, et_HargaBeli, et_HargaJual, et_LokasiProduk, et_DeskripsiProduk;
    private ImageButton btn_Back;
    private Spinner sKategoriProduk;
    private ShapeableImageView img_Produk;
    private AppCompatButton btn_Simpan;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private Uri imageUri = null;
    private FirebaseStorage mStorage;

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
        btn_Back = findViewById(R.id.btnBack);
        btn_Simpan = findViewById(R.id.btnSimpanProduk);

        et_NamaProduk.addTextChangedListener(textWatcher);
        et_HargaBeli.addTextChangedListener(textWatcher);
        et_HargaJual.addTextChangedListener(textWatcher);
        et_LokasiProduk.addTextChangedListener(textWatcher);
        et_DeskripsiProduk.addTextChangedListener(textWatcher);

        mStorage = FirebaseStorage.getInstance();

        btn_Back.setOnClickListener(v -> {
            Intent backUploadFragment = new Intent(UploadPageActivity.this, HomePageActivity.class);
            startActivity(backUploadFragment);
            finish();
        });

        img_Produk.setOnClickListener(v -> {
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

        btn_Simpan.setOnClickListener(v -> {
            String getNamaProduk = et_NamaProduk.getText().toString();
            String getHargaBeli = et_HargaBeli.getText().toString();
            String getHargaJual = et_HargaJual.getText().toString();
            String getLokasiProduk = et_LokasiProduk.getText().toString();
            String getDeskripsiProduk = et_DeskripsiProduk.getText().toString();
            String getKategoriProduk = sKategoriProduk.getSelectedItem().toString();

            if (getNamaProduk.isEmpty()) {
                et_NamaProduk.setError("Nama produk harus diisi");
            } else if (getHargaBeli.isEmpty()) {
                et_HargaBeli.setError("Harga beli harus diisi");
            } else if (getHargaJual.isEmpty()) {
                et_HargaJual.setError("Harga jual harus diisi");
            } else if (getLokasiProduk.isEmpty()) {
                et_LokasiProduk.setError("Lokasi produk harus diisi");
            } else if (getDeskripsiProduk.isEmpty()) {
                et_DeskripsiProduk.setError("Deskripsi produk harus diisi");
            } else if (getKategoriProduk.isEmpty()) {
                TextView errorText = (TextView) sKategoriProduk.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                errorText.setText("Kategori produk harus dipilih");
            } else {
                final String[] getNamaPenjual = new String[1];
                final String[] getNoWhatsapp = new String[1];
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                database.child("Users").child(userId).child("UserData").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            userModel.setKey(snapshot.getKey());
                            getNamaPenjual[0] = userModel.getNama().toString();
                            getNoWhatsapp[0] = userModel.getWhatsapp().toString();

                            StorageReference filePath = mStorage.getReference().child("imageProduk").child(imageUri.getLastPathSegment());
                            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    img_Produk.setBackground(null);
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String getFotoProduk = task.getResult().toString();
                                            String produkKey = database.child("Users").child(userId).child("ProdukSaya").push().getKey();

                                            database.child("Users").child(userId).child("ProdukSaya").child(produkKey).setValue(new KatalogModel(
                                                    getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                            )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    database.child("SemuaProduk").child(produkKey).setValue(new KatalogModel(
                                                            getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                                    ));

                                                    database.child("Kategori").child(getKategoriProduk).child(produkKey).setValue(new KatalogModel(
                                                            getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                                    ));
                                                }
                                            });
                                            Toast.makeText(UploadPageActivity.this, "Data produk berhasil ditambahkan ke aplikasi", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Toast.makeText(UploadPageActivity.this, "Seluruh data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UploadPageActivity.this, HomePageActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadPageActivity.this, "Harap mengisi seluruh data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nama = et_NamaProduk.getText().toString().trim();
            String hargaBeli = et_HargaBeli.getText().toString().trim();
            String hargaJual = et_HargaJual.getText().toString().trim();
            String lokasi = et_LokasiProduk.getText().toString().trim();
            String deskripsi = et_DeskripsiProduk.getText().toString().trim();
            boolean isFormValid = !nama.isEmpty() && !hargaBeli.isEmpty() && !hargaJual.isEmpty() && !lokasi.isEmpty() && !deskripsi.isEmpty();
            btn_Simpan.setEnabled(isFormValid);

            if (isFormValid) {
                btn_Simpan.setBackgroundResource(R.drawable.btn_primary_bg);
                btn_Simpan.setTextColor(getResources().getColor(R.color.white));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}