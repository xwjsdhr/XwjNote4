package com.xwj.xwjnote4.view;

/**
 * 登陆界面的视图层的接口
 * Created by xwjsd on 2016-01-07.
 */
public interface LoginView {
    /**
     * 获取用户名
     *
     * @return
     */
    String getUsername();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    void finishActivity();

    void showProgress();

    void hideProgress();
}
