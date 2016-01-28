package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.model.UpdateNote;
import com.xwj.xwjnote4.model.impl.NoteModelImpl;
import com.xwj.xwjnote4.presenter.NotePresenter;
import com.xwj.xwjnote4.utils.CommonUtils;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.view.NoteDetailView;

import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * NotePresenter的实现类。
 * Created by xwjsd on 2016-01-05.
 */
public class NotePresenterImpl implements NotePresenter {
    private NoteDetailView mNoteDetailView;
    private Note mNote;
    private NoteModel mNoteModel;
    private EventBus mEventBus;

    public NotePresenterImpl(Context context, NoteDetailView noteDetailView) {
        mNoteDetailView = noteDetailView;
        mNoteModel = new NoteModelImpl(context);
        mEventBus = EventBus.getDefault();
    }

    /**
     * 保存便签
     */
    @Override
    public void saveNote() {
        if (mNoteDetailView.isEditTextUnFocused() || TextUtils.isEmpty(mNoteDetailView.getNoteTitle()) ||
                TextUtils.isEmpty(mNoteDetailView.getNoteContent())) {
            mNoteDetailView.finishActivity();
        } else {
            String title = mNoteDetailView.getNoteTitle();
            String content = mNoteDetailView.getNoteContent();

            if (mNote != null) {

                mNote.setTitle(title);
                mNote.setContent(content);
                mNote.setLastModifiedTime(new Date());
                UpdateNote updateNote = new UpdateNote();
                updateNote.setNote(mNote);
                mEventBus.post(updateNote);
            } else {
                mNote = new Note();
                mNote.setTitle(title);
                mNote.setContent(content);
                mNote.setId(CommonUtils.getGuid());
                mNote.setCreateTime(new Date());
                mNote.setLastModifiedTime(new Date());
                mNote.setNoteType(NoteUtil.NOTE_TYPE_NORMAL);
                mNote.setUserAuth("");
                mNote.setLayoutType(ConstantUtils.LAYOUT_TYPE_ORDINARY);
                mNote.setHasSync(false);
                mNote.setFavorite(false);
                mEventBus.post(mNote);
            }
            mNoteDetailView.finishActivity();
        }

    }

    /**
     * 获取Intent中的note对象
     *
     * @return
     */
    @Override
    public Note getNoteByIntent() {
        mNote = mNoteDetailView.detailGetIntent();
        if (mNote != null) {
            mNoteDetailView.bindNote(mNote);
            return mNote;
        }
        return null;
    }

    /**
     * 处理option item 点击。
     *
     * @param itemId
     */
    @Override
    public void handleItemClick(int itemId) {
        switch (itemId) {
            case R.id.action_truncate:
                mNoteDetailView.showdSnackbar("确定删除");
                break;
            case R.id.action_save:
                if (mNote != null) {
                    String title = mNoteDetailView.getNoteTitle();
                    String content = mNoteDetailView.getNoteContent();
                    mNote.setTitle(title);
                    mNote.setContent(content);
                    mNote.setLastModifiedTime(new Date());
                    UpdateNote updateNote = new UpdateNote();
                    updateNote.setNote(mNote);
                    mEventBus.post(updateNote);
                } else {
                    String title = mNoteDetailView.getNoteTitle();
                    String content = mNoteDetailView.getNoteContent();
                    mNote = new Note();
                    mNote.setTitle(title);
                    mNote.setContent(content);
                    mNote.setId(CommonUtils.getGuid());
                    mNote.setCreateTime(new Date());
                    mNote.setLastModifiedTime(new Date());
                    mNote.setNoteType(NoteUtil.NOTE_TYPE_NORMAL);
                    mNote.setLayoutType(ConstantUtils.LAYOUT_TYPE_ORDINARY);
                    mNote.setUserAuth("");
                    mNote.setHasSync(false);
                    mNote.setFavorite(false);
                    mEventBus.post(mNote);
                }
                mNoteDetailView.finishActivity();
                break;
        }
    }

    /**
     * 删除便签
     *
     * @param note
     */
    @Override
    public void deleteNote(Note note) {
        mNoteModel.deleteNote(note);
        mNoteDetailView.finishActivity();
    }

    @Override
    public void registerBus() {
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }


    @Override
    public void unRegisterBus() {
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    @Override
    public void configView(SharedPreferences sharedPreferences) {
        
    }
}
