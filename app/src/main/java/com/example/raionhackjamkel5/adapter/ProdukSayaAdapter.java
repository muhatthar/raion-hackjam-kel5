package com.example.raionhackjamkel5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdukSayaAdapter extends RecyclerView.Adapter<ProdukSayaAdapter.ProdukSayaViewHolder> {
    private List<KatalogModel> produkItems;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public ProdukSayaAdapter(List<KatalogModel> produkItems, Context context) {
        this.produkItems = produkItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdukSayaAdapter.ProdukSayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View produkView = inflater.inflate(R.layout.produk_saya_item, parent, false);
        return new ProdukSayaViewHolder(produkView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukSayaAdapter.ProdukSayaViewHolder holder, int position) {
        KatalogModel produkData = produkItems.get(position);

        holder.tv_NamaProduk.setText(produkData.getNamaProduk());
        holder.tv_HargaProduk.setText(produkData.getHargaJual());
        holder.tv_KotaProduk.setText(produkData.getLokasiProduk());
        Picasso.get().load(produkData.getFotoProduk()).into(holder.iv_FotoProduk);
    }

    @Override
    public int getItemCount() {
        return produkItems.size();
    }

    public class ProdukSayaViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_FotoProduk;
        ImageButton btn_Edit, btn_Delete;
        TextView tv_NamaProduk, tv_HargaProduk, tv_KotaProduk;
        public ProdukSayaViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_FotoProduk = itemView.findViewById(R.id.ivFotoProdukSaya);
            btn_Delete = itemView.findViewById(R.id.btnDelete);
            btn_Edit = itemView.findViewById(R.id.btnEdit);
            tv_NamaProduk = itemView.findViewById(R.id.tvNamaProdukSaya);
            tv_HargaProduk = itemView.findViewById(R.id.tvHargaProdukSaya);
            tv_KotaProduk = itemView.findViewById(R.id.tvKotaProdukSaya);
        }
    }
}
