package jp.co.pockeps.observablescrollviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    @BindView(R.id.image) ImageView mImageView;
    @BindView(R.id.list_background) View mListBackgroundView;
    @BindView(R.id.list) ObservableListView mListView;
    @BindView(R.id.toolbar) Toolbar mToolbarView;
    private ParallaxHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxtoolbarlistview);
        ButterKnife.bind(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        helper = new ParallaxHelper(this);
        mToolbarView.setBackgroundColor(helper.getToolbarColor());

        mListView.setScrollViewCallbacks(this);
        mListView.addHeaderView(helper.getListViewHeader());

        Dummy dummy = new Dummy(this);
        dummy.setDummyData(mListView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mListView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        mToolbarView.setBackgroundColor(helper.getToolbarColorWithAlpha(scrollY));
        mImageView.setTranslationY(helper.getImageTranslationY(scrollY));

        // Translate list background
        mListBackgroundView.setTranslationY(helper.getListBackGroundTransitionY(scrollY));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }
}
