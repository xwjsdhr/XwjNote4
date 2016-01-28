package com.xwj.xwjnote4.view.impl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.presenter.UserPresenter;
import com.xwj.xwjnote4.presenter.impl.UserPresenterImpl;
import com.xwj.xwjnote4.view.LoginView;

/**
 * 登陆界面Activity
 */
public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private Toolbar mToolbar;
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLoginOk, mBtnLoginCancel;
    private UserPresenter mUserPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    private void setListener() {
        mBtnLoginOk.setOnClickListener(this);
        mBtnLoginCancel.setOnClickListener(this);
    }

    private void initView() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);
        mEtUsername = (EditText) this.findViewById(R.id.et_login_username);
        mEtPassword = (EditText) this.findViewById(R.id.et_login_password);
        mBtnLoginOk = (Button) this.findViewById(R.id.btn_login_ok);
        mBtnLoginCancel = (Button) this.findViewById(R.id.btn_login_cancel);
        mUserPresenter = new UserPresenterImpl(this, this);
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
    public void finishActivity() {
        this.finish();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "正在登录", "登录中");
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        mUserPresenter.handleClick(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reg) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }
}
