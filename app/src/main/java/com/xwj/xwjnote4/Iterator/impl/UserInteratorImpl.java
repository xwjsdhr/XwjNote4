package com.xwj.xwjnote4.Iterator.impl;

import android.content.Context;

import com.xwj.xwjnote4.Iterator.OnUserListener;
import com.xwj.xwjnote4.Iterator.UserInterator;
import com.xwj.xwjnote4.model.MyUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xwjsd on 2016-01-07.
 */
public class UserInteratorImpl implements UserInterator {

    private Context mContext;

    public UserInteratorImpl(Context context) {
        mContext = context;
    }

    @Override
    public void login(String username, String password, final OnUserListener listener) {

        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.onLoginSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onLoginFailed(s);
            }
        });
    }

    @Override
    public void register(String username, String password, String password2, final OnUserListener listener) {


        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.onRegSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onRegFailed(s);
            }
        });
    }
}
