package com.xwj.xwjnote4.view.impl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.presenter.NotePresenter;
import com.xwj.xwjnote4.presenter.impl.NotePresenterImpl;
import com.xwj.xwjnote4.utils.CommonUtils;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.view.NoteDetailView;

/**
 * 便签详情页面的Activity
 */
public class NoteDetailActivity extends AppCompatActivity implements NoteDetailView, View.OnFocusChangeListener {


    private static final String TAG = NoteDetailActivity.class.getSimpleName();
    private EditText mEtTitle;
    private EditText mEtContent;
    private Toolbar mToolbar;
    private TextView mTvCreateTime;
    private TextView mTvUpdateTime;
    private NotePresenter mNotePresenter;
    private Note mNote;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvTotal;

    private MenuItem mSaveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initView();
        setListener();
        mNotePresenter = new NotePresenterImpl(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNote = mNotePresenter.getNoteByIntent();
    }

    /**
     * 初始化视图。
     */
    public void initView() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTvTitle = (TextView) this.findViewById(R.id.tv_title);
        mTvContent = (TextView) this.findViewById(R.id.tv_content);
        mEtTitle = (EditText) this.findViewById(R.id.et_detail_title);
        mEtContent = (EditText) this.findViewById(R.id.et_detail_content);
        mTvCreateTime = (TextView) this.findViewById(R.id.tv_detail_create_time);
        mTvUpdateTime = (TextView) this.findViewById(R.id.tv_detail_update_time);
        mTvTotal = (TextView) this.findViewById(R.id.tv_detail_total);
    }

    /**
     * 设置监听器。
     */
    private void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotePresenter.saveNote();
            }
        });
        mEtContent.setOnFocusChangeListener(this);
        mEtTitle.setOnFocusChangeListener(this);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isEditTextUnEmpty()) {
                    showFinishBtn();
                } else {
                    hideFinishBtn();
                }
                setContentNum(s.length());
                setTotalNum(getTotalNum());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isEditTextUnEmpty()) {
                    showFinishBtn();
                } else {
                    hideFinishBtn();
                }
                setTitleNum(s.length());
                setTotalNum(getTotalNum());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 绑定便签
     *
     * @param note
     */
    @Override
    public void bindNote(Note note) {
        mEtContent.setText(note.getContent());
        mEtTitle.setText(note.getTitle());
        mTvTitle.setText(String.format("标题(%d)", getTitleNum()));
        mTvContent.setText(String.format("内容(%d)", getContentNum()));
        mTvTotal.setText(String.format("总字数(%d)", getTitleNum() + getContentNum()));
        mTvCreateTime.setText(String.format("创建时间：%s", CommonUtils.getDate(this, note.getCreateTime())));
        mTvUpdateTime.setText(String.format("更新时间：%s", CommonUtils.getDate(this, note.getLastModifiedTime())));

    }

    @Override
    public void finishActivity() {
        this.finish();
        //overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    @Override
    public String getNoteTitle() {
        return mEtTitle.getText().toString();
    }

    @Override
    public String getNoteContent() {
        return mEtContent.getText().toString();
    }

    @Override
    public void changeNavigationIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.action_truncate);
        mSaveItem = menu.findItem(R.id.action_save);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mNotePresenter.handleItemClick(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotePresenter.unRegisterBus();
    }

    @Override
    public void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog)
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteDetailActivity.this.finishActivity();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public boolean isEditTextUnFocused() {
        return !mEtTitle.isFocused() && !mEtContent.isFocused();
    }

    @Override
    public void showdSnackbar(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setTitle("确认删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mNote != null) {
                            mNotePresenter.deleteNote(mNote);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    @Override
    public int getTitleNum() {
        return mEtTitle.getText().toString().length();
    }

    @Override
    public int getContentNum() {
        return mEtContent.getText().toString().length();
    }

    @Override
    public void setTitleNum(int num) {
        mTvTitle.setText(String.format("标题(%d)", num));
    }

    @Override
    public void setContentNum(int num) {
        mTvContent.setText(String.format("内容(%d)", num));
    }

    @Override
    public int getTotalNum() {
        return getContentNum() + getTitleNum();
    }

    @Override
    public void setTotalNum(int num) {
        mTvTotal.setText(String.format("总字数(%d)", num));
    }

    @Override
    public void showFinishBtn() {
        if (mSaveItem != null) {
            mSaveItem.setVisible(true);
        }
    }

    @Override
    public void hideFinishBtn() {
        if (mSaveItem != null) {
            mSaveItem.setVisible(false);
        }
    }

    @Override
    public boolean isEditTextUnEmpty() {

        return !TextUtils.isEmpty(mEtTitle.getText().toString()) &&
                !TextUtils.isEmpty(mEtContent.getText().toString());
    }

    @Override
    public Note detailGetIntent() {
        return (Note) getIntent().getSerializableExtra(ConstantUtils.NOTE_EXTRA);
    }

    @Override
    public void backToHomeFragment() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    }
}
