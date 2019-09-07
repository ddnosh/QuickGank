package com.androidwind.gank.gankgirl;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.constant.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
                .listener(new RequestListener<Drawable>() {
                    /**
                     * 加载失败
                     * @return false 未消费，继续走into(ImageView)
                     *         true 已消费，不再继续走into(ImageView)
                     */
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    /**
                     * 加载成功
                     * @return false 未消费，继续走into(ImageView)
                     *         true 已消费，不再继续走into(ImageView)
                     */
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //加载完成后的处理
                        mImageViewGirl.setImageDrawable(resource);
                        dismissLoadingDialog();
                        return false;
                    }
                })
                .into(mImageViewGirl);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_girl;
    }
}
