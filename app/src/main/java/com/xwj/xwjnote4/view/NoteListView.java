package com.xwj.xwjnote4.view;

import android.support.annotation.Nullable;

import com.xwj.xwjnote4.model.Note;

import java.util.ArrayList;

/**
 * list列表的视图层接口
 * Created by xwjsd on 2016-01-05.
 */
public interface NoteListView {
    /**
     * 与ListView或者是RecyclerView绑定数据。
     *
     * @param list
     */
    void bindData(ArrayList<Note> list);

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 显示list为空时的视图
     * @param text
     */
    void showEmptyView(@Nullable String text);
    /**
     * 隐藏list为空时的视图
     * @param text
     */
    void hideEmptyView(@Nullable String text);

    String getSearchText();

    /**
     * 刷新列表
     * @param note
     */
    void refreshList(Note note);

    /**
     * 更新列表
     * @param note
     */
    void refreshUpdate(Note note);


}
