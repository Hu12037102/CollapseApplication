package com.xiaobai.collapseapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DataViewPager extends ViewPager {
    public DataViewPager(@NonNull Context context) {
        super(context);
    }

    public DataViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalAccessError error) {
            error.fillInStackTrace();
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalAccessError error) {
            error.fillInStackTrace();
        }
        return false;
    }
}
