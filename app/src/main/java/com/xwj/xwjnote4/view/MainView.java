package com.xwj.xwjnote4.view;

import android.content.Intent;

import com.xwj.xwjnote4.model.MyUser;

/**
 * 主界面的视图层接口
 * Created by xwjsd on 2016-01-05.
 */
public interface MainView {

    /**
     * 添加主界面的Fragment。
     */
    void toHomeFragment();

    /**
     * 添加收藏的Fragment。
     */
    void toFavoriteFragment();

    /**
     * 添加回收站的Fragment。
     */
    void toTrashFragment();

    void toSecurityFragment();

    /**
     * 关闭抽屉。
     */
    void closeDrawer();

    /**
     * 打开抽屉式布局。
     */
    void openDrawer();

    /**
     * 设置Toolbar的标题。
     */
    void setToolbarTitle(String title);

    /**
     * 设置用户头像
     *
     * @param imageUrl
     */
    void setUserIcon(String imageUrl);

    /**
     * 设置用户名
     *
     * @param userName
     */
    void setUserName(String userName);

    /**
     * 改变actionbutton的图标
     *
     * @param resId
     */
    void changeFloatButtonIcon(int resId);

    /**
     * 隐藏按钮
     */
    void hideFloatButton();

    /**
     * 显示按钮
     */
    void showFloatButton();

    /**
     * @param user
     */
    void bindUser(MyUser user);

    /**
     * 启动activity
     *
     * @param intent
     */
    void mainStartActivity(Intent intent);

    /**
     * 关闭当前activity
     */
    void mainFinishActivity();

    void setToolbarTitle(int count);

    void toAddFragment();

    void disableDrawer();

    void enableDrawer();

}
