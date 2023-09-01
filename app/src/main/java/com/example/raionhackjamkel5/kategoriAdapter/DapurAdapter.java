package com.example.raionhackjamkel5.kategoriAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.detail.DetailProdukActivity;
import com.example.raionhackjamkel5.model.DapurModel;
import com.example.raionhackjamkel5.upload.EditActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class DapurAdapter extends RecyclerView.Adapter<DapurAdapter.DapurViewHolder> {

    private List<DapurModel> dapurItems;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DapurAdapter(List<DapurModel> dapurItems, Context context){
        this.dapurItems = dapurItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DapurAdapter.DapurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View katalogView = inflater.inflate(R.layout.katalog_item, parent, false);
        return new DapurViewHolder(katalogView);
    }

    @Override
    public void onBindViewHolder(@NonNull DapurAdapter.DapurViewHolder holder, int position) {
//        katalogItems = getItemAtPosition(position);
        DapurModel katalogData = dapurItems.get(position);

        holder.tv_NamaProduk.setText(katalogData.getNamaProduk());
        holder.tv_HargaProduk.setText("Rp " + katalogData.getHargaJual());
        holder.tv_KotaProduk.setText(katalogData.getLokasiProduk());
        Picasso.get().load(katalogData.getFotoProduk()).into(holder.iv_FotoProduk);

        Intent sendKey = new Intent(context, EditActivity.class);
        sendKey.putExtra("keyDapur", katalogData.getKey());

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

    @Override
    public int getItemCount() {
        return dapurItems.size();
    }


    public class DapurViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_FotoProduk;
        ImageButton btn_Bookmark;
        TextView tv_NamaProduk, tv_HargaProduk, tv_KotaProduk;

        public DapurViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_FotoProduk = itemView.findViewById(R.id.ivFotoProduk);
            btn_Bookmark = itemView.findViewById(R.id.btnBookmark);
            tv_NamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tv_HargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tv_KotaProduk = itemView.findViewById(R.id.tvKotaProduk);
        }
    }
}




