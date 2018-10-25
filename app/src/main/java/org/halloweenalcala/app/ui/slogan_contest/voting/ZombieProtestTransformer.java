package org.halloweenalcala.app.ui.slogan_contest.voting;

import android.view.View;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;

/**
 * Created by julio on 17/10/17.
 */

public class ZombieProtestTransformer extends ABaseTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    protected void onTransform(View view, float position) {

        final float rotation = 15f * position; // frecuencia de bote
        float bote = (float) (Math.sin(rotation)*30); // amplitud del bote

        if (position <= 0f) {

            view.setTranslationX(view.getWidth()*-position);
            view.setTranslationY(-bote);

            float scale = 1f - position * 1f;
            view.setScaleX(scale);
            view.setScaleY(scale);

            view.setPivotX(view.getWidth() * 0.85f);
            view.setPivotY(0);

            if (position < -0.7f) {
                view.setAlpha(1 + position*1.43f);
            }


//            view.setPivotX(0);
//            view.setPivotY(view.getHeight() * 0.5f);
//            view.setRotationY(rotation);

        } else if (position <= 1f) {
//            final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

//            view.setAlpha(1 - position);
            view.setTranslationX(view.getWidth() * -position);
            view.setTranslationY(bote);
//            float scale = 1f - position * 1.3f;
//            view.setScaleX(scale);
//            view.setScaleY(scale);
//
//            view.setPivotX(view.getWidth() * 0.5f);
//            view.setPivotY(view.getHeight());

//            view.setPivotY(0.5f * view.getHeight());
//            view.setScaleX(scaleFactor);
//            view.setScaleY(scaleFactor);
        }

    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }
}
