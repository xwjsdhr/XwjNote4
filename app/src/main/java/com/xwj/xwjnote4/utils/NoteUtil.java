package com.xwj.xwjnote4.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.xwj.xwjnote4.model.DaoMaster;
import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobObject;
import de.greenrobot.dao.query.WhereCondition;

/**
 * 操作数据库的工具类。
 * Created by xwjsd on 2015/12/4.
 */
public class NoteUtil {

    private static final String TAG = NoteUtil.class.getSimpleName();
    private static NoteUtil sInstance = new NoteUtil();
    private static NoteDao sDao;
    private static Context sContext;

    private NoteUtil() {
    }


    public static NoteUtil getInstance(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "NOTE_UPDATE_3.db", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster master = new DaoMaster(db);
        sDao = master.newSession().getNoteDao();
        sContext = context;
        return sInstance;
    }

    /**
     * 添加便签
     *
     * @param note
     */
    public void addNote(Note note) {
        long i = sDao.insert(note);

        Log.e(TAG, "insert     " + i);
    }


    /**
     * 删除便签
     *
     * @param note
     */
    public void deleteNote(Note note) {
        sDao.delete(note);
    }

    /**
     * 更新便签
     *
     * @param note
     */
    public void updateNote(Note note) {
        sDao.update(note);
    }

    /**
     * 通过id获取便签
     *
     * @param id
     * @return
     */
    public Note getNote(String id) {

        WhereCondition condition = new WhereCondition.PropertyCondition(NoteDao.Properties.Id, "=", id);
        List<Note> list = sDao.queryBuilder().where(condition).list();
        return list.size() != 0 ? list.get(0) : null;
    }

    /**
     * @return 获取所有非回收站的便签的列表。
     */
    public ArrayList<Note> getAll() {
        ArrayList<Note> arrayList = new ArrayList<>();
        arrayList.addAll(sDao.queryRaw("where " + NoteDao.Properties.NoteType.columnName + "= ? order by " + NoteDao.Properties.LastModifiedTime.columnName + " desc", NOTE_TYPE_NORMAL));
        return arrayList;
    }

    /**
     * 通过类型获取便签列表
     *
     * @param type 指定的类型
     * @return 列表
     */
    public ArrayList<Note> getNotes(String type) {
        ArrayList<Note> arrayList = new ArrayList<>();
        WhereCondition.StringCondition condition = new WhereCondition.StringCondition(
                NoteDao.Properties.NoteType.columnName + " = ?", type
        );
        List<Note> list = sDao.queryBuilder()
                .where(condition)
                .orderDesc(NoteDao.Properties.CreateTime).list();
        arrayList.addAll(list);
        return arrayList;

    }

    public static final String NOTE_TYPE_NORMAL = "NOTE_TYPE_NORMAL";
    public static final String NOTE_TYPE_TRASH = "NOTE_TYPE_TRASH";
    public static final String NOTE_TYPE_SECURITY = "NOTE_TYPE_SECURITY";

    /**
     * 同过标题获取是否在数据库中。
     *
     * @param title     指定的标题
     * @param isInTrash 是否在回收站
     * @return
     */
    public ArrayList<Note> getNotesByTitle(String title, boolean isInTrash) {
        ArrayList<Note> arrayList = new ArrayList<>();
        String noteType = null;
        if (!isInTrash) {
            noteType = NOTE_TYPE_NORMAL;
        } else {
            noteType = NOTE_TYPE_TRASH;
        }
        if (TextUtils.isEmpty(title)) {
            arrayList.addAll(sDao.queryRaw("where " + NoteDao.Properties.NoteType.columnName +
                    " = ?", noteType));
        } else {

            arrayList.addAll(sDao.queryRaw("where " + NoteDao.Properties.Title.columnName +
                    " like  ? and " + NoteDao.Properties.NoteType.columnName + " = ?", "%" + title +
                    "%", noteType));
        }
        return arrayList;
    }

    public ArrayList<Note> getAllFavoriteNotes() {

        ArrayList<Note> arrayList = new ArrayList<>();

        arrayList.addAll(sDao.queryRaw("where " + NoteDao.Properties.Favorite.columnName + " = ? and " +
                        NoteDao.Properties.NoteType.columnName + " = ? order by " + NoteDao.Properties.LastModifiedTime.columnName,
                1 + "", NoteUtil.NOTE_TYPE_NORMAL));
        Collections.reverse(arrayList);
        return arrayList;
    }

    public void clearAllTrash() {
        sDao.getDatabase().execSQL("delete from " + sDao.getTablename()
                + " where " + NoteDao.Properties.NoteType.columnName + "=" + "\'" + NoteUtil.NOTE_TYPE_TRASH + "\'");
    }


    public List<Note> addAllNotes(List<Note> list) {
        List<Note> reNotes = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                List<Note> notes = sDao.queryRaw("where " + NoteDao.Properties.Id.columnName + "= ? desc", list.get(i).getId());
                if (notes != null && notes.size() > 0) {

                } else {
                    sDao.insert(list.get(i));
                    reNotes.add(list.get(i));
                }
            }
        }
        return reNotes;
    }

    public void allNoteList(List<Note> list) {
        sDao.insertInTx(list);
    }

    public List<BmobObject> getAllUnSyncNotes() {
        List<Note> list = sDao.queryRaw("where " + NoteDao.Properties.HasSync + " = ?", 0 + "");
        List<BmobObject> bmobObjectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            bmobObjectList.add(list.get(i));
        }
        return bmobObjectList;
    }

    public List<Note> getAllUnRecoveryNotes(List<Note> notes) {
        ArrayList<Note> all = getAll();
        List<Note> unRecoveryNotes = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            for (int j = 0; j < all.size(); j++) {
                if (!notes.contains(all.get(j))) {
                    unRecoveryNotes.add(all.get(j));
                }
            }
        }
        return unRecoveryNotes;
    }

    public int getNotesCount(String type) {
        WhereCondition.StringCondition condition = new WhereCondition.StringCondition(
                NoteDao.Properties.NoteType.columnName + "=?",
                type);
        long count = sDao.queryBuilder().where(condition).count();
        Log.e(TAG, count + "");
        return (int) count;
    }
}
