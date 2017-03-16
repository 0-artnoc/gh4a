package com.gh4a.widget;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.gh4a.R;

public class ViewPagerBottomSheet extends NestedScrollView {

    private ViewPager mViewPager;
    private TabLayout mTabs;
    private BottomSheetBehavior mBehavior;

    public ViewPagerBottomSheet(Context context) {
        super(context);
        initialize(context);
    }

    public ViewPagerBottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ViewPagerBottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        View view = View.inflate(context, R.layout.view_pager_bottom_sheet, this);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mTabs = (TabLayout) view.findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mViewPager);

        if (!isInEditMode()) {
            mBehavior = BottomSheetBehavior.from(this);
        }
    }

    public void setPagerAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);

        LinearLayout tabStrip = (LinearLayout) mTabs.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tab = tabStrip.getChildAt(i);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab.getLayoutParams();
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.weight = 1;
            tab.setLayoutParams(lp);
        }
    }

    public BottomSheetBehavior getBehavior() {
        return mBehavior;
    }
}
