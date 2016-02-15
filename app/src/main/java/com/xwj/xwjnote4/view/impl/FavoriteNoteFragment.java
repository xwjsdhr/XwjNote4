package com.xwj.xwjnote4.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.adapter.NoteAdapter;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.presenter.NoteListPresenter;
import com.xwj.xwjnote4.presenter.impl.NoteListPresenterImpl;
import com.xwj.xwjnote4.utils.PreferenceUtils;
import com.xwj.xwjnote4.view.NoteListView;

import java.util.ArrayList;

/**
 * 收藏便签的Fragment
 * Created by xwjsd on 2016-01-05.
 */
public class FavoriteNoteFragment extends Fragment implements NoteListView {

    public static final String TAG = FavoriteNoteFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRvHome;
    private NoteAdapter mAdapter;
    private NoteListPresenter mNotePresenter;
    private TextView mTvEmpty;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Note> mList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mNotePresenter = new NoteListPresenterImpl(context, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvHome = (RecyclerView) view.findViewById(R.id.rc_home);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_home_empty);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sl_home);
        boolean isGrid = PreferenceUtils.getBooleanOther(mContext, "key_recyclerview_layout");
        if (!isGrid) {
            mRvHome.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            mRvHome.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        mRvHome.setHasFixedSize(true);
        mAdapter = new NoteAdapter(mContext, mList, mSwipeRefreshLayout);
        mRvHome.setAdapter(mAdapter);
        mNotePresenter.getNotesByType("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void bindData(ArrayList<Note> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showEmptyView(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTvEmpty.setText(R.string.text_favorite_empty);
        }
        mTvEmpty.setVisibility(View.VISIBLE);
        mRvHome.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView(@Nullable String text) {

    }

    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public void refreshList(Note note) {

    }

    @Override
    public void refreshUpdate(Note note) {

    }
}
