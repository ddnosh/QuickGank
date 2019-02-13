package com.androidwind.gank.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.androidwind.gank.R;
import com.androidwind.gank.about.GankAboutActivity;
import com.androidwind.gank.base.BaseTActivity;
import com.androidwind.gank.base.BaseTFragment;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.gankcatalogue.GankCatalogueFragment;
import com.androidwind.gank.ganklist.GankListFragment;

import java.util.List;

import butterknife.BindView;
import la.xiong.androidquick.tool.LogUtil;
import la.xiong.androidquick.tool.ToastUtil;
import la.xiong.androidquick.ui.base.QuickActivity;
import la.xiong.androidquick.ui.dialog.dialogactivity.CommonDialog;
import la.xiong.androidquick.ui.permission.EasyPermissions;
import la.xiong.androidquick.ui.view.BottomBar;
import la.xiong.androidquick.ui.view.BottomBarTab;
import la.xiong.androidquick.ui.view.CommonToolBar;

public class MainActivity extends BaseTActivity {

    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.common_tool_bar)
    CommonToolBar mCommonToolBar;

    private BaseTFragment[] mFragments = new BaseTFragment[3];

    private String deniedPermsString;

    private long DOUBLE_CLICK_TIME = 0L;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        permissionsCheck();
//        GankListFragment dailyFragment = findFragment(GankListFragment.class);
        if (savedInstanceState == null) {
            mFragments[0] = GankListFragment.newInstance();
            mFragments[1] = GankCatalogueFragment.newInstance();
//            mFragments[2] = GankListFragment.newInstance();
            getSupportDelegate().loadMultipleRootFragment(R.id.container, 0,
                    mFragments[0],
                    mFragments[1]);
//                    mFragments[2]);
        } else {
            mFragments[0] = findFragment(GankListFragment.class);
            mFragments[1] = findFragment(GankCatalogueFragment.class);
//            mFragments[2] = findFragment(GankListFragment.class);
        }
        mBottomBar.addItem(new BottomBarTab(mContext, R.mipmap.ic_launcher, "日报"))
                .addItem(new BottomBarTab(mContext, R.mipmap.ic_launcher, "目录"));
//                .addItem(new BottomBarTab(mContext, R.mipmap.ic_launcher, "搜索"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        mCommonToolBar.setOnTopBarClickListener(new CommonToolBar.OnToolBarClickListener(){

            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                readyGo(GankAboutActivity.class);
            }
        });
    }

    private void permissionsCheck() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        performCodeWithPermission(1, Constants.RC_PERMISSION_PERMISSION_ACTIVITY, perms, new QuickActivity.PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
                LogUtil.d(TAG, "allPerms:" + allPerms.toString());
//                ToastUtil.showToast("Granted");
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                LogUtil.d(TAG, "deniedPerms:" + deniedPerms.toString());
                if (hasPermanentlyDenied) {
                    StringBuilder denied = new StringBuilder();
                    if (deniedPerms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE) || deniedPerms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        denied.append("存储文件使用");
                    }
                    deniedPermsString = denied.toString();
                    EasyPermissions.goSettingsPermissions(MainActivity.this, 2, Constants.RC_PERMISSION_PERMISSION_ACTIVITY, Constants.RC_PERMISSION_BASE);
                }
            }

            @Override
            public void showDialog(int dialogType, final EasyPermissions.DialogCallback callback) {
                switch (dialogType) {
                    case 1:
                        getDialogBuilder(mContext).
                                setTitle(getString(R.string.app_name)).
                                setMessage(getString(R.string.dialog_storage_permission)).
                                setPositiveButton("OK").
                                setBtnClickCallBack(new CommonDialog.DialogBtnCallBack() {
                                    @Override
                                    public void onDialogButClick(boolean isConfirm) {
                                        if (isConfirm) {
                                            callback.onGranted();
                                        }
                                    }
                                }).show().setCancelable(false);
                        break;
                    case 2:
                        getDialogBuilder(mContext).
                                setTitle(getString(R.string.app_name)).
                                setMessage(getString(R.string.dialog_rationale_ask_again, deniedPermsString)).
                                setPositiveButton("Go to setting").
                                setBtnClickCallBack(new CommonDialog.DialogBtnCallBack() {
                                    @Override
                                    public void onDialogButClick(boolean isConfirm) {
                                        if (isConfirm) {
                                            callback.onGranted();
                                        }
                                    }
                                }).show().setCancelable(false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "requestCode:" + requestCode);
        if (requestCode == Constants.RC_PERMISSION_BASE) {
            permissionsCheck();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - DOUBLE_CLICK_TIME) > 2000) {
                ToastUtil.showToast("再按一次退出");
                DOUBLE_CLICK_TIME = currentTime;
            } else {
                System.gc();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
