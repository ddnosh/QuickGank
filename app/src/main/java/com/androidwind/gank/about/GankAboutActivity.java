package com.androidwind.gank.about;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTActivity;
import com.androidwind.gank.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;
import la.xiong.androidquick.tool.AppUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankAboutActivity extends BaseTActivity {

    @BindView(R.id.tv_version_value)
    TextView mVersion;
    @BindView(R.id.tv_github_value)
    TextView mGitHub;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mVersion.setText(AppUtil.getVersionName(this));
    }

    @OnClick(R.id.tv_github_value)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_github_value:
                Bundle bundle = new Bundle();
                bundle.putString("URL", mGitHub.getText().toString());
                readyGo(WebViewActivity.class, bundle);
                break;
        }
    }
}
