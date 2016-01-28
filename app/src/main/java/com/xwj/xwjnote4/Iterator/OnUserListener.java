package com.xwj.xwjnote4.Iterator;

/**
 * Created by xwjsd on 2016-01-07.
 */
public interface OnUserListener {
    void onLoginSuccess();

    void onLoginFailed(String msg);

    void onRegSuccess();

    void onRegFailed(String msg);
}
