package com.androidwind.gank.ganksearch;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.bean.entity.SearchBean;
import com.androidwind.gank.bean.model.SearchGank;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.androidwind.androidquick.ui.adapter.CommonAdapter;
import com.androidwind.androidquick.ui.adapter.CommonViewHolder;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankSearchFragment extends BaseTFragment<GankSearchPresenter> implements GankSearchContract.View {

    public static final String TAG = "GankSearchFragment";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.ev_search)
    EditText mEditTextSearch;

    private CommonAdapter mCommonAdapter;
    private List<SearchBean> mSearchList = new ArrayList();
    private int page = 1;

    public static GankSearchFragment newInstance() {
        Bundle args = new Bundle();
        GankSearchFragment fragment = new GankSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCommonAdapter = new CommonAdapter<SearchBean>(getActivity(), R.layout.item_gankdaily_content, mSearchList) {
            @Override
            public void convert(CommonViewHolder holder, final SearchBean bean) {
                holder.setText(R.id.tv_content, bean.desc);
                holder.setOnClickListener(R.id.cl_daily, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.GANK_URL, bean.url);
                        readyGo(WebViewActivity.class, bundle);
                    }
                });
                holder.setText(R.id.tv_author, bean.who);
                holder.setText(R.id.tv_time, bean.publishedAt.split("T")[0]);
            }
        };
        mRecyclerView.setAdapter(mCommonAdapter);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //pull down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchList.clear();
                page = 1;
                mPresenter.initData(mEditTextSearch.getText().toString().trim(), page);
            }
        });
        mRecyclerView.addOnScrollListener(new GankSearchFragment.onLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                showLoadingDialog();
                mPresenter.initData(mEditTextSearch.getText().toString().trim(), ++page);
            }
        });

        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchList.clear();
                    page = 1;
                    mPresenter.initData(mEditTextSearch.getText().toString().trim(), page);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    public void showSearchList(SearchGank search) {
        swipeRefreshLayout.setRefreshing(false);
        dismissLoadingDialog();
        mSearchList.addAll(search.results);
        mCommonAdapter.update(mSearchList);
    }

    abstract class onLoadMoreListener extends RecyclerView.OnScrollListener {
        private int countItem;
        private int lastItem;
        private boolean isScolled = false;
        private RecyclerView.LayoutManager layoutManager;

        protected abstract void onLoading(int countItem, int lastItem);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_DRAGGING || newState == SCROLL_STATE_SETTLING) {
                isScolled = true;
            } else {
                isScolled = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                layoutManager = recyclerView.getLayoutManager();
                countItem = layoutManager.getItemCount();
                lastItem = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
            if (isScolled && countItem != lastItem && lastItem == countItem - 1) {
                onLoading(countItem, lastItem);
            }
        }
    }
}
