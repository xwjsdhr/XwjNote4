package com.xwj.xwjnote4.presenter;

import com.xwj.xwjnote4.model.Note;

/**
 * 详情页面的业务逻辑类。
 * Created by xwjsd on 2016-01-05.
 */
public interface NotePresenter extends BasePresenter {
    /**
     * 保存便签
     */
    void saveNote();

    /**
     * 获取Intent中的Note对象
     */
    Note getNoteByIntent();

    void handleItemClick(int itemId);

    void deleteNote(Note note);

    void registerBus();

    void unRegisterBus();
}
