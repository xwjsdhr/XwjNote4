package com.xwj.xwjnote4.presenter;

import android.view.View;

/**
 * Created by xwjsd on 2016-01-07.
 */
public interface UserPresenter {

    void login();

    void loginCancel();

    void register();

    void handleClick(View v);

    void registerCancel();

}
