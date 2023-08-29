package com.example.raionhackjamkel5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.raionhackjamkel5.R;

public class OnBoarding1Activity extends AppCompatActivity {

    AppCompatButton btn_Next;
    LinearLayout dots_Layout;
    ViewPager view_Pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding1);

        btn_Next = findViewById(R.id.btnNext);
        dots_Layout = findViewById(R.id.dotsLayout);
        view_Pager = findViewById(R.id.slider);

        OnBoardingAdapter adapter = new OnBoardingAdapter(this);
        view_Pager.setAdapter(adapter);
    }
}