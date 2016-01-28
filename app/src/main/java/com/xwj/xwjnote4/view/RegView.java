package com.xwj.xwjnote4.view;

/**
 * Created by xwjsd on 2016-01-07.
 */
public interface RegView {
    String getUsername();

    String getPassword();

    String getPassword2();

    void finishActivity();

    void showProgress();

    void hideProgress();
}
