package com.xwj.xwjnote4.presenter;

import com.xwj.xwjnote4.model.Note;

/**
 * List界面的业务逻辑类的接口
 * Created by xwjsd on 2016-01-05.
 */
public interface NoteListPresenter  extends BasePresenter{
    /**
     * 通过指定类型的的Note集合。
     *
     * @param type 指定类型
     */
    void getNotesByType(String type);

    void searchNotesByTitle(String title);

    void addNote(Note note);

    void updateNote(Note note);

    void recover();
}
