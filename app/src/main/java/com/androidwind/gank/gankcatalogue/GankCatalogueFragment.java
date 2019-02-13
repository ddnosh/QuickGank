package com.androidwind.gank.gankcatalogue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.androidwind.gank.MyApplication;
import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankCatalogueFragment extends BaseTFragment {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.viewpagertab)
    SmartTabLayout mSmartTabLayout;

    List<String> tabs;

    public static GankCatalogueFragment newInstance() {
        Bundle args = new Bundle();
        GankCatalogueFragment fragment = new GankCatalogueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//                getSupportFragmentManager(), FragmentPagerItems.with(mContext)
//                .add("Android", GankItemFragment.class, new Bundler().putString(Constants.GANK_TYPE, "Android").get())
//                .add("iOS", GankItemFragment.class, new Bundler().putString(Constants.GANK_TYPE, "iOS").get())
//                .add("前端", GankItemFragment.class, new Bundler().putString(Constants.GANK_TYPE, "前端").get())
//                .create());

        List<Fragment> fragmentList = new ArrayList();
        fragmentList.add(GankItemFragment.newInstance("Android"));
        fragmentList.add(GankItemFragment.newInstance("iOS"));
        fragmentList.add(GankItemFragment.newInstance("前端"));
        fragmentList.add(GankItemFragment.newInstance("瞎推荐"));
        fragmentList.add(GankItemFragment.newInstance("拓展资源"));
        fragmentList.add(GankItemFragment.newInstance("App"));
        fragmentList.add(GankItemFragment.newInstance("休息视频"));

        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm, fragmentList);

        tabs = Arrays.asList(MyApplication.getInstance().getResources()
                .getStringArray(R.array.subtabs));
        mViewPager.setOffscreenPageLimit(tabs.size());
        mViewPager.setAdapter(pagerAdapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_catalogue;
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fragmentmanager;
        private List<Fragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentmanager = fm;
            this.fragmentList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
