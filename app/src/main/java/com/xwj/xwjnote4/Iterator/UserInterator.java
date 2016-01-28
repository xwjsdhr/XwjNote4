package com.xwj.xwjnote4.Iterator;

/**
 * Created by xwjsd on 2016-01-07.
 */
public interface UserInterator {
    void login(String username, String password, OnUserListener listener);

    void register(String username, String password, String password2, OnUserListener listener);
}
