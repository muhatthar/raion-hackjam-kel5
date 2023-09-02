package com.example.raionhackjamkel5.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.detail.DetailProdukActivity;
import com.example.raionhackjamkel5.kategoriAdapter.DapurAdapter;
import com.example.raionhackjamkel5.model.DapurModel;
import com.example.raionhackjamkel5.model.ElektronikModel;
import com.example.raionhackjamkel5.model.HiasanModel;
import com.example.raionhackjamkel5.model.KamarModel;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.example.raionhackjamkel5.model.PerabotModel;
import com.example.raionhackjamkel5.upload.EditActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

    public class ProdukSayaAdapter extends RecyclerView.Adapter<ProdukSayaAdapter.ProdukSayaViewHolder> {

    private List<KatalogModel> produkItems;
    private Context mcontext;
    private Intent mDataIntent;
    private List<PerabotModel> perabotItems;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage mStorage = FirebaseStorage.getInstance();

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
    public void onBindViewHolder(@NonNull ProdukSayaAdapter.ProdukSayaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KatalogModel produkData = produkItems.get(position);

        holder.tv_NamaProduk.setText(produkData.getNamaProduk());
        holder.tv_HargaProduk.setText("Rp " + formatNumberCurrency(produkData.getHargaJual()));
        holder.tv_KotaProduk.setText(produkData.getLokasiProduk());
        Picasso.get().load(produkData.getFotoProduk()).into(holder.iv_FotoProduk);

        holder.btn_Edit.setOnClickListener(v -> {
            Intent edit = new Intent(context, EditActivity.class);
            edit.putExtra("fotoProduk", produkData.getFotoProduk());
            edit.putExtra("nama", produkData.getNamaProduk());
            edit.putExtra("hargaBeli", produkData.getHargaBeli());
            edit.putExtra("hargaJual", produkData.getHargaJual());
            edit.putExtra("lokasi", produkData.getLokasiProduk());
            edit.putExtra("deskripsi", produkData.getDeskripsiProduk());
            edit.putExtra("key", produkData.getKey());

            context.startActivity(edit);
        });

        holder.iv_FotoProduk.setOnClickListener(v -> {
            Intent detailProduk = new Intent(context, DetailProdukActivity.class);
            detailProduk.putExtra("key", produkData.getKey());
            detailProduk.putExtra("nama", produkData.getNamaProduk());
            detailProduk.putExtra("hargaBeli", produkData.getHargaBeli());
            detailProduk.putExtra("hargaJual", produkData.getHargaJual());
            detailProduk.putExtra("kota", produkData.getLokasiProduk());
            detailProduk.putExtra("deskripsi", produkData.getDeskripsiProduk());
            detailProduk.putExtra("fotoProduk", produkData.getFotoProduk());
            detailProduk.putExtra("namaPenjual", produkData.getNamaPenjual());
            detailProduk.putExtra("whatsappPenjual", produkData.getNoWhatsapp());

            context.startActivity(detailProduk);
        });

        holder.btn_Delete.setOnClickListener(v -> {
            Dialog popup = new Dialog(context);
            popup.setContentView(R.layout.delete_popup);
            Window window = popup.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

            AppCompatButton cancel = popup.findViewById(R.id.btnDeleteCancel);
            AppCompatButton confirm = popup.findViewById(R.id.btnDeleteConfirm);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (produkData.getFotoProduk() != null) {
                        StorageReference storageRef = mStorage.getReferenceFromUrl(produkData.getFotoProduk());
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                database.child("Users").child(userId).child("ProdukSaya").child(produkData.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        database.child("SemuaProduk").child(produkData.getKey()).removeValue();
                                        database.child("Kategori").child(produkData.getKategoriProduk()).child(produkData.getKey()).removeValue();

                                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        produkItems.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                    popup.dismiss();
                }
            });
            popup.show();
        });
    }

    public ProdukSayaAdapter(Context context, Intent DataIntent) {
        mcontext = context;
        mDataIntent = DataIntent;
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

    private String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(number));
    }

    public void filterList(List<KatalogModel> filterKatalog){
        produkItems = filterKatalog;
        notifyDataSetChanged();
    }
}