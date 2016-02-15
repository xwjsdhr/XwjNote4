package com.xwj.xwjnote4.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
public class HomeNoteFragment extends Fragment implements NoteListView, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

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
    private SearchView mSearchView;

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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name_detail);
        toolbar.inflateMenu(R.menu.menu_main);
        Menu menu = toolbar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
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
        setHasOptionsMenu(true);
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

    public void onEvent(TextMsg msg) {
        mNotePresenter.searchNotesByTitle(msg.getQueryText());
    }

    public void onEvent(Note note) {
        mNotePresenter.addNote(note);
    }

    public void onEvent(UpdateNote note) {
        mNotePresenter.updateNote(note.getNote());
    }

    public void onEventMainThread(ArrayList<Note> list) {
        this.bindData(list);
        this.hideProgress();
        this.hideEmptyView("a");
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mNotePresenter.searchNotesByTitle(newText);
        return true;
    }

}
