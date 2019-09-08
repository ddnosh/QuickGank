package com.androidwind.gank.gankdaily;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTActivity;
import com.androidwind.gank.bean.entity.GankBean;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.tool.DateUtil;
import com.androidwind.gank.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import com.androidwind.androidquick.ui.adapter.CommonViewHolder;
import com.androidwind.androidquick.ui.adapter.MultiItemCommonAdapter;
import com.androidwind.androidquick.ui.adapter.MultiItemTypeSupport;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankDailyActivity extends BaseTActivity<GankDailyPresenter> implements GankDailyContract.View {

    @BindView(R.id.rv_adapter)
    RecyclerView mRecyclerView;

    private DailyAdapter mDailyAdapter;
    private Bundle mBundle;
    private List<GankBean> gankBeanList = new ArrayList();

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mBundle = extras;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mDailyAdapter = new DailyAdapter(this, gankBeanList);
        mRecyclerView.setAdapter(mDailyAdapter);

        Calendar calendar = (Calendar) mBundle.getSerializable(Constants.GANK_DATE);
        mPresenter.getDaily(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void showDailyList(List<GankBean> beans) {
        gankBeanList.addAll(beans);
        mDailyAdapter.notifyDataSetChanged();
    }

    class DailyAdapter extends MultiItemCommonAdapter<GankBean> {

        public DailyAdapter(Context context, List<GankBean> datas) {
            super(context, datas, new MultiItemTypeSupport<GankBean>() {
                @Override
                public int getLayoutId(int itemType) {
                    if (itemType == 1) {
                        return R.layout.item_gankdaily_title;
                    } else {
                        return R.layout.item_gankdaily_content;
                    }
                }

                @Override
                public int getItemViewType(int position, GankBean bean) {
                    if (bean.source.equals(Constants.GANK_TYPE)) {
                        return 1;
                    }
                    return 2;
                }

            });

        }

        @Override
        public void convert(CommonViewHolder holder, GankBean bean) {
            if (holder.getItemViewType() == 1) {
                holder.setText(R.id.tv_title, bean.type);
            } else {
                holder.setText(R.id.tv_content, bean.desc);
                holder.setOnClickListener(R.id.tv_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.GANK_URL, bean.url);
                        readyGo(WebViewActivity.class, bundle);
                    }
                });
                holder.setText(R.id.tv_author, bean.who);
                holder.setText(R.id.tv_time, DateUtil.toDateString2(bean.publishedAt));
            }
        }
    }
}
