package com.example.lyz.myv2ex.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.utils.DateUtils;


public class RelativeTimeTextView extends TextView {

    private long referenceTime;
    private Handler handler = new Handler();

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long difference = Math.abs(System.currentTimeMillis() - referenceTime * 1000);
            long interval = AppConfig.MINUTE_IN_MILLIS;
            if(difference > AppConfig.YEAR_IN_MILLIS) {
                interval = AppConfig.YEAR_IN_MILLIS;
            } else if(difference > AppConfig.MONTH_IN_MILLIS) {
                interval = AppConfig.MONTH_IN_MILLIS;
            } else if(difference > AppConfig.WEEK_IN_MILLIS) {
                interval = AppConfig.WEEK_IN_MILLIS;
            } else if(difference > AppConfig.DAY_IN_MILLIS) {
                interval = AppConfig.DAY_IN_MILLIS;
            } else if(difference > AppConfig.HOUR_IN_MILLIS) {
                interval = AppConfig.HOUR_IN_MILLIS;
            }
            updateDisplayText();
            handler.postDelayed(this, interval);
        }
    };

    private void updateDisplayText() {
        if(referenceTime <= 0)
            return;
        setText(DateUtils.convertDistTimeStampToString(referenceTime));
    }

    public void setReferenceTime(long referenceTime) {
        this.referenceTime = referenceTime;
        updateDisplayText();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        DebugLog.i("onVisibilityChanged was called.");
        if(visibility == GONE || visibility == INVISIBLE) {
            stopPeriodicallyUpdatingRelativeTime();
        } else {
            startPeriodicallyUpdatingRelativeTime();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        DebugLog.i("onAttachedToWindow was called.");
        startPeriodicallyUpdatingRelativeTime();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DebugLog.i("onDetachedFromWindow was called.");
        stopPeriodicallyUpdatingRelativeTime();
    }

    private void stopPeriodicallyUpdatingRelativeTime() {
        handler.removeCallbacks(updateTimeTask);
    }

    private void startPeriodicallyUpdatingRelativeTime() {
        handler.post(updateTimeTask);
    }

    public RelativeTimeTextView(Context context) {
        super(context);
    }

    public RelativeTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RelativeTimeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.savedReferenceTime = referenceTime;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState)state;
        referenceTime = savedState.savedReferenceTime;
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    public static class SavedState extends BaseSavedState {

        private long savedReferenceTime;

        private SavedState(Parcel source) {
            super(source);
            savedReferenceTime = source.readLong();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(savedReferenceTime);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
