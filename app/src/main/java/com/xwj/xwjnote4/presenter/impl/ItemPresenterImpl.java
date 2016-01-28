package com.xwj.xwjnote4.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.model.impl.NoteModelImpl;
import com.xwj.xwjnote4.presenter.ItemPresenter;
import com.xwj.xwjnote4.utils.ConstantUtils;
import com.xwj.xwjnote4.utils.NoteUtil;
import com.xwj.xwjnote4.utils.PreferenceUtils;
import com.xwj.xwjnote4.view.ItemView;
import com.xwj.xwjnote4.view.MainView;
import com.xwj.xwjnote4.view.impl.MainActivity;
import com.xwj.xwjnote4.view.impl.NoteDetailFragment;

import de.greenrobot.event.EventBus;

/**
 * item的业务逻辑实现类。
 * Created by xwjsd on 2016-01-06.
 */
public class ItemPresenterImpl implements ItemPresenter {

    private static final String TAG = ItemPresenterImpl.class.getSimpleName();
    private ItemView mItemView;
    private Context mContext;
    private NoteModel mNoteModel;
    private EventBus mEventBus;
    private FragmentManager mManager;
    private MainView mMainView;


    public ItemPresenterImpl(Context context, ItemView itemView, MainView mainView) {
        mItemView = itemView;
        mContext = context;
        mNoteModel = new NoteModelImpl(context);
        mEventBus = EventBus.getDefault();
        mManager = ((MainActivity) context).getSupportFragmentManager();
        this.mMainView = mainView;
    }

    @Override
    public void handlePopupMenu(PopupMenu menu, int itemId) {
        switch (itemId) {
            case R.id.action_delete:
                mNoteModel.deleteNote(mItemView.getNote());
                mItemView.removeItem(mItemView.getNote());
                saveDelNoteId();
                menu.dismiss();
                break;
            case R.id.action_trash:
                mNoteModel.moveToTrashBin(mItemView.getNote());
                mItemView.removeItem(mItemView.getNote());
                menu.dismiss();
                break;
            case R.id.action_favorite:
                mItemView.toggleCheckBox();
                menu.dismiss();
                break;
            case R.id.action_share:
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                menu.dismiss();
                break;
            case R.id.action_restore:
                mNoteModel.restoreNote(mItemView.getNote());
                mItemView.removeItem(mItemView.getNote());
                menu.dismiss();
                break;
            case R.id.action_show_more:
                Menu popupMenu = menu.getMenu();
                MenuItem favItem = popupMenu.findItem(R.id.action_favorite);
                MenuItem trashItem = popupMenu.findItem(R.id.action_trash);
                MenuItem shareItem = popupMenu.findItem(R.id.action_share);
                favItem.setVisible(true);
                trashItem.setVisible(true);
                shareItem.setVisible(true);
                menu.show();
                break;
        }
    }

    private void saveDelNoteId() {
        String idStr = PreferenceUtils.getString(mContext, ConstantUtils.DEL_NOTE_LIST);
        if (TextUtils.isEmpty(idStr)) {
            PreferenceUtils.setString(mContext, ConstantUtils.DEL_NOTE_LIST, mItemView.getNote().getId());
        } else {
            idStr = idStr + "," + mItemView.getNote().getId();
            PreferenceUtils.setString(mContext, ConstantUtils.DEL_NOTE_LIST, idStr);
        }
    }

    @Override
    public void favoToggle(Note note, boolean isChecked) {
        note.setFavorite(isChecked);
        mNoteModel.updateNote(note);
    }

    @Override
    public void toggleContent() {
        if (mItemView.contentIsShown()) {
            mItemView.hideContent();
            mItemView.changeShowText(R.string.text_show_content, R.drawable.ic_arrow_drop_down_18dp);
        } else {
            mItemView.showContent();
            mItemView.changeShowText(R.string.text_hide_content, R.drawable.ic_arrow_drop_up_18dp);
        }
    }

    @Override
    public void handleClick(View v) {

        if (v.getId() == R.id.tv_item_toggle_content) {
            this.toggleContent();
        } else if (v.getId() == R.id.btn_item_delete) {
            mNoteModel.deleteNote(mItemView.getNote());
            mItemView.removeItem(mItemView.getNote());
            saveDelNoteId();
            mEventBus.post(mNoteModel.getNotesCount(NoteUtil.NOTE_TYPE_NORMAL));
        } else if (v.getId() == R.id.btn_item_favo) {

            mItemView.toggleCheckBox();
            mItemView.closeSwipeLayout();
            mItemView.toggleBtnText();
        } else if (v.getId() == R.id.btn_item_move_to_trash) {
            mNoteModel.moveToTrashBin(mItemView.getNote());
            mItemView.removeItem(mItemView.getNote());
        } else if (v.getId() == R.id.ll_content) {
            if (mItemView.isSwiped()) {
                mItemView.closeSwipeLayout();
            } else {
//                Intent intent = new Intent(mContext, NoteDetailActivity.class);
//                intent.putExtra(ConstantUtils.NOTE_EXTRA, mItemView.getNote());
//                mItemView.itemStartActivity(intent);
                mManager.beginTransaction()
                        .add(R.id.container_main, new NoteDetailFragment(), NoteDetailFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public boolean handleLongClick(View view) {
        if (mItemView.contentIsShown()) {
            Toast.makeText(mContext, "请先隐藏便签内容", Toast.LENGTH_SHORT).show();
        } else {
            mItemView.showPopupMenu();
        }
        return true;
    }

    @Override
    public void configView(SharedPreferences sharedPreferences) {
        boolean canShowContent
                = sharedPreferences.getBoolean("key_show_more_content", false);
        /**
         * 判断设置中的当前选项是否开启。
         */
        if (canShowContent) {
            mItemView.showShowMore();
        } else {
            mItemView.hideShowMore();
        }
        this.toggleContent();
    }
}
