package com.example.raionhackjamkel5.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.homepage.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText et_Email, et_Password;
    private Button btn_SignIn, btn_SignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        FirebaseApp.initializeApp(this);

        et_Email = findViewById(R.id.etEmail);
        et_Password = findViewById(R.id.etPassword);
        btn_SignIn = findViewById(R.id.btnSignIn);
        btn_SignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        et_Email.addTextChangedListener(textWatcher);
        et_Password.addTextChangedListener(textWatcher);

        btn_SignUp.setOnClickListener(v -> {
            Intent signup = new Intent (SignInActivity.this, SignUpActivity.class);
            startActivity(signup);
        });

        btn_SignIn.setOnClickListener(v -> {
            SignIn(et_Email.getText().toString(), et_Password.getText().toString());
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

    public void updateUI(FirebaseUser user){
        if (user != null){
            Intent login = new Intent(SignInActivity.this, HomePageActivity.class);
            startActivity(login);
        } else {
            Toast.makeText(this, "Sign Up Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm(){
        boolean result = true;
        String password = et_Password.getText().toString();

        if (TextUtils.isEmpty(et_Email.getText().toString())){
            Toast.makeText(this, "Isi Email Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (TextUtils.isEmpty(et_Password.getText().toString())){
            Toast.makeText(this, "Isi Password Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if (password.length() < 8) {
            et_Password.setError("Minimal 8 karakter diperlukan");
            result = false;
        }

        return result;
    }

    public void SignIn(String email, String password){
        if (!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(SignInActivity.this, "Email dan Password tidak Valid", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = et_Email.getText().toString().trim();
            String password = et_Password.getText().toString().trim();
            boolean isFormValid = !email.isEmpty() && !password.isEmpty();
            btn_SignIn.setEnabled(isFormValid);

            if (isFormValid) {
                btn_SignIn.setBackgroundResource(R.drawable.btn_primary_bg);
                btn_SignIn.setTextColor(getResources().getColor(R.color.white));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}