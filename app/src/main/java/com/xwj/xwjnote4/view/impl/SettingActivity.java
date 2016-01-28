package com.xwj.xwjnote4.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.presenter.SettingPresenter;
import com.xwj.xwjnote4.presenter.impl.SettingPresenterImpl;
import com.xwj.xwjnote4.view.SettingView;

/**
 * Created by xwjsd on 2016-01-13.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener, SettingView {

    private LinearLayout mLlStoreLocale, mLlRecover;

    private TextView mTvSize;
    private SettingPresenter mSettingPresenter;
    private ProgressBar mPbSetting, mPbSettingRecover;
    private TextView mTvFileDir;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting);
        initView();
        getFragmentManager().beginTransaction()
                .replace(R.id.setting_container, new SettingFragment())
                .commit();
        mSettingPresenter = new SettingPresenterImpl(this, this);
        mSettingPresenter.getData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
                overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
            }
        });
        mLlStoreLocale = (LinearLayout) this.findViewById(R.id.ll_store_local);
        mTvSize = (TextView) this.findViewById(R.id.tv_size);
        mLlRecover = (LinearLayout) this.findViewById(R.id.ll_recover);
        mPbSetting = (ProgressBar) this.findViewById(R.id.pb_setting);
        mPbSettingRecover = (ProgressBar) this.findViewById(R.id.pb_setting_recover);
        mTvFileDir = (TextView) this.findViewById(R.id.tv_file_dir);
        mLlStoreLocale.setOnClickListener(this);
        mLlRecover.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLlStoreLocale) {
            mSettingPresenter.storeToLocale();
        } else if (v == mLlRecover) {
            mSettingPresenter.recover();
        }
    }

    @Override
    public void showBackUpProgress() {
        mPbSetting.setVisibility(View.VISIBLE);
        mLlRecover.setClickable(false);
        mLlRecover.setEnabled(false);
        mLlStoreLocale.setClickable(false);
        mLlStoreLocale.setEnabled(false);
        mTvSize.setVisibility(View.GONE);
    }

    @Override
    public void hideBackUpProgress() {
        mPbSetting.setVisibility(View.GONE);
        mLlStoreLocale.setClickable(true);
        mLlStoreLocale.setEnabled(true);
        mLlRecover.setClickable(true);
        mLlRecover.setEnabled(true);
        mTvSize.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecoveryProgress() {
        mPbSettingRecover.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecoveryProgress() {
        mPbSettingRecover.setVisibility(View.GONE);
    }

    @Override
    public void setFileSize(long size) {
        mTvSize.setText(Formatter.formatFileSize(this, size));
    }

    @Override
    public void bindView(String fileDir, long fileSize) {
        mTvSize.setText(Formatter.formatFileSize(this, fileSize));
        mTvFileDir.setText(fileDir);
    }
}

