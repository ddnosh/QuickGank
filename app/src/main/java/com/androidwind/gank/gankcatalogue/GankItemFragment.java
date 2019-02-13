package com.androidwind.gank.gankcatalogue;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.bean.entity.GankBean;
import com.androidwind.gank.bean.model.SimpleGank;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.tool.DateUtils;
import com.androidwind.gank.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import la.xiong.androidquick.ui.adapter.CommonAdapter;
import la.xiong.androidquick.ui.adapter.CommonViewHolder;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankItemFragment extends BaseTFragment<GankItemPresenter> implements GankItemContract.View{

    public static final String TAG = "GankItemFragment";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_adapter)
    RecyclerView mRecyclerView;
    private CommonAdapter mCommonAdapter;
    private List<GankBean> mCatalogueList = new ArrayList();
    private int page = 1;

    public static GankItemFragment newInstance(String catalogue) {
        Bundle args = new Bundle();
        args.putString(Constants.GANK_TYPE, catalogue);
        GankItemFragment fragment = new GankItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        String catalogue = (String)getArguments().get(Constants.GANK_TYPE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCommonAdapter = new CommonAdapter<GankBean>(getActivity(), R.layout.item_gankdaily_content, mCatalogueList) {
            @Override
            public void convert(CommonViewHolder holder, final GankBean bean) {
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
                holder.setText(R.id.tv_time, DateUtils.toDateString2(bean.publishedAt));
            }
        };
        mRecyclerView.setAdapter(mCommonAdapter);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //pull down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCatalogueList.clear();
                page = 1;
                mPresenter.initData(catalogue, page);
            }
        });
        mRecyclerView.addOnScrollListener(new onLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                showLoadingDialog();
                mPresenter.initData(catalogue, ++page);
            }
        });

        showLoadingDialog();
        mPresenter.initData(catalogue, page);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_item;
    }

    @Override
    public void showItemData(SimpleGank simpleGank) {
        swipeRefreshLayout.setRefreshing(false);
        dismissLoadingDialog();
        mCatalogueList.addAll(simpleGank.results);
        mCommonAdapter.update(mCatalogueList);
        mCommonAdapter.notifyDataSetChanged();
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
