package com.xwj.xwjnote4.view;

/**
 * Created by xwjsd on 2016-01-18.
 */
public interface SettingView {

    void showBackUpProgress();

    void hideBackUpProgress();

    void showRecoveryProgress();

    void hideRecoveryProgress();

    void setFileSize(long size);

    void bindView(String fileDir, long fileSize);

}
