package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.xwj.xwjnote4.Iterator.BackUpIterator;
import com.xwj.xwjnote4.Iterator.BackUpListener;
import com.xwj.xwjnote4.Iterator.impl.BackUpIteratorImpl;
import com.xwj.xwjnote4.presenter.SettingPresenter;
import com.xwj.xwjnote4.utils.FileUtils;
import com.xwj.xwjnote4.view.SettingView;

/**
 * Created by xwjsd on 2016-01-18.
 */
public class SettingPresenterImpl implements SettingPresenter, BackUpListener {

    private Context mContext;
    private SettingView mSettingView;
    private BackUpIterator mIterator;
    private FileUtils mFileUtils;

    public SettingPresenterImpl(Context context, SettingView settingView) {
        mContext = context;
        mSettingView = settingView;
        mIterator = new BackUpIteratorImpl(context);
        mFileUtils = FileUtils.getInstance(context);
    }

    @Override
    public void storeToLocale() {
        mSettingView.showBackUpProgress();
        mIterator.backUp2Disk(this);
    }

    @Override
    public long getFileSize() {
        return mFileUtils.getFileSize("notes_backup");
    }

    @Override
    public void recover() {
        mSettingView.showRecoveryProgress();
        mIterator.recover(this);
    }

    @Override
    public void getData() {
        String fileDir = mFileUtils.getFileDir("notes_backup");
        long fileSize = mFileUtils.getFileSize("notes_backup");
        mSettingView.bindView(fileDir, fileSize);
    }

    @Override
    public void onSuccess() {
        mSettingView.hideBackUpProgress();
        mSettingView.hideRecoveryProgress();
        mSettingView.setFileSize(getFileSize());
        Toast.makeText(mContext, "备份成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        mSettingView.hideBackUpProgress();
        mSettingView.hideRecoveryProgress();
        Toast.makeText(mContext, "备份失败", Toast.LENGTH_SHORT).show();
    }
}
