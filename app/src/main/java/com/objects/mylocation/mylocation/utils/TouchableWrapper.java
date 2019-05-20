package com.objects.mylocation.mylocation.utils;

/**
 * Created by ayman on 2019-05-19.
 */

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.objects.mylocation.mylocation.MainActivity;

public class TouchableWrapper extends FrameLayout {

    public TouchableWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                MainActivity.mMapIsTouched = true;
                break;

            case MotionEvent.ACTION_UP:
                MainActivity.mMapIsTouched = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}