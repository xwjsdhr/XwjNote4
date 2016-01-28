package com.xwj.xwjnote4.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.presenter.NotePresenter;
import com.xwj.xwjnote4.presenter.impl.NotePresenterImpl;
import com.xwj.xwjnote4.utils.CommonUtils;
import com.xwj.xwjnote4.view.NoteDetailView;

/**
 * Created by xwjsd on 2016-01-22.
 */
public class NoteDetailFragment extends Fragment implements NoteDetailView {

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
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mNotePresenter = new NotePresenterImpl(context, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mEtTitle = (EditText) view.findViewById(R.id.et_detail_title);
        mEtContent = (EditText) view.findViewById(R.id.et_detail_content);
        mTvCreateTime = (TextView) view.findViewById(R.id.tv_detail_create_time);
        mTvUpdateTime = (TextView) view.findViewById(R.id.tv_detail_update_time);
        mTvTotal = (TextView) view.findViewById(R.id.tv_detail_total);

    }

    @Override
    public void onResume() {
        super.onResume();
        mNotePresenter.getNoteByIntent();
    }

    @Override
    public void bindNote(Note note) {

        mEtContent.setText(note.getContent());
        mEtTitle.setText(note.getTitle());
        mTvTitle.setText(String.format("标题(%d)", getTitleNum()));
        mTvContent.setText(String.format("内容(%d)", getContentNum()));
        mTvTotal.setText(String.format("总字数(%d)", getTitleNum() + getContentNum()));
        mTvCreateTime.setText(String.format("创建时间：%s", CommonUtils.getDate(mContext, note.getCreateTime())));
        mTvUpdateTime.setText(String.format("更新时间：%s", CommonUtils.getDate(mContext, note.getLastModifiedTime())));

    }

    @Override
    public void finishActivity() {

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
    public void showDialog() {

    }

    @Override
    public boolean isEditTextUnFocused() {
        if (!mEtTitle.isFocused() && !mEtContent.isFocused()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showdSnackbar(String msg) {

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
//        if (mSaveItem != null) {
//            mSaveItem.setVisible(true);
//        }
    }

    @Override
    public void hideFinishBtn() {
//        if (mSaveItem != null) {
//            mSaveItem.setVisible(false);
//        }
    }

    @Override
    public boolean isEditTextUnEmpty() {

        if (!TextUtils.isEmpty(mEtTitle.getText().toString()) &&
                !TextUtils.isEmpty(mEtContent.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Note detailGetIntent() {
        return mNote;
    }

    public void onEvent(Note note) {
        mNote = note;
    }
}
