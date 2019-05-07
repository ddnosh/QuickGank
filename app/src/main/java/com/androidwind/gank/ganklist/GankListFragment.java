package com.androidwind.gank.ganklist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.bean.entity.GankBean;
import com.androidwind.gank.bean.model.SimpleGank;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.gankdaily.GankDailyActivity;
import com.androidwind.gank.gankgirl.GankGirlFragment;
import com.androidwind.gank.tool.DateUtils;
import com.androidwind.gank.tool.GlideUtils;
import com.androidwind.gank.webview.WebViewActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import la.xiong.androidquick.ui.adapter.CommonAdapter;
import la.xiong.androidquick.ui.adapter.CommonViewHolder;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankListFragment extends BaseTFragment<GankListPresenter> implements GankListContract.View{

    public static final String TAG = "GankListFragment";

    @BindView(R.id.rv_adapter)
    RecyclerView mRecyclerView;
    private CommonAdapter mCommonAdapter;
    private RefreshLayout refreshLayout;
    private List<GankBean> mCatalogueList = new ArrayList();
    private int page = 1;

    public static GankListFragment newInstance() {
        Bundle args = new Bundle();
        GankListFragment fragment = new GankListFragment();
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
        mCommonAdapter = new CommonAdapter<GankBean>(getActivity(), R.layout.item_ganklist, mCatalogueList) {
            @Override
            public void convert(CommonViewHolder holder, final GankBean bean) {
//                holder.setImageResourceWithGlide(R.id.iv_girl, bean.url);
                GlideUtils.loadImageView(bean.url, holder.getView(R.id.iv_girl));
                holder.setText(R.id.tv_date, DateUtils.toDateString2(bean.publishedAt));
                holder.setOnClickListener(R.id.cl_item, v -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bean.publishedAt);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.GANK_DATE, calendar);
                    readyGo(GankDailyActivity.class, bundle);
                });
                holder.setOnClickListener(R.id.iv_girl, v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GANK_URL, bean.url);
                    readyGo(GankGirlFragment.class, bundle);
                });
                holder.setText(R.id.tv_rest, bean.desc);
                holder.setOnClickListener(R.id.tv_rest, v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GANK_URL, bean.playUrl);
                    readyGo(WebViewActivity.class, bundle);
                });
            }
        };
        mRecyclerView.setAdapter(mCommonAdapter);

        refreshLayout = getActivity().findViewById(R.id.smartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mCatalogueList.clear();
                page = 1;
                mPresenter.initData(page);
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPresenter.initData(++page);
                refreshLayout.finishLoadMore();
            }
        });

        showLoadingDialog();
        mPresenter.initData(page);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_list;
    }

    @Override
    public void showGirlList(SimpleGank girl) {
        dismissLoadingDialog();
        mCatalogueList.addAll(girl.results);
        mCommonAdapter.update(mCatalogueList);
    }
}
