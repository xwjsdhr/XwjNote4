package com.xwj.xwjnote4.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 数据模型接口
 * Created by xwjsd on 2015/12/4.
 */
public interface NoteModel {

    /**
     * 添加便签
     *
     * @param note
     */
    void addNote(Note note);

    /**
     * 删除便签
     *
     * @param note
     */
    void deleteNote(Note note);

    /**
     * 更新便签
     *
     * @param note
     */
    void updateNote(Note note);

    /**
     * 获取指定Id的便签
     *
     * @param id
     * @return
     */
    Note getNote(String id);

    /**
     * 获取全部便签
     *
     * @return 便签的集合
     */
    ArrayList<Note> getAllNotes();

    /**
     * 将制定的Note移入回收站
     *
     * @param note
     */
    void moveToTrashBin(Note note);

    /**
     * 获取指定类型的Note的集合。
     *
     * @param type
     * @return
     */
    ArrayList<Note> getNotes(String type);


    ArrayList<Note> getNotesByTitle(String content, boolean b);

    void restoreNote(Note note);

    /**
     * 获取所有已收藏的便签集合。
     *
     * @return
     */
    ArrayList<Note> getAllFavoriteNotes();

    void clearAllTrash();

    List<Note> addAllNotes(List<Note> list);

    void moveToSecurity(Note note);

    void addNoteList(List<Note> list);
    public List<BmobObject> getAllUnSyncList();

    int getNotesCount(String type);
}
