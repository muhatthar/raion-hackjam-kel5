package com.example.raionhackjamkel5.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_Nama, et_Email, et_Whatsapp, et_Kota, et_Password;
    Button btn_SignUp;
    private FirebaseAuth mAuth;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_Nama = findViewById(R.id.etRNama);
        et_Email = findViewById(R.id.etREmail);
        et_Whatsapp = findViewById(R.id.etRWhatsapp);
        et_Kota = findViewById(R.id.etRKotaAsal);
        et_Password = findViewById(R.id.etRPassword);
        btn_SignUp = findViewById(R.id.btnRSignUp);

        mAuth = FirebaseAuth.getInstance();

        btn_SignUp.setOnClickListener(v -> {
            signUp(et_Email.getText().toString(), et_Password.getText().toString());
        });
    }

    public void updateUI(FirebaseUser user){
        if (user != null){
            Intent signIn = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(signIn);
        } else {
            Toast.makeText(SignUpActivity.this, "Sign Up Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm(){
        boolean result = true;
        String password = et_Password.getText().toString();

        if (TextUtils.isEmpty(et_Email.getText().toString())){
            Toast.makeText(this, "Masukkan E-Mail Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Password.getText().toString())){
            Toast.makeText(this, "Masukkan Password Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Nama.getText().toString())){
            Toast.makeText(this, "Masukkan Nama Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Whatsapp.getText().toString())){
            Toast.makeText(this, "Masukkan Nomor Whatsapp Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Kota.getText().toString())){
            Toast.makeText(this, "Masukkan Kota Asal Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (password.length() < 8) {
            et_Password.setError("Minimal 8 Karakter Diperlukan");
            result = false;
        }

        return result;
    }

    public void signUp(String email, String password){
        if (!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Email dan Password tidak Valid", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

        String nama = et_Nama.getText().toString();
        String whatsapp = et_Whatsapp.getText().toString();
        String asal = et_Kota.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
//      String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.child("User").child(mAuth.getUid()).push().setValue(new UserModel(nama, whatsapp, asal)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SignUpActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                updateUI(user);
            }
        });
    }
}