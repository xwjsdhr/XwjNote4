package com.xwj.xwjnote4.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.utils.ConstantUtils;

import java.util.ArrayList;

/**
 * Note的适配器
 * Created by xwjsd on 2016-01-05.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<Note> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NoteAdapter(Context context, ArrayList<Note> list, SwipeRefreshLayout swipeRefreshLayout) {
        mContext = context;
        mList = list;
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getLayoutType();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstantUtils.LAYOUT_TYPE_ORDINARY) {
            View itemView = mInflater.inflate(R.layout.item_note_total, parent, false);
            return new NoteViewHolder(itemView, mContext, mList, this, mSwipeRefreshLayout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {

        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
