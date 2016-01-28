package com.xwj.xwjnote4.utils;

import android.content.Context;

import com.xwj.xwjnote4.model.Note;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by xwjsd on 2016-01-18.
 */
public class FileUtils {
    private static FileUtils ourInstance = new FileUtils();
    private static Context mContext;

    public static FileUtils getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    private FileUtils() {
    }

    public void list2File(String fileName, ArrayList<Note> list) throws IOException {
        File filesDir = mContext.getFilesDir();
        File f = new File(filesDir, fileName);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(f));
        if (f.exists()) {
            oos.reset();
        }
        oos.writeObject(list);
        oos.flush();
        oos.close();

    }

    public ArrayList<Note> file2List(String fileName) throws IOException, ClassNotFoundException {
        File filesDir = mContext.getFilesDir();
        File f = new File(filesDir, fileName);
        ObjectInputStream ois = null;
        ois = new ObjectInputStream(new FileInputStream(f));
        return (ArrayList<Note>) ois.readObject();
    }

    public long getFileSize(String fileName) {
        File filesDir = mContext.getFilesDir();
        File file = new File(filesDir, fileName);
        if (file.isFile() && file.exists()) {
            return file.getFreeSpace();
        }
        return 0;
    }

    public String getFileDir(String fileName) {
        File filesDir = mContext.getFilesDir();
        File file = new File(filesDir, fileName);
        if (file.isFile() && file.exists()) {
            return  file.getName();
        }
        return null;
    }
}
