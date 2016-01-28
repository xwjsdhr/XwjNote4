package com.xwj.xwjnote4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.presenter.ItemPresenter;
import com.xwj.xwjnote4.presenter.impl.ItemPresenterImpl;
import com.xwj.xwjnote4.utils.CommonUtils;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.utils.PreferenceUtils;
import com.xwj.xwjnote4.view.ItemView;
import com.xwj.xwjnote4.view.impl.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ViewHolder
 * Created by xwjsd on 2016-01-06.
 */
class NoteViewHolder extends RecyclerView.ViewHolder implements ItemView, View.OnLongClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener, SwipeLayout.SwipeListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = NoteViewHolder.class.getSimpleName();
    @Bind(R.id.tv_item_title)
    public TextView mTvTitle; //标题
    @Bind(R.id.chk_item_favorite)
    public CheckBox mChkFavo; // 收藏按钮
    @Bind(R.id.tv_item_date)
    public TextView mTvDate; // 日期
    @Bind(R.id.tv_item_content)
    public TextView mTvContent; //内容
    @Bind(R.id.tv_item_toggle_content)
    public TextView mTvShowMore; // 显示更多
    @Bind(R.id.sl_item)
    public SwipeLayout mSwipeLayout;
    @Bind(R.id.btn_item_delete)
    public ImageButton mBtnDelete;  //删除按钮
    @Bind(R.id.btn_item_favo)
    public Button mBtnFavo;
    @Bind(R.id.btn_item_move_to_trash)
    public ImageButton mBtnTrash;
    @Bind(R.id.ll_content)
    public LinearLayout mLlContent;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private PopupMenu mPopupMenu; // 长按显示菜单
    private Context mContext; //上下文
    private ItemPresenter mItemPresenter;
    private ArrayList<Note> mList; //数据集合
    private NoteAdapter mAdapter;
    private Note mNote;
    MainActivity mainActivity;


    public NoteViewHolder(View itemView, Context context, ArrayList<Note> list, NoteAdapter adapter,
                          SwipeRefreshLayout swipeRefreshLayout) {
        super(itemView);
        mainActivity = (MainActivity) context;
        initView(itemView, context, list, adapter, swipeRefreshLayout);
        configView();
        registerListener(itemView);
    }

    private void initView(View itemView, Context context, ArrayList<Note> list,
                          NoteAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        mContext = context;
        mList = list;
        mAdapter = adapter;
        mItemPresenter = new ItemPresenterImpl(context, this, mainActivity);
        mSwipeRefreshLayout = swipeRefreshLayout;
        mPopupMenu = new PopupMenu(context, itemView);
        inflateMenu(list);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 设置视图。
     */
    private void configView() {
        mTvShowMore.setVisibility(PreferenceUtils
                .getBooleanOther(mContext, "key_show_more_content") ? View.VISIBLE : View.GONE);
        mTvShowMore.setText(mTvContent.isShown() ? R.string.text_hide_content : R.string.text_show_content);
        mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
    }

    /**
     * 注册监听器
     *
     * @param itemView
     */
    private void registerListener(View itemView) {
        mSwipeLayout.addSwipeListener(this);
        itemView.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mTvShowMore.setOnClickListener(this);
        mBtnFavo.setOnClickListener(this);
        mBtnTrash.setOnClickListener(this);
        mLlContent.setOnClickListener(this);
        mPopupMenu.setOnMenuItemClickListener(this);
        mChkFavo.setOnCheckedChangeListener(this);
    }

    private void inflateMenu(ArrayList<Note> list) {
        mPopupMenu.inflate(list.get(0).getNoteType().equals(NoteUtil.NOTE_TYPE_TRASH) ?
                R.menu.popup_menu_trash : R.menu.popup_menu);
    }

    @Override
    public void showPopupMenu() {
        Menu menu = mPopupMenu.getMenu();
        MenuItem item = menu.findItem(R.id.action_favorite);
        if (item != null) {
            if (getNote().getFavorite()) {
                item.setTitle("取消收藏");
            } else {
                item.setTitle("收藏");
            }
            item.setVisible(false);
        }
        MenuItem trashItem = menu.findItem(R.id.action_trash);
        if (trashItem != null) {
            trashItem.setVisible(false);
        }
        MenuItem shareItem = menu.findItem(R.id.action_share);
        if (shareItem != null) {
            shareItem.setVisible(false);
        }
        mPopupMenu.show();
    }

    /**
     * 获取当前item的note
     *
     * @return
     */
    @Override
    public Note getNote() {
        return mNote;
    }

    /**
     * 删除item项
     *
     * @param note
     */
    @Override
    public void removeItem(Note note) {
        int index = mList.indexOf(note);
        mList.remove(note);
        mAdapter.notifyItemRemoved(index);
    }

    @Override
    public void showContent() {
        mTvContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        mTvContent.setVisibility(View.GONE);
    }

    @Override
    public boolean contentIsShown() {
        return mTvContent.getVisibility() == View.VISIBLE;
    }

    @Override
    public void changeShowText(int resId, int drawableId) {
        mTvShowMore.setText(resId);
    }

    @Override
    public void toggleCheckBox() {
        mChkFavo.toggle();
        mItemPresenter.favoToggle(this.getNote(), mChkFavo.isChecked());
    }

    @Override
    public void itemStartActivity(Intent intent) {
        mContext.startActivity(intent);
        //mActivity.overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    @Override
    public boolean isSwiped() {
        return SwipeLayout.Status.Open == mSwipeLayout.getOpenStatus();
    }

    @Override
    public void closeSwipeLayout() {
        mSwipeLayout.close(true);
    }

    @Override
    public void toggleBtnText() {
        mBtnFavo.setText(getNote().getFavorite() ? R.string.text_btn_unfavo : R.string.text_btn_favo);
    }

    @Override
    public void closeSwipeLayoutWithoutSmooth() {
        mSwipeLayout.close(false);
    }

    /**
     * 绑定item视图。
     *
     * @param note
     */
    @Override
    public void bindView(Note note) {
        mNote = note;
        this.mTvTitle.setText(note.getTitle());
        this.mTvContent.setText(note.getContent());
        this.mTvDate.setText(CommonUtils.getDate(mContext, note.getCreateTime()));
        this.itemView.setTag(note);
        this.mChkFavo.setTag(note);
        this.mChkFavo.setChecked(note.getFavorite());
        this.mLlContent.setTag(note);
    }

    @Override
    public void hideShowMore() {

    }

    @Override
    public void showShowMore() {

    }

    @Override
    public boolean onLongClick(View v) {
        return mItemPresenter.handleLongClick(v);
    }

    @Override
    public void onClick(View v) {
        mItemPresenter.handleClick(v);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mItemPresenter.handlePopupMenu(mPopupMenu, item.getItemId());
        return true;
    }

    @Override
    public void onStartOpen(SwipeLayout layout) {
        itemView.setClickable(false);
        itemView.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onOpen(SwipeLayout layout) {
        itemView.setEnabled(false);
        itemView.setClickable(false);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onStartClose(SwipeLayout layout) {
        itemView.setClickable(false);
        itemView.setEnabled(false);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClose(SwipeLayout layout) {
        itemView.setClickable(true);
        itemView.setEnabled(true);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
    }

    @Override
    public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mItemPresenter.favoToggle((Note) buttonView.getTag(), isChecked);
        toggleBtnText();
    }
}
