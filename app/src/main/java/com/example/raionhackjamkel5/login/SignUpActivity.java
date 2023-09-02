package com.example.raionhackjamkel5.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

    private EditText et_Nama, et_Email, et_Whatsapp, et_Kota, et_Password, et_ConfirmPassword;
    ImageButton btn_Back;
    TextView tv_SignIn;
    private AppCompatCheckBox cb_Agree;
    Button btn_SignUp;
    private FirebaseAuth mAuth;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private boolean isPasswordVisible = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_Nama = findViewById(R.id.etRNama);
        et_Email = findViewById(R.id.etREmail);
        et_Whatsapp = findViewById(R.id.etRWhatsapp);
        cb_Agree = findViewById(R.id.cb_Agree);
        et_Kota = findViewById(R.id.etRKotaAsal);
        et_ConfirmPassword = findViewById(R.id.etConfirmPasswordRegistrasi);
        et_Password = findViewById(R.id.etPasswordRegistrasi);
        btn_SignUp = findViewById(R.id.btnRSignUp);
        tv_SignIn = findViewById(R.id.tvSignIn);
        btn_Back = findViewById(R.id.btnBack);

        btn_Back.setOnClickListener(v -> {
            Intent backSignIn = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(backSignIn);
            finish();
        });

        Typeface customFont = ResourcesCompat.getFont(this, R.font.poppins_regular);

        cb_Agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkFormValidity();
            }
        });

        checkFormValidity();
        
        et_Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_Password.getRight() - et_Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        isPasswordVisible = !isPasswordVisible;

                        if (isPasswordVisible) {
                            et_Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            et_Password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toggle_visible, 0);
                            et_Password.setTypeface(customFont);
                            et_Password.setTextColor(getColor(R.color.black));
                            et_Password.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        } else {
                            et_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_Password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toggle_invisible, 0);
                            et_Password.setTypeface(customFont);
                            et_Password.setTextColor(getColor(R.color.black));
                            et_Password.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        }

                        // Memindahkan kursor ke akhir teks
                        et_Password.setSelection(et_Password.length());

                        return true;
                    }
                }
                return false;
            }
        });

        et_ConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_ConfirmPassword.getRight() - et_ConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        isPasswordVisible = !isPasswordVisible;

                        if (isPasswordVisible) {
                            et_ConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_ConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toggle_visible, 0);
                            et_ConfirmPassword.setTypeface(customFont);
                            et_ConfirmPassword.setTextColor(getColor(R.color.black));
                            et_ConfirmPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        } else {
                            et_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_ConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toggle_invisible, 0);
                            et_ConfirmPassword.setTypeface(customFont);
                            et_ConfirmPassword.setTextColor(getColor(R.color.black));
                            et_ConfirmPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        }

                        // Memindahkan kursor ke akhir teks
                        et_ConfirmPassword.setSelection(et_ConfirmPassword.length());

                        return true;
                    }
                }
                return false;
            }
        });

        String oriSignIn = getString(R.string.sign_in);
        String targetSignIn = "Masuk";
        int startIndex = oriSignIn.indexOf(targetSignIn);
        int endIndex = startIndex + targetSignIn.length();

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(oriSignIn);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent signIn = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(signIn);
            }
        };

        spannableStringBuilder.setSpan(clickableSpan, startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_SignIn.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
        tv_SignIn.setMovementMethod(LinkMovementMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();

        btn_SignUp.setOnClickListener(v -> {
            signUp(et_Email.getText().toString(), et_Password.getText().toString());
        });
    }

    private void checkFormValidity() {
        String email = et_Email.getText().toString().trim();
        String password = et_Password.getText().toString().trim();
        String nama = et_Nama.getText().toString().trim();
        String asal = et_Kota.getText().toString().trim();
        String whatsapp = et_Whatsapp.getText().toString().trim();
        boolean isFormValid = !email.isEmpty() && !password.isEmpty() && !nama.isEmpty() && !asal.isEmpty() && !whatsapp.isEmpty() && cb_Agree.isChecked();
        btn_SignUp.setEnabled(isFormValid);

        if (isFormValid) {
            btn_SignUp.setBackgroundResource(R.drawable.btn_primary_bg);
            btn_SignUp.setTextColor(getResources().getColor(R.color.white));
        }
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
            Toast.makeText(this, "Masukkan e-mail", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Password.getText().toString())){
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Nama.getText().toString())){
            Toast.makeText(this, "Masukkan nama anda", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Whatsapp.getText().toString())){
            Toast.makeText(this, "Masukkan nomor whatsapp", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Kota.getText().toString())){
            Toast.makeText(this, "Masukkan Kota asal anda", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_ConfirmPassword.getText().toString())){
            Toast.makeText(this, "Masukkan konfirmasi password", Toast.LENGTH_SHORT).show();
            result = false;
        } else if (!TextUtils.isEmpty(et_ConfirmPassword.getText().toString()) && !et_ConfirmPassword.getText().toString().equals(et_Password.getText().toString())){
            Toast.makeText(this, "Password anda tidak sama", Toast.LENGTH_SHORT).show();
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
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null){
                                String userId = user.getUid();
                                String nama = et_Nama.getText().toString();
                                String kota = et_Kota.getText().toString();
                                String whatsapp = et_Whatsapp.getText().toString();

                                DatabaseReference userRef = database.child("Users").child(userId).child("UserData");
                                UserModel userModel = new UserModel(nama, whatsapp, kota);
                                userRef.setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignUpActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                                    }
                                }). addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Email dan Password tidak Valid", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}