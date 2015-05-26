package com.gh4a.activities.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;

public abstract class FragmentFactory {
    protected HomeActivity mActivity;

    protected FragmentFactory(HomeActivity activity) {
        mActivity = activity;
    }

    protected abstract int getTitleResId();
    protected abstract int[] getTabTitleResIds();
    protected abstract Fragment getFragment(int position);
    protected abstract void onRefreshFragment(Fragment fragment);

    /* expected format: int[tabCount][2] - 0 is header, 1 is status bar */
    protected int[][] getTabHeaderColors() {
        return null;
    }

    protected ListAdapter getToolDrawerAdapter() {
        return null;
    }

    protected boolean onDrawerItemSelected(int position) {
        return false;
    }

    protected boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    protected boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    protected void onSaveInstanceState(Bundle outState) {}

    protected void onStart() {}
}
