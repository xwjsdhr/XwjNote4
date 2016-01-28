package com.xwj.xwjnote4.Iterator;

import com.xwj.xwjnote4.model.Note;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by xwjsd on 2016-01-05.
 */
public interface NoteIterator {
    void getNotes(String type);

    void searchNotes(String title);

    void recoverNotes(FindListener<Note> listener);

    void getNotes2(String type);

    void getNoteCount(String type);
}
