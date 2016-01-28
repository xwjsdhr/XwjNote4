package com.xwj.xwjnote4.view.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xwj.xwjnote4.R;

/**
 * Created by xwjsd on 2016-01-12.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIvIcon;
    private Button mBtnLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_red_50_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
                overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
            }
        });
        mIvIcon = (ImageView) this.findViewById(R.id.iv_about_icon);
        mBtnLink = (Button) this.findViewById(R.id.btn_about_link);
        mBtnLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnLink) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.baidu.com"));
            startActivity(intent);
        }
    }
}
