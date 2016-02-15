package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.MyUser;
import com.xwj.xwjnote4.presenter.MainPresenter;
import com.xwj.xwjnote4.view.MainView;
import com.xwj.xwjnote4.view.impl.AboutActivity;
import com.xwj.xwjnote4.view.impl.LoginActivity;
import com.xwj.xwjnote4.view.impl.SettingActivity;

/**
 * MainPresenter的实现类。
 * Created by xwjsd on 2016-01-05.
 */
public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private Context mContext;
    private MainView mMainView;

    public MainPresenterImpl(Context context, MainView mainView) {
        mContext = context;
        mMainView = mainView;
    }

    @Override
    public void switchFragmentById(int id) {
        switch (id) {
            case R.id.action_home:
                mMainView.toHomeFragment();
                mMainView.closeDrawer();
                mMainView.showFloatButton();
                break;
            case R.id.action_favorite:
                mMainView.toFavoriteFragment();
                mMainView.closeDrawer();
                mMainView.hideFloatButton();
                break;
            case R.id.action_trash:
                mMainView.toTrashFragment();
                mMainView.closeDrawer();
                mMainView.hideFloatButton();
                break;
        }
    }

    @Override
    public void handleSelectMenuItem(int itemId) {
        Intent intent = null;
        switch (itemId) {
            case R.id.action_about:
                intent = new Intent(mContext, AboutActivity.class);
                mMainView.mainStartActivity(intent);
                break;
            case R.id.action_setting:
                intent = new Intent(mContext, SettingActivity.class);
                mMainView.mainStartActivity(intent);
                break;
            case R.id.action_exit:
                mMainView.mainFinishActivity();
        }
    }

    @Override
    public void handleClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_user_icon:
                intent = new Intent(mContext, LoginActivity.class);
                mMainView.mainStartActivity(intent);
                break;
            case R.id.fab:
//                intent = new Intent(mContext, NoteDetailActivity.class);
//                mMainView.mainStartActivity(intent);
                mMainView.toAddFragment();
                break;
        }
    }

    @Override
    public void getCurrentUser() {
        MyUser currentUser = MyUser.getCurrentUser(mContext, MyUser.class);
        if (currentUser != null) {
            mMainView.bindUser(currentUser);
        }
    }

    @Override
    public void configView(SharedPreferences sharedPreferences) {

    }
}
