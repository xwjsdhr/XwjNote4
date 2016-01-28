package com.xwj.xwjnote4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by xwjsd on 2016-01-12.
 */
public class ScreenReceiver extends BroadcastReceiver {
    private static final String TAG = ScreenReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e(TAG, "SCREEN_ON");
        }
    }
}
