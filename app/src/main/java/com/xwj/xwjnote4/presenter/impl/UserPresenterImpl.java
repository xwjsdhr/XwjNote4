package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.xwj.xwjnote4.Iterator.OnUserListener;
import com.xwj.xwjnote4.Iterator.UserInterator;
import com.xwj.xwjnote4.Iterator.impl.UserInteratorImpl;
import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.presenter.UserPresenter;
import com.xwj.xwjnote4.view.LoginView;
import com.xwj.xwjnote4.view.RegView;

/**
 * Created by xwjsd on 2016-01-07.
 */
public class UserPresenterImpl implements UserPresenter, OnUserListener {

    private Context mContext;
    private LoginView mLoginView;
    private RegView mRegView;
    private UserInterator mUserInterator;


    public UserPresenterImpl(Context context, LoginView loginView) {
        mContext = context;
        mLoginView = loginView;
        mUserInterator = new UserInteratorImpl(context);
    }

    public UserPresenterImpl(Context context, RegView regView) {
        mContext = context;
        mRegView = regView;
        mUserInterator = new UserInteratorImpl(context);
    }


    @Override
    public void login() {
        String username = mLoginView.getUsername();
        String password = mLoginView.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mLoginView.showProgress();
        mUserInterator.login(username, password, this);
    }

    @Override
    public void register() {
        String username = mRegView.getUsername();
        String password = mRegView.getPassword();
        String password2 = mRegView.getPassword2();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(password2)) {
            Toast.makeText(mContext, "用户名密码不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (!TextUtils.equals(password, password2)) {
            Toast.makeText(mContext, "密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        mRegView.showProgress();
        mUserInterator.register(username, password, password2, this);
    }

    @Override
    public void handleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_ok:
                this.login();
                break;
            case R.id.btn_login_cancel:
                this.loginCancel();
                break;
            case R.id.btn_reg_ok:
                this.register();
                break;
            case R.id.btn_reg_cancel:
                this.registerCancel();
                break;
        }
    }

    @Override
    public void registerCancel() {
        mRegView.finishActivity();
    }

    @Override
    public void loginCancel() {
        mLoginView.finishActivity();
    }

    @Override
    public void onLoginSuccess() {
        mLoginView.hideProgress();
        Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
        mLoginView.finishActivity();
    }

    @Override
    public void onLoginFailed(String msg) {
        mLoginView.hideProgress();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        mLoginView.finishActivity();
    }

    @Override
    public void onRegSuccess() {
        mRegView.hideProgress();
        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
        mRegView.finishActivity();
    }

    @Override
    public void onRegFailed(String msg) {
        mRegView.hideProgress();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        mRegView.finishActivity();
    }
}
