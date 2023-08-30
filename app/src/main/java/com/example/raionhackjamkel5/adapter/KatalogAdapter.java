package com.example.raionhackjamkel5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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
        KatalogModel katalogData = katalogItems.get(position);
        holder.tv_NamaProduk.setText(katalogData.getNamaProduk());
        holder.tv_HargaProduk.setText(katalogData.getHargaJual());
        holder.tv_KotaProduk.setText(katalogData.getLokasiProduk());
    }

    @Override
    public int getItemCount() {
        return katalogItems.size();
    }

    public class KatalogViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_FotoProduk;
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
}
