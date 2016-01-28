package com.xwj.xwjnote4.widget;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.xwj.xwjnote4.R;

/**
 * Created by xwjsd on 2016-01-13.
 */
public class LayoutPickerPreference extends DialogPreference {


    public LayoutPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.layout_picker_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
    }

}
