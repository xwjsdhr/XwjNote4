package com.xwj.xwjnote4.view;

import android.content.Intent;

import com.xwj.xwjnote4.model.Note;

/**
 * item的视图层接口
 * Created by xwjsd on 2016-01-06.
 */
public interface ItemView {

    /**
     * 显示PopupMenu。
     */
    void showPopupMenu();

    /**
     * 获取当前位置的Note对象
     *
     * @return
     */
    Note getNote();

    /**
     * 删除当前的Item
     *
     * @param note
     */
    void removeItem(Note note);

    /**
     * 显示内容
     */
    void showContent();

    /**
     * 隐藏内容
     */
    void hideContent();

    /**
     * 判断当前内容文本是否选中
     *
     * @return
     */
    boolean contentIsShown();


    void changeShowText(int resId, int drawableId);

    /**
     * 选中或者取消选中checkBox
     */
    void toggleCheckBox();

    /**
     * 启动activity
     *
     * @param intent
     */
    void itemStartActivity(Intent intent);

    /**
     * 判断当前是否被展开
     *
     * @return
     */
    boolean isSwiped();

    /**
     * 关闭侧滑菜单
     */
    void closeSwipeLayout();

    void toggleBtnText();

    void closeSwipeLayoutWithoutSmooth();

    void bindView(Note note);

    void hideShowMore();

    void showShowMore();


    void toDetailFragment(Note note);
}
