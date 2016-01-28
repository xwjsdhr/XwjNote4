package com.xwj.xwjnote4.Iterator.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.xwj.xwjnote4.Iterator.BackUpIterator;
import com.xwj.xwjnote4.Iterator.BackUpListener;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.model.impl.NoteModelImpl;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.utils.FileUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by xw
 * jsd on 2016-01-18.
 */
public class BackUpIteratorImpl implements BackUpIterator {
    private static final int SUCCESS = 1;
    private static final int FAILED = 2;
    private Context mContext;
    private SharedPreferences mSp;
    private NoteModel mNoteModel;
    private SimpleDateFormat simpleDateFormat;
    private BackUpListener mListener;
    private FileUtils mFileUtils;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    mListener.onSuccess();
                    break;
                case FAILED:
                    mListener.onFailed();
                    break;
            }
        }
    };

    public BackUpIteratorImpl(Context context) {
        this.mContext = context;
        mSp = context.getSharedPreferences(ConstantUtils.NAME_STORE_NOTES, Context.MODE_PRIVATE);
        mNoteModel = new NoteModelImpl(context);
        mFileUtils = FileUtils.getInstance(context);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    }

    @Override
    public void backUp2Disk(final BackUpListener listener) {
        mListener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                ArrayList<Note> allNotes = mNoteModel.getAllNotes();
                try {
                    mFileUtils.list2File("notes_backup", allNotes);
                    mHandler.sendEmptyMessage(SUCCESS);
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(FAILED);
                }
            }
        }).start();
    }

    @Override
    public void recover(BackUpListener listener) {
        mListener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                try {
                    ArrayList<Note> list = mFileUtils.file2List("notes_backup");
                    mNoteModel.addNoteList(list);
                    mHandler.sendEmptyMessage(SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(FAILED);
                }
            }
        }).start();
    }
}
