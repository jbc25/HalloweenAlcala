package org.halloweenalcala.app.ui.slogan_contest.voting;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.halloweenalcala.app.R;


/**
 * Created by julio on 16/03/18.
 */

public class BrainRatingView extends LinearLayout implements View.OnClickListener {

    private int rating;
    private OnRatingChangeListener onRatingChangeListener;

    public BrainRatingView(Context context) {
        super(context);
        init();
    }

    public BrainRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrainRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setOrientation(HORIZONTAL);

        for (int i = 0; i < 5; i++) {
            View apple = View.inflate(getContext(), R.layout.view_brain_rating, null);
            apple.setTag(i + 1);
            apple.setOnClickListener(this);
            addView(apple);
        }

        invalidate();
    }


    @Override
    public void onClick(View v) {

        int position = Integer.parseInt(v.getTag().toString());
        showRating(position, true);
    }

    private void showRating(int rating, boolean fromUser) {

        this.rating = rating;

        for (int i = 0; i < getChildCount(); i++) {
            View apple = getChildAt(i);
            boolean mustSelectApple = Integer.parseInt(apple.getTag().toString()) <= rating;
            apple.setSelected(mustSelectApple);
        }

        invalidate();

        if (fromUser) {
            if (onRatingChangeListener != null) {
                onRatingChangeListener.onRatingChange(rating);
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        showRating(rating, false);
    }

    public interface OnRatingChangeListener {
        void onRatingChange(int rating);
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }
}
