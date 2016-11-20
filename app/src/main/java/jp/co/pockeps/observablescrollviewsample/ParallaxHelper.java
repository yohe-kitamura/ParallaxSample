package jp.co.pockeps.observablescrollviewsample;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AbsListView;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.lang.ref.WeakReference;

public class ParallaxHelper {

    @NonNull final private WeakReference<Context> contextWeakReference;
    private int toolbarColor = R.color.primary;
    private int parallaxImageHeight = R.dimen.parallax_image_height;

    public ParallaxHelper(@NonNull Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    public int getToolbarColor() {
        Context context = contextWeakReference.get();
        return context != null ? ScrollUtils.getColorWithAlpha(0, ContextCompat.getColor(context, toolbarColor)) : 0;
    }

    public void setToolbarColor(@ColorRes int toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public int getToolbarColorWithAlpha(int scrollY) {
        return ScrollUtils.getColorWithAlpha(getAlpha(scrollY), getToolbarColor());
    }

    public int getParallaxImageHeight() {
        Resources resources = getResources();
        return resources != null ? resources.getDimensionPixelSize(parallaxImageHeight) : 0;
    }

    public void setParallaxImageHeight(@DimenRes int parallaxImageHeight) {
        this.parallaxImageHeight = parallaxImageHeight;
    }

    @Nullable
    public View getListViewHeader() {
        Context context = getContext();
        View paddingView = null;
        if (context != null) {
            paddingView = new View(context);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    getParallaxImageHeight());
            paddingView.setLayoutParams(lp);

            paddingView.setClickable(true);

        }
        return paddingView;
    }

    public float getAlpha(float scrollY) {
        return Math.min(1, scrollY / getParallaxImageHeight());
    }

    public int getListBackGroundTransitionY(int scrollY) {
        return Math.max(0, -scrollY + getParallaxImageHeight());
    }

    public int getImageTranslationY(int scrollY) {
        return -scrollY / 2;
    }

    @Nullable
    private Context getContext() {
        return contextWeakReference.get();
    }

    @Nullable
    private Resources getResources() {
        Context context = getContext();
        return context != null ? context.getResources() : null;
    }
}
