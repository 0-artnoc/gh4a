package com.gh4a.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gh4a.R;
import com.gh4a.adapter.RootAdapter;
import com.gh4a.loader.LoaderCallbacks;
import com.gh4a.utils.UiUtils;
import com.gh4a.widget.DividerItemDecoration;
import com.gh4a.widget.SwipeRefreshLayout;

public abstract class LoadingListFragmentBase extends LoadingFragmentBase implements
        LoaderCallbacks.ParentCallback, SwipeRefreshLayout.ChildScrollDelegate {
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;

    public interface OnRecyclerViewCreatedListener {
        void onRecyclerViewCreated(Fragment fragment, RecyclerView recyclerView);
    }

    public LoadingListFragmentBase() {

    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_fragment_content, parent, false);

        mEmptyView = (TextView) view.findViewById(android.R.id.empty);
        int emptyTextResId = getEmptyTextResId();
        if (emptyTextResId != 0) {
            mEmptyView.setText(emptyTextResId);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        onRecyclerViewInflated(mRecyclerView, inflater);
        if (hasDividers()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext()));
        }
        if (!hasCards()) {
            mRecyclerView.setBackgroundResource(
                    UiUtils.resolveDrawable(getActivity(), R.attr.listBackground));
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof OnRecyclerViewCreatedListener) {
            ((OnRecyclerViewCreatedListener) getActivity()).onRecyclerViewCreated(this, mRecyclerView);
        }
    }

    @Override
    public boolean canChildScrollUp() {
        return getView() != null && UiUtils.canViewScrollUp(mRecyclerView);
    }

    protected void updateEmptyState() {
        if (mRecyclerView == null) {
            return;
        }

        boolean empty = false;
        RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
        if (adapter instanceof RootAdapter) {
            // don't count headers and footers
            empty = ((RootAdapter) adapter).getCount() == 0;
        } else if (adapter != null) {
            empty = adapter.getItemCount() == 0;
        }

        mRecyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    protected void onRecyclerViewInflated(RecyclerView view, LayoutInflater inflater) {

    }

    protected boolean hasDividers() {
        return true;
    }
    protected boolean hasCards() { return false; }

    @Override
    protected void setHighlightColors(int colorAttrId, int statusBarColorAttrId) {
        super.setHighlightColors(colorAttrId, statusBarColorAttrId);
        UiUtils.trySetListOverscrollColor(mRecyclerView, getHighlightColor());
    }

    protected abstract int getEmptyTextResId();
}
