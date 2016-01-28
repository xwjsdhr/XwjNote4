package com.xwj.xwjnote4.Iterator.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xwj.xwjnote4.Iterator.NoteIterator;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.model.impl.NoteModelImpl;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.utils.PreferenceUtils;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * 511535438
 * 业务交互类的实现
 * Created by xwjsd on 2016-01-05.
 */
public class NoteIteratorImpl implements NoteIterator {
    private static final String TAG = NoteIteratorImpl.class.getSimpleName();
    private Context mContext;

    private NoteModel mNoteModel;
    private EventBus mEventBus;

    public NoteIteratorImpl(Context context) {
        mContext = context;
        mNoteModel = new NoteModelImpl(context);
        mEventBus = EventBus.getDefault();
    }


    @Override
    public void getNotes(final String type) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Note> notes = null;
                if (NoteUtil.NOTE_TYPE_NORMAL.equals(type)) {
                    notes = mNoteModel.getNotes(NoteUtil.NOTE_TYPE_NORMAL);
                } else if (NoteUtil.NOTE_TYPE_TRASH.equals(type)) {
                    notes = mNoteModel.getNotes(NoteUtil.NOTE_TYPE_NORMAL);
                } else if (NoteUtil.NOTE_TYPE_SECURITY.equals(type)) {
                    notes = mNoteModel.getNotes(NoteUtil.NOTE_TYPE_NORMAL);
                } else {
                    notes = mNoteModel.getNotes(NoteUtil.NOTE_TYPE_NORMAL);
                }
                Log.e(TAG, Thread.currentThread().getName());
                mEventBus.post(notes);
            }
        }).start();

    }

    @Override
    public void searchNotes(final String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(title)) {
                    ArrayList<Note> list = mNoteModel.getNotesByTitle(title, false);
                    mEventBus.post(list);
                } else {
                    ArrayList<Note> notes = mNoteModel.getAllNotes();
                    mEventBus.post(notes);
                }
            }
        }).start();
    }


    /**
     * 恢复便签
     *
     * @param listener
     */
    @Override
    public void recoverNotes(FindListener<Note> listener) {
        BmobQuery<Note> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("hasSync", true);
        bmobQuery.addWhereEqualTo("userAuth", "aaaa");
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(mContext, listener);
        PreferenceUtils.clear(mContext, ConstantUtils.DEL_NOTE_LIST);
    }

    @Override
    public void getNotes2(String type) {

    }

    @Override
    public void getNoteCount(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int counts = mNoteModel.getNotesCount(type);
                mEventBus.post(counts);
            }
        }).start();
    }
}
