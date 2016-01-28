package com.xwj.xwjnote4.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

/**
 * 常用工具类
 * Created by xwjsd on 2015/12/4.
 */
public class CommonUtils {
    /**
     * 生成UUid
     *
     * @return
     */
    public static String getGuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取指定日期对应的相对日期。
     * @param context
     * @param date
     * @return
     */
    public static String getDate(Context context, Date date) {
        return DateUtils.getRelativeDateTimeString(context, date.getTime()
                , DateUtils.MINUTE_IN_MILLIS
                , DateUtils.DAY_IN_MILLIS
                , DateUtils.FORMAT_ABBREV_ALL
        ).toString();
    }

    /**
     * 获取Toast
     * @param mContext
     * @param msg
     */
    public static void mkTst(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
