package com.example.raionhackjamkel5.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.detail.DetailProdukActivity;
import com.example.raionhackjamkel5.model.BookmarkModel;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.example.raionhackjamkel5.model.UserModel;
import com.example.raionhackjamkel5.profil.BookmarkPageActivity;
import com.example.raionhackjamkel5.upload.EditActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class KatalogAdapter extends RecyclerView.Adapter<KatalogAdapter.KatalogViewHolder> {

    private List<KatalogModel> katalogItems;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public KatalogAdapter(List<KatalogModel> katalogItems, Context context){
        this.katalogItems = katalogItems;
        this.context = context;
    }

    @NonNull
    @Override
    public KatalogAdapter.KatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View katalogView = inflater.inflate(R.layout.katalog_item, parent, false);
        return new KatalogViewHolder(katalogView);
    }

    @Override
    public void onBindViewHolder(@NonNull KatalogAdapter.KatalogViewHolder holder, int position) {
//        katalogItems = getItemAtPosition(position);
        KatalogModel katalogData = katalogItems.get(position);

        holder.tv_NamaProduk.setText(katalogData.getNamaProduk());
        holder.tv_HargaProduk.setText("Rp " + formatNumberCurrency(katalogData.getHargaJual()));
        holder.tv_KotaProduk.setText(katalogData.getLokasiProduk());
        Picasso.get().load(katalogData.getFotoProduk()).into(holder.iv_FotoProduk);

        Intent sendKey = new Intent(context, EditActivity.class);
        sendKey.putExtra("keyKatalog", katalogData.getKey());

        holder.btn_Bookmark.setOnClickListener(v -> {
            String getNamaProduk = katalogData.getNamaProduk();
            String getHargaBeli = katalogData.getHargaBeli();
            String getHargaJual = katalogData.getHargaJual();
            String getLokasiProduk = katalogData.getLokasiProduk();
            String getDeskripsiProduk = katalogData.getDeskripsiProduk();
            String getKategoriProduk = katalogData.getKategoriProduk();
            String getFotoProduk = katalogData.getFotoProduk();
            final String[] getNamaPenjual = new String[1];
            final String[] getNoWhatsapp = new String[1];

            holder.btn_Bookmark.setImageResource(R.drawable.ic_bookmark_click);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            database.child("Users").child(userId).child("UserData").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        userModel.setKey(snapshot.getKey());
                        getNamaPenjual[0] = userModel.getNama().toString();
                        getNoWhatsapp[0] = userModel.getWhatsapp().toString();

                        database.child("Users").child(userId).child("Bookmark").child(katalogData.getKey()).setValue(new KatalogModel(
                                getNamaProduk, getNamaPenjual[0], getNoWhatsapp[0], getHargaBeli, getHargaJual, getLokasiProduk, getDeskripsiProduk, getKategoriProduk, getFotoProduk)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Produk berhasil ditambahkan ke bookmark Anda", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Produk gagal ditambahkan ke bookmark Anda", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent bookmarkProduk = new Intent(context, BookmarkPageActivity.class);
            bookmarkProduk.putExtra("key", katalogData.getKey());
            bookmarkProduk.putExtra("nama", katalogData.getNamaProduk());
            bookmarkProduk.putExtra("hargaBeli", katalogData.getHargaBeli());
            bookmarkProduk.putExtra("hargaJual", katalogData.getHargaJual());
            bookmarkProduk.putExtra("kota", katalogData.getLokasiProduk());
            bookmarkProduk.putExtra("deskripsi", katalogData.getDeskripsiProduk());
            bookmarkProduk.putExtra("fotoProduk", katalogData.getFotoProduk());
            bookmarkProduk.putExtra("namaPenjual", katalogData.getNamaPenjual());
            bookmarkProduk.putExtra("whatsappPenjual", katalogData.getNoWhatsapp());
        });

        holder.iv_FotoProduk.setOnClickListener(v -> {
            Intent detailProduk = new Intent(context, DetailProdukActivity.class);
            detailProduk.putExtra("key", katalogData.getKey());
            detailProduk.putExtra("nama", katalogData.getNamaProduk());
            detailProduk.putExtra("hargaBeli", katalogData.getHargaBeli());
            detailProduk.putExtra("hargaJual", katalogData.getHargaJual());
            detailProduk.putExtra("kota", katalogData.getLokasiProduk());
            detailProduk.putExtra("deskripsi", katalogData.getDeskripsiProduk());
            detailProduk.putExtra("fotoProduk", katalogData.getFotoProduk());
            detailProduk.putExtra("namaPenjual", katalogData.getNamaPenjual());
            detailProduk.putExtra("whatsappPenjual", katalogData.getNoWhatsapp());

            context.startActivity(detailProduk);
        });
    }

    private String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return katalogItems.size();
    }


    public class KatalogViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_FotoProduk;
        ImageButton btn_Bookmark;
        TextView tv_NamaProduk, tv_HargaProduk, tv_KotaProduk;

        public KatalogViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_FotoProduk = itemView.findViewById(R.id.ivFotoProduk);
            btn_Bookmark = itemView.findViewById(R.id.btnBookmark);
            tv_NamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tv_HargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tv_KotaProduk = itemView.findViewById(R.id.tvKotaProduk);
        }
    }

    public void filterList(List<KatalogModel> filterKatalog){
        katalogItems = filterKatalog;
        notifyDataSetChanged();
    }
}




