package com.xwj.xwjnote4.presenter;

import android.view.View;
import android.widget.PopupMenu;

import com.xwj.xwjnote4.model.Note;

/**
 * Item的逻辑交互类的接口。
 * Created by xwjsd on 2016-01-06.
 */
public interface ItemPresenter extends BasePresenter {

    /**
     * 处理相对应的itemId的操作。
     *
     * @param itemId
     */
    void handlePopupMenu(PopupMenu menu, int itemId);

    /**
     * 收藏和取消收藏
     *
     * @param note
     * @param isChecked
     */
    void favoToggle(Note note, boolean isChecked);

    /**
     * 隐藏或显示内容
     */
    void toggleContent();

    /**
     * 处理点击事件
     *
     * @param view
     */
    void handleClick(View view);

    boolean handleLongClick(View view);
}
