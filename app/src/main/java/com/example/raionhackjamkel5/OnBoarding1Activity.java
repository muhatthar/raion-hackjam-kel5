package com.example.raionhackjamkel5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raionhackjamkel5.R;
import com.example.raionhackjamkel5.helpers.SaveState;
import com.example.raionhackjamkel5.homepage.HomePageActivity;

public class OnBoarding1Activity extends AppCompatActivity {

    AppCompatButton btn_Next;
    LinearLayout dots_Layout;
    ViewPager view_Pager;
    TextView[] dots;
    int currentPosition;
    SaveState saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding1);

        btn_Next = findViewById(R.id.btnNext);
        dots_Layout = findViewById(R.id.dotsLayout);
        view_Pager = findViewById(R.id.slider);
        dotsFunction(0);
        saveState = new SaveState(OnBoarding1Activity.this, "0B");
        if (saveState.getState() == 1){
            Intent homepage = new Intent(OnBoarding1Activity.this, HomePageActivity.class);
            startActivity(homepage);
        }

        OnBoardingAdapter adapter = new OnBoardingAdapter(this);
        view_Pager.setAdapter(adapter);
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_Pager.setCurrentItem(currentPosition + 1, true);
            }
        });
        view_Pager.setOnPageChangeListener(onPageChangeListener);
    }

    private void dotsFunction(int pos){
        dots = new TextView[2];
        dots_Layout.removeAllViews();

        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("-"));
            dots[i].setTextColor(Color.parseColor("#E9E9E9"));
            dots[i].setTextSize(30);

            dots_Layout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[pos].setTextColor(Color.parseColor("#3C8918"));
            dots[pos].setTextSize(40);
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            dotsFunction(position);
            currentPosition = position;
            if (currentPosition <= 1){
                btn_Next.setText("Mulai");
                btn_Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_Pager.setCurrentItem(currentPosition);
                    }
                });
            } else {
                btn_Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveState.setState(1);
                        Intent homepage = new Intent(OnBoarding1Activity.this, HomePageActivity.class);
                        startActivity(homepage);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}