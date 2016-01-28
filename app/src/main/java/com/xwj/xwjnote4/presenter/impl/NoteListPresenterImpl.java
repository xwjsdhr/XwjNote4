package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.xwj.xwjnote4.Iterator.NoteIterator;
import com.xwj.xwjnote4.Iterator.impl.NoteIteratorImpl;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.model.impl.NoteModelImpl;
import com.xwj.xwjnote4.presenter.NoteListPresenter;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.view.NoteListView;
import com.xwj.xwjnote4.view.impl.HomeNoteFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * NoteListPresenter的实现类。
 * Created by xwjsd on 2016-01-05.
 */
public class NoteListPresenterImpl extends FindListener<Note> implements NoteListPresenter {
    private static final String TAG = NoteListPresenterImpl.class.getSimpleName();
    private Context mContext;
    private NoteListView mNoteView;
    private NoteIterator mNoteIterator;
    private NoteModel mNoteModel;
    private EventBus mEventBus;


    public NoteListPresenterImpl(Context context, NoteListView noteView) {
        mContext = context;
        mNoteView = noteView;
        mNoteIterator = new NoteIteratorImpl(context);
        mNoteModel = new NoteModelImpl(context);
        mEventBus = EventBus.getDefault();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }

    @Override
    public void getNotesByType(String type) {
        //mNoteView.showProgress();
        mNoteIterator.getNotes(type);
        mNoteIterator.getNoteCount(type);

    }

    @Override
    public void searchNotesByTitle(String title) {
        mNoteView.showProgress();
        mNoteIterator.searchNotes(title);
    }

    @Override
    public void addNote(Note note) {
        mNoteModel.addNote(note);
        mNoteView.refreshList(note);
        mEventBus.post(mNoteModel.getNotesCount(NoteUtil.NOTE_TYPE_NORMAL));
    }

    @Override
    public void updateNote(Note note) {
        mNoteModel.updateNote(note);
        mNoteView.refreshUpdate(note);
    }

    @Override
    public void recover() {
        mNoteIterator.recoverNotes(this);
    }

    @Override
    public void onSuccess(List<Note> list) {

    }

    @Override
    public void onError(int i, String s) {

    }

    public void onEventMainThread(ArrayList<Note> list) {
        Log.e(TAG, Thread.currentThread().getName());
        if (mNoteView.getClass().getName().equals(HomeNoteFragment.class.getName())) {
            mNoteView.bindData(list);
            mNoteView.hideProgress();
            mNoteView.hideEmptyView("a");
            if (mEventBus.isRegistered(this)) {
                mEventBus.unregister(this);
            }
        }
    }


    @Override
    public void configView(SharedPreferences sharedPreferences) {

    }
}
