package com.xwj.xwjnote4.Iterator;

/**
 * Created by xwjsd on 2016-01-18.
 */
public interface BackUpIterator {
    void backUp2Disk(BackUpListener listener);

    void recover(BackUpListener listener);
}
