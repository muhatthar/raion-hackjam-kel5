package com.example.raionhackjamkel5.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.homepage.HomePageActivity;
import com.example.raionhackjamkel5.model.KatalogModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends PagerAdapter {

    private boolean isExpand = false;
    private ImageButton btnBack;
    private ImageView iv_FotoProdukDetail;
    private TextView tv_NamaProdukDetail, tv_HargaJualDetail, tv_KotaProdukDetail, tv_HargaBeliDetail, tv_DeskripsiDetail, tv_NamaPenjual;
    private AppCompatButton btn_ReadMore;
    private ShapeableImageView iv_ProfilPenjual;
    private Context mcontext;
    private Intent mDataIntent;
    LayoutInflater layoutInflater;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    List<KatalogModel> detailItems;
    Uri imageUri = null;
    FirebaseStorage mStorage;
    private FirebaseAuth mAuth;

    public DetailAdapter(Context context, Intent DataIntent) {
        mcontext = context;
        mDataIntent = DataIntent;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.detail, container, false);

        detailItems = new ArrayList<>();

        iv_FotoProdukDetail = v.findViewById(R.id.ivFotoProdukDetail);
        tv_NamaProdukDetail = v.findViewById(R.id.tvNamaProdukDetail);
        tv_HargaJualDetail = v.findViewById(R.id.tvHargaJualDetail);
        tv_KotaProdukDetail = v.findViewById(R.id.tvKotaProdukDetail);
        tv_HargaBeliDetail = v.findViewById(R.id.tvHargaBeliDetail);
        tv_DeskripsiDetail = v.findViewById(R.id.tvDeskripsiDetail);
        tv_NamaPenjual = v.findViewById(R.id.tvNamaPenjual);
        btn_ReadMore = v.findViewById(R.id.btnReadMore);
        iv_ProfilPenjual = v.findViewById(R.id.ivProfilPenjual);
        btnBack = v.findViewById(R.id.btnBackDetail);

        btnBack.setOnClickListener(view -> {
            Intent homepage = new Intent(mcontext, HomePageActivity.class);
            mcontext.startActivity(homepage);
            if (mcontext instanceof Activity){
                ((Activity) mcontext).finish();
            }
        });

        String namaProduk = mDataIntent.getStringExtra("nama");
        String hargaBeli = mDataIntent.getStringExtra("hargaBeli");
        String hargaJual = mDataIntent.getStringExtra("hargaJual");
        String kota = mDataIntent.getStringExtra("kota");
        String deskripsi = mDataIntent.getStringExtra("deskripsi");
        String fotoProduk = mDataIntent.getStringExtra("fotoProduk");
        String namaPenjual = mDataIntent.getStringExtra("namaPenjual");
        String whatsappPenjual = mDataIntent.getStringExtra("whatsappPenjual");
        Picasso.get().load(fotoProduk).into(iv_FotoProdukDetail);

        tv_NamaProdukDetail.setText(namaProduk);
        tv_HargaJualDetail.setText("Rp " + formatNumberCurrency(hargaJual));
        tv_HargaBeliDetail.setText("Rp " + formatNumberCurrency(hargaBeli));
        tv_KotaProdukDetail.setText(kota);
        tv_DeskripsiDetail.setText(deskripsi);
        tv_NamaPenjual.setText(namaPenjual);

        tv_DeskripsiDetail.post(new Runnable() {
            @Override
            public void run() {
                if (tv_DeskripsiDetail.getLineCount() > tv_DeskripsiDetail.getMaxLines()) {
                    btn_ReadMore.setVisibility(View.VISIBLE);
                    btn_ReadMore.setOnClickListener(v -> {
                        toggleTextView();
                    });
                }
            }
        });

        mStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        container.addView(v);

        return v;
    }

    private String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(number));
    }

    private void toggleTextView(){
        if (isExpand) {
            tv_DeskripsiDetail.setMaxLines(3);
            btn_ReadMore.setText("Baca selengkapnya");
        } else {
            tv_DeskripsiDetail.setMaxLines(Integer.MAX_VALUE);
            btn_ReadMore.setText("Lebih sedikit");
        }
        isExpand = !isExpand;
    }
}
