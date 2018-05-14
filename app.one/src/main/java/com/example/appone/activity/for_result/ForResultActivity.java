package com.example.appone.activity.for_result;

import android.content.Intent;
import android.os.Bundle;

import com.qihoo360.replugin.loader.a.PluginActivity;

/**
 * @author RePlugin Team
 */
public class ForResultActivity extends PluginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.putExtra("data", "data from app one plugin, resultCode is 0x012");
        setResult(0x012, intent);
        finish();
    }
}
