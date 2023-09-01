package com.example.raionhackjamkel5.upload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

public class EditActivity extends AppCompatActivity {

    ShapeableImageView imgProdukEdit;
    ImageButton btn_Back;
    EditText etNamaProdukEdit, etHargaBeliEdit, etHargaJualEdit, etLokasiProdukEdit, etDeskripsiEdit;
    Spinner sKategoriProdukEdit;
    AppCompatButton btnSimpanProdukEdit;
    Uri imageUri = null;
    FirebaseStorage mStorage;
    private static final int galleryCode = 1;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btn_Back = findViewById(R.id.btnBack);
        imgProdukEdit = findViewById(R.id.imgProdukEdit);
        etNamaProdukEdit = findViewById(R.id.etNamaProdukEdit);
        etHargaBeliEdit = findViewById(R.id.etHargaBeliEdit);
        etHargaJualEdit = findViewById(R.id.etHargaJualEdit);
        etLokasiProdukEdit = findViewById(R.id.etLokasiProdukEdit);
        etDeskripsiEdit = findViewById(R.id.etDeskripsiProdukEdit);
        sKategoriProdukEdit = findViewById(R.id.sKategoriProdukEdit);
        btnSimpanProdukEdit = findViewById(R.id.btnSimpanProdukEdit);

        mStorage = FirebaseStorage.getInstance();

        Intent getData = getIntent();
        String namaProduk = getData.getStringExtra("nama");
        String hargaBeli = getData.getStringExtra("hargaBeli");
        String hargaJual = getData.getStringExtra("hargaJual");
        String lokasi = getData.getStringExtra("lokasi");
        String deskripsi = getData.getStringExtra("deskripsi");
        String fotoProduk = getData.getStringExtra("fotoProduk");
        Picasso.get().load(fotoProduk).into(imgProdukEdit);

        etNamaProdukEdit.setText(namaProduk);
        etHargaBeliEdit.setText(hargaBeli);
        etHargaJualEdit.setText(hargaJual);
        etLokasiProdukEdit.setText(lokasi);
        etDeskripsiEdit.setText(deskripsi);

        imgProdukEdit.setOnClickListener(v -> {
            Intent addImg = new Intent(Intent.ACTION_GET_CONTENT);
            addImg.setType("image/*");
            startActivityForResult(addImg, galleryCode);

            if (fotoProduk != null) {
                StorageReference storageRef = mStorage.getReferenceFromUrl(fotoProduk);

                storageRef.delete();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryCode && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imgProdukEdit.setImageURI(imageUri);
        }

        Intent getDataKey = getIntent();
        String key = getDataKey.getStringExtra("key");

        btnSimpanProdukEdit.setOnClickListener(v -> {
            String getNamaProduk = etNamaProdukEdit.getText().toString();
            String getHargaBeli = etHargaBeliEdit.getText().toString();
            String getHargaJual = etHargaJualEdit.getText().toString();
            String getLokasiProduk = etLokasiProdukEdit.getText().toString();
            String getDeskripsiProduk = etDeskripsiEdit.getText().toString();
            String getKategoriProduk = sKategoriProdukEdit.getSelectedItem().toString();

            if (getNamaProduk.isEmpty()) {
                etNamaProdukEdit.setError("Nama produk harus diisi");
            } else if (getHargaBeli.isEmpty()) {
                etHargaBeliEdit.setError("Harga beli harus diisi");
            } else if (getHargaJual.isEmpty()) {
                etHargaJualEdit.setError("Harga jual harus diisi");
            } else if (getLokasiProduk.isEmpty()) {
                etLokasiProdukEdit.setError("Lokasi produk harus diisi");
            } else if (getDeskripsiProduk.isEmpty()) {
                etDeskripsiEdit.setError("Deskripsi produk harus diisi");
            } else if (getKategoriProduk.isEmpty()) {
                TextView errorText = (TextView) sKategoriProdukEdit.getSelectedView();
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
                                    imgProdukEdit.setBackground(null);
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String getFotoProduk = task.getResult().toString();

//                                            String produkKey = database.child("Users").child(userId).child("ProdukSaya").push().getKey();
                                            database.child("Users").child(userId).child("ProdukSaya").child(key).setValue(new KatalogModel(
                                                    getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                            )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    database.child("SemuaProduk").child(key).setValue(new KatalogModel(
                                                            getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                                    ));

                                                    database.child("Kategori").child(getKategoriProduk).child(key).setValue(new KatalogModel(
                                                            getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk
                                                    ));
                                                }
                                            });
                                            Toast.makeText(EditActivity.this, "Data produk berhasil ditambahkan ke aplikasi", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Toast.makeText(EditActivity.this, "Seluruh data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditActivity.this, HomePageActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditActivity.this, "Harap mengisi seluruh data", Toast.LENGTH_SHORT).show();
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
}