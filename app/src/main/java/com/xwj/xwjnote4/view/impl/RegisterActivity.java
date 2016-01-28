package com.xwj.xwjnote4.view.impl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.presenter.UserPresenter;
import com.xwj.xwjnote4.presenter.impl.UserPresenterImpl;
import com.xwj.xwjnote4.view.RegView;

/**
 * Created by xwjsd on 2016-01-12.
 */
public class RegisterActivity extends AppCompatActivity implements RegView, View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtPassword2;
    private Button mBtnRegOk;
    private Button mBtnRegCancel;
    private UserPresenter mUserPresenter;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mUserPresenter = new UserPresenterImpl(this, this);

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        mEtUsername = (EditText) this.findViewById(R.id.et_reg_username);
        mEtPassword = (EditText) this.findViewById(R.id.et_reg_password);
        mEtPassword2 = (EditText) this.findViewById(R.id.et_reg_password2);
        mBtnRegOk = (Button) this.findViewById(R.id.btn_reg_ok);
        mBtnRegCancel = (Button) this.findViewById(R.id.btn_reg_cancel);
        mBtnRegOk.setOnClickListener(this);
        mBtnRegCancel.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    @Override
    public String getUsername() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public String getPassword2() {
        return mEtPassword2.getText().toString();
    }

    @Override
    public void finishActivity() {
        this.finish();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "正在注册", "");
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        mUserPresenter.handleClick(v);
    }
}
