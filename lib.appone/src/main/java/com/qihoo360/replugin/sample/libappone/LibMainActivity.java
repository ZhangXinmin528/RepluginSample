package com.qihoo360.replugin.sample.libappone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ZhangXinmin on 2018/5/14.
 * Copyright (c) 2018 . All rights reserved.
 * activity in lib for app one.
 */
public class LibMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_activity_main);
    }
}
