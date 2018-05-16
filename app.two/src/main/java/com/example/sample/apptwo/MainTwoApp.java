/*
 * Copyright (C) 2005-2017 Qihoo 360 Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.sample.apptwo;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;

/**
 * @author RePlugin Team
 */
public class MainTwoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RePlugin.registerPluginBinder("demo2test", new Demo2Impl());
    }

    public static void helloFromAppOne(Context c, String text) {
        Toast.makeText(c, "AppTwo: say hello! " + text, Toast.LENGTH_SHORT).show();
    }
}
