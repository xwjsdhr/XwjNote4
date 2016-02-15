package com.xwj.xwjnote4.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by xwjsd on 2016-01-22.
 */
public class NoteDetailFragment extends Fragment implements NoteDetailView, View.OnClickListener {

    private static final String TAG = NoteDetailFragment.class.getSimpleName();
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
    private FragmentManager mManager;
    private MenuItem mSaveItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mNotePresenter = new NotePresenterImpl(context, this);
        mManager = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_note_detail, container, false);
        setHasOptionsMenu(true);
        return v;
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
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_detail);
        Menu menu = mToolbar.getMenu();
        mSaveItem = menu.findItem(R.id.action_save);
        mToolbar.setNavigationOnClickListener(this);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (isEditTextUnEmpty()) {
//                    showFinishBtn();
//                } else {
//                    hideFinishBtn();
//                }
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
//                if (isEditTextUnEmpty()) {
//                    showFinishBtn();
//                } else {
//                    hideFinishBtn();
//                }
                setTitleNum(s.length());
                setTotalNum(getTotalNum());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNote = mNotePresenter.getNoteByIntent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_detail, menu);
//        mSaveItem = menu.findItem(R.id.action_save);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            Log.e(TAG, "onOptionItemSelected");
        }
        return true;
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

        if (!TextUtils.isEmpty(mEtTitle.getText().toString()) &&
                !TextUtils.isEmpty(mEtContent.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Note detailGetIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNote = (Note) bundle.getSerializable(ConstantUtils.NOTE_EXTRA);
        }
        return mNote;
    }

    @Override
    public void backToHomeFragment() {
        mManager.beginTransaction()
                .setCustomAnimations(R.anim.scale_in, R.anim.scale_out)
                .replace(R.id.container_main, new HomeNoteFragment(), HomeNoteFragment.class.getSimpleName())
                .remove(this)
                .commit();

    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick");
        mNotePresenter.handleClick(v);
    }
}
