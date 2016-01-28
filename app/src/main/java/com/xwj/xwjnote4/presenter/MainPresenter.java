package com.xwj.xwjnote4.presenter;

import android.view.View;

/**
 * Created by xwjsd on 2016-01-05.
 */
public interface MainPresenter extends BasePresenter {
    /**
     * 通过id切换Fragment
     *
     * @param id Menu的id
     */
    void switchFragmentById(int id);

    void handleSelectMenuItem(int itemId);

    void handleClick(View v);

    void getCurrentUser();
}
