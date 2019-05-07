package com.androidwind.gank.gankgirl;

import android.os.Bundle;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.constant.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankGirlFragment extends BaseTFragment {

    @BindView(R.id.iv_girl)
    PhotoView mImageViewGirl;

    private Bundle mBundle;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        showLoadingDialog();
        mBundle = getActivity().getIntent().getExtras();
        String url = mBundle.getString(Constants.GANK_URL);
        Glide.with(mContext)
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //加载完成后的处理
                        mImageViewGirl.setImageDrawable(resource);
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_girl;
    }
}
