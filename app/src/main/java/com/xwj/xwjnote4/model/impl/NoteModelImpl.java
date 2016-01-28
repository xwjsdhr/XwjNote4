package com.xwj.xwjnote4.model.impl;

import android.content.Context;

import com.xwj.xwjnote4.model.Note;
import com.xwj.xwjnote4.model.NoteModel;
import com.xwj.xwjnote4.utils.NoteUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;


/**
 * note的数据模型
 * Created by xwjsd on 2015/12/4.
 */
public class NoteModelImpl implements NoteModel {

    private NoteUtil mUtil;

    public NoteModelImpl(Context context) {
        mUtil = NoteUtil.getInstance(context);
    }

    @Override
    public void addNote(Note note) {
        mUtil.addNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        mUtil.deleteNote(note);
    }

    @Override
    public void updateNote(Note note) {
        mUtil.updateNote(note);
    }

    @Override
    public Note getNote(String id) {
        return mUtil.getNote(id);
    }

    @Override
    public ArrayList<Note> getAllNotes() {
        return mUtil.getAll();
    }

    @Override
    public void moveToTrashBin(Note note) {
        note.setNoteType(NoteUtil.NOTE_TYPE_TRASH);
        mUtil.updateNote(note);
    }

    @Override
    public ArrayList<Note> getNotes(String type) {
        return mUtil.getNotes(type);
    }

    @Override
    public ArrayList<Note> getNotesByTitle(String title, boolean b) {
        return mUtil.getNotesByTitle(title, b);
    }

    @Override
    public void restoreNote(Note note) {
        note.setNoteType(NoteUtil.NOTE_TYPE_NORMAL);
        mUtil.updateNote(note);
    }

    @Override
    public ArrayList<Note> getAllFavoriteNotes() {
        return mUtil.getAllFavoriteNotes();
    }

    @Override
    public void clearAllTrash() {
        mUtil.clearAllTrash();
    }

    @Override
    public List<Note> addAllNotes(List<Note> list) {
        return mUtil.addAllNotes(list);

    }

    @Override
    public void moveToSecurity(Note note) {
        note.setNoteType(NoteUtil.NOTE_TYPE_SECURITY);
        mUtil.updateNote(note);
    }

    @Override
    public void addNoteList(List<Note> list) {
        mUtil.allNoteList(list);
    }

    @Override
    public List<BmobObject> getAllUnSyncList() {
        //List<BmobObject> list = new ArrayList<>();


        return null;
    }

    @Override
    public int getNotesCount(String type) {
        return mUtil.getNotesCount(type);
    }
}
