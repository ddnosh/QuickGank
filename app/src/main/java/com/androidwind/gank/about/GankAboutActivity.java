package com.androidwind.gank.about;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.androidwind.gank.R;
import com.androidwind.gank.base.BaseTActivity;
import com.androidwind.gank.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;
import com.androidwind.androidquick.util.AppUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankAboutActivity extends BaseTActivity {

    @BindView(R.id.tv_version_value)
    TextView mVersion;
    @BindView(R.id.tv_github_value)
    TextView mGitHub;
    @BindView(R.id.tv_desc_value)
    TextView mDesc;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mVersion.setText(AppUtil.getVersionName(this));
        mDesc.setText(getString());
        //设置超链接可点击
        mDesc.setMovementMethod(LinkMovementMethod.getInstance());
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

    private SpannableString getString() {
        final SpannableString spannableString = new SpannableString(
                "一个基于AndroidQuick快速开发库的Gank.io客户端");
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),
                4, 16,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),
                4, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("URL", "https://github.com/ddnosh/AndroidQuick");
                readyGo(WebViewActivity.class, bundle);
            }
        }, 4, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
