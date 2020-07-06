package com.android.rb.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.rb.R;

public class RBNonSwipePageView extends ViewPager {

    public boolean swipeable;

    public RBNonSwipePageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrsParams(context, attrs);
    }

    private void getAttrsParams(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RBNonSwipePageView);
        try {
            swipeable = a.getBoolean(R.styleable.RBNonSwipePageView_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable && super.onInterceptTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable && super.onTouchEvent(event);
    }
}
