package com.example.raionhackjamkel5.profil;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.model.UserModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePageFragment extends Fragment {

    View view;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ShapeableImageView ivProfil;
    TextView tvNamaUser, tvEmailUser, tvJumlahBook;
    CardView btnLogout;
    ImageButton btnNextPengaturanAkun, btnNextBookmark;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilePageFragment newInstance(String param1, String param2) {
        ProfilePageFragment fragment = new ProfilePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        ivProfil = view.findViewById(R.id.ivProfil);
        tvNamaUser = view.findViewById(R.id.tvNamaUser);
        tvEmailUser = view.findViewById(R.id.tvEmailUser);
        tvJumlahBook = view.findViewById(R.id.tvJumlahBook);
        btnLogout = view.findViewById(R.id.btn_Logout);
        btnNextBookmark = view.findViewById(R.id.btn_NextBookmark);
        btnNextPengaturanAkun = view.findViewById(R.id.btn_NextPengaturanAkun);
        user = mAuth.getCurrentUser();

        btnNextPengaturanAkun.setOnClickListener(v -> {
            Intent pengaturanAkun = new Intent(getContext(), PengaturanAkunPage.class);
            startActivity(pengaturanAkun);
        });

        btnNextBookmark.setOnClickListener(v -> {
            Intent bookmark = new Intent(getContext(), BookmarkPageActivity.class);
            startActivity(bookmark);
        });

        if (user != null) {
            String userId = user.getUid();
            String email = user.getEmail().toString();

            database.child("Users").child(userId).child("UserData").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        UserModel user = snapshot.getValue(UserModel.class);
                        user.setKey(snapshot.getKey());
                        tvNamaUser.setText(user.getNama());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            database.child("Users").child(userId).child("Bookmark").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long childBookmark = snapshot.getChildrenCount();
                    tvJumlahBook.setText(String.valueOf(childBookmark) + " produk");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            tvEmailUser.setText(email);

        }

        return view;
    }
}