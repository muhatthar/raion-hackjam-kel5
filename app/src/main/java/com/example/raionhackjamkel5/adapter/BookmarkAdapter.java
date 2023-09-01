package com.example.raionhackjamkel5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.detail.DetailProdukActivity;
import com.example.raionhackjamkel5.model.BookmarkModel;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.example.raionhackjamkel5.upload.EditActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private List<KatalogModel> bookmarkItems;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public BookmarkAdapter(List<KatalogModel> bookmarkItems, Context context){
        this.bookmarkItems = bookmarkItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarkAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View katalogView = inflater.inflate(R.layout.bookmark_item, parent, false);
        return new BookmarkViewHolder(katalogView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.BookmarkViewHolder holder, int position) {
//        katalogItems = getItemAtPosition(position);
        KatalogModel katalogData = bookmarkItems.get(position);

        holder.tv_NamaProduk.setText(katalogData.getNamaProduk());
        holder.tv_HargaProduk.setText("Rp " + katalogData.getHargaJual());
        holder.tv_KotaProduk.setText(katalogData.getLokasiProduk());
        Picasso.get().load(katalogData.getFotoProduk()).into(holder.iv_FotoProduk);

        Intent sendKey = new Intent(context, EditActivity.class);
        sendKey.putExtra("keyKatalog", katalogData.getKey());

        holder.btn_Bookmark.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            database.child("Users").child(userId).child("Bookmark").child(katalogData.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Data berhasil dihapus dari bookmark", Toast.LENGTH_SHORT).show();

                    bookmarkItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
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

    @Override
    public int getItemCount() {
        return bookmarkItems.size();
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_FotoProduk;
        ImageButton btn_Bookmark;
        TextView tv_NamaProduk, tv_HargaProduk, tv_KotaProduk;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_FotoProduk = itemView.findViewById(R.id.ivFotoProdukBook);
            btn_Bookmark = itemView.findViewById(R.id.btnBookmarkBook);
            tv_NamaProduk = itemView.findViewById(R.id.tvNamaProdukBook);
            tv_HargaProduk = itemView.findViewById(R.id.tvHargaProdukBook);
            tv_KotaProduk = itemView.findViewById(R.id.tvKotaProdukBook);
        }
    }

    public void filterList(List<KatalogModel> filterKatalog){
        bookmarkItems = filterKatalog;
        notifyDataSetChanged();
    }
}




