package com.xwj.xwjnote4.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.adapter.NoteAdapter;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.TextMsg;
import com.xwj.xwjnote4.model.UpdateNote;
import com.xwj.xwjnote4.presenter.NoteListPresenter;
import com.xwj.xwjnote4.presenter.impl.NoteListPresenterImpl;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.utils.PreferenceUtils;
import com.xwj.xwjnote4.view.NoteListView;
import com.xwj.xwjnote4.widget.MyItemDecoration;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by xwjsd on 2016-01-05.
 */
public class HomeNoteFragment extends Fragment implements NoteListView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = HomeNoteFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRvHome;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NoteAdapter mAdapter;
    private NoteListPresenter mNotePresenter;
    private TextView mTvEmpty;
    private ArrayList<Note> mList = new ArrayList<>();
    private ProgressBar mProgressBar;
    private EventBus mEventBus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mNotePresenter = new NoteListPresenterImpl(context, this);
        mEventBus = EventBus.getDefault();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvHome = (RecyclerView) view.findViewById(R.id.rc_home);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sl_home);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_home_empty);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_home);
        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null ||
                                recyclerView.getChildCount() == 0) ? 0
                                : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRvHome.setHasFixedSize(true);
        mRvHome.addItemDecoration(new MyItemDecoration(mContext));
        mAdapter = new NoteAdapter(mContext, mList, mSwipeRefreshLayout);
        mRvHome.setAdapter(mAdapter);
        mNotePresenter.getNotesByType(NoteUtil.NOTE_TYPE_NORMAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRvHome.setLayoutManager(!PreferenceUtils.getBooleanOther(mContext, ConstantUtils.SP_RV_LAYOUT_KEY)
                ? new LinearLayoutManager(mContext)
                : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    /**
     * 绑定数据
     *
     * @param list
     */
    @Override
    public void bindData(ArrayList<Note> list) {
        Log.e(TAG, list.size() + "");
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRvHome.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mRvHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView(String text) {
        mTvEmpty.setVisibility(View.VISIBLE);
        mRvHome.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView(@Nullable String text) {
        mTvEmpty.setVisibility(View.GONE);
        mRvHome.setVisibility(View.VISIBLE);
    }

    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public void refreshList(Note note) {
        if (note != null) {
            mList.add(0, note);
        }
        if (mAdapter != null) {
            mAdapter.notifyItemInserted(0);
        }
        mRvHome.scrollToPosition(0);
    }


    @Override
    public void refreshUpdate(Note note) {
        int index = mList.indexOf(note);
        mList.set(index, note);
        mAdapter.notifyItemChanged(index);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    public void onEvent(TextMsg msg) {
        mNotePresenter.searchNotesByTitle(msg.getQueryText());
    }

    public void onEvent(Note note) {
        Log.e(TAG, "ONEVENT ADD");
        Log.e(TAG, note.toString());
        mNotePresenter.addNote(note);

    }

    public void onEvent(UpdateNote note) {
        mNotePresenter.updateNote(note.getNote());
    }
}
