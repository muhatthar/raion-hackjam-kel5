package com.example.raionhackjamkel5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class OnBoardingAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public OnBoardingAdapter(Context context) {
        this.context = context;
    }

    int titles[] = {
            R.string.onboard_title1,
            R.string.onboard_title2
    };

    int descs[] = {
            R.string.onboard_desc1,
            R.string.onboard_desc2
    };

    int images[] = {
            R.drawable.ic_logo_bg,
            R.drawable.ic_reuse
    };

    int imgViews[] = {
            R.drawable.bg_onboard1,
            R.drawable.bg_onboard2
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.slide, container, false);

        ImageView image = v.findViewById(R.id.slideImg);
        TextView title = v.findViewById(R.id.slideTitle);
        TextView desc = v.findViewById(R.id.slideDesc);
        View view = v.findViewById(R.id.viewImg);
        ConstraintLayout layout = v.findViewById(R.id.slideLayout);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        desc.setText(descs[position]);
        view.setBackgroundResource(imgViews[position]);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) image.getLayoutParams();
        if (position == 0) {
            params.topMargin = dpToPx(320); // Ubah 320 sesuai dengan nilai yang diinginkan
        } else if (position == 1) {
            params.topMargin = dpToPx(167); // Ubah 167 sesuai dengan nilai yang diinginkan
        }
        image.setLayoutParams(params);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
