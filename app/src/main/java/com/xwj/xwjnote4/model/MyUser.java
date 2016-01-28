package com.xwj.xwjnote4.model;

import cn.bmob.v3.BmobUser;

/**
 *
 * Created by xwjsd on 2016-01-12.
 */
public class MyUser extends BmobUser {

    private String userIconUrl;

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

}
