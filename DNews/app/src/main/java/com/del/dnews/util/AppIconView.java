package com.del.dnews.util;

import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.animation.ValueAnimator;
import android.content.Context;
import android.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.MotionEvent;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.media.ThumbnailUtils;
import com.del.dnews.R;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.graphics.BitmapFactory;


public class AppIconView extends AppCompatImageView {

    private Bitmap fgBitmap;
    private Paint paint;
    private int size;
    private float rotation = 90;
    private float fgScale = 0, bgScale = 0;

    private Path path;
    private ValueAnimator animator;

    public AppIconView(Context context) {
        this(context, null);
    }

    public AppIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        ValueAnimator animator = ValueAnimator.ofFloat(bgScale, 0.8f);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(2000);
        animator.setStartDelay(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    bgScale = (float) animator.getAnimatedValue();
                    invalidate();
                }
            });
        animator.start();

        animator = ValueAnimator.ofFloat(rotation, 360);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(2000);
        animator.setStartDelay(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    fgScale = animator.getAnimatedFraction() * 0.8f;
                    rotation = (float) animator.getAnimatedValue();
                    invalidate();
                }
            });
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (animator != null && animator.isStarted())
                    animator.cancel();

                animator = ValueAnimator.ofFloat(fgScale, 1);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            fgScale = (float) valueAnimator.getAnimatedValue();
                            bgScale = ((fgScale - 0.8f) / 2) + 0.8f;
                            invalidate();
                        }
                    });
                animator.start();
                break;
            case MotionEvent.ACTION_UP:
                if (animator != null && animator.isStarted())
                    animator.cancel();

                animator = ValueAnimator.ofFloat(fgScale, 0.8f);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            fgScale = (float) valueAnimator.getAnimatedValue();
                            bgScale = ((fgScale - 0.8f) / 2) + 0.8f;
                            invalidate();
                        }
                    });
                animator.start();
                break;
        }
        return true;
    }

    private Bitmap getRoundBitmap(@DrawableRes int drawable, int size) {
        Bitmap bitmap = drawableToBitmap(ContextCompat.getDrawable(getContext(), drawable));
        bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 6, bitmap.getHeight() / 6, (int) (0.666 * bitmap.getWidth()), (int) (0.666 * bitmap.getHeight()));
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, size, size);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(size / 2);
        roundedBitmapDrawable.setAntiAlias(true);

        return drawableToBitmap(roundedBitmapDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int size = Math.min(canvas.getWidth(), canvas.getHeight());
        if (this.size != size || fgBitmap == null || path == null) {
            this.size = size;
            fgBitmap = getRoundBitmap(R.drawable.ic_launcher, size);
            
            path = new Path();
            path.arcTo(new RectF(0, 0, size, size), 0, 359);
            path.close();
        }

        Matrix matrix = new Matrix();
        matrix.postScale(bgScale, bgScale, size / 2, size / 2);

        Path path = new Path();
        this.path.transform(matrix, path);
        canvas.drawPath(path, paint);

        matrix = new Matrix();
        matrix.postTranslate(-fgBitmap.getWidth() / 2, -fgBitmap.getHeight() / 2);
        matrix.postScale(fgScale, fgScale);
        matrix.postRotate(rotation);
        matrix.postTranslate(fgBitmap.getWidth() / 2, fgBitmap.getHeight() / 2);
        canvas.drawBitmap(fgBitmap, matrix, paint);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);

        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
                return bitmapDrawable.getBitmap();
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        else
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
