package com.example.appone;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appone.activity.file_provider.FileProviderActivity;
import com.example.appone.activity.notify_test.NotifyActivity;
import com.example.appone.activity.preference.PrefActivity2;
import com.example.appone.activity.single_instance.TIActivity;
import com.example.appone.activity.single_top.SingleTopActivity;
import com.example.appone.activity.standard.StandardActivity;
import com.example.appone.activity.task_affinity.TAActivity1;
import com.example.appone.activity.theme.ThemeBlackNoTitleBarActivity;
import com.example.appone.activity.theme.ThemeBlackNoTitleBarFullscreenActivity;
import com.example.appone.activity.theme.ThemeDialogActivity;
import com.example.appone.activity.webview.WebViewActivity;
import com.example.appone.service.PluginDemoService1;
import com.example.appone.support.NotifyUtils;
import com.example.lib.common.TimeUtils;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.sample.libappone.LibMainActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.example.appone.support.NotifyUtils.ACTION_START_NOTIFY_UI;
import static com.example.appone.support.NotifyUtils.NOTIFY_KEY;

/**
 * 内置插件
 */
public class MainAppOneActivity extends AppCompatActivity {
    private static final String TAG = MainAppOneActivity.class.getSimpleName();

    private static final int REQUEST_CODE_APP_ONE = 0x021;
    private static final int RESULT_CODE_APP_ONE = 0x022;

    private List<TestItem> mItems = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_activity_main);

        initParamsAndValues();

        initViews();

    }

    private void initParamsAndValues() {
        mContext = this;

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_START_NOTIFY_UI);
        registerReceiver(mIntentReceiver, filter);

        initData();
    }

    private void initViews() {

        final ListView listView = findViewById(R.id.listview_main);
        listView.setAdapter(new TestAdapter());
    }

    private void initData() {
        //===================================TO host=====================================//
        mItems.add(new TestItem("Jump to Host App", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开宿主Activity(app中的)
                Intent intent = new Intent();
                intent.setClassName("com.example.repluginsample", "com.example.repluginsample.PluginFragmentActivity");
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: start Host activity(Main)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(RePlugin.getHostContext().getPackageName(), "com.qihoo360.replugin.sample.host.MainActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }));

        //==================================in app one====================================//
        mItems.add(new TestItem("Activity: Standard", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StandardActivity.class);
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: Theme BlackNoTitleBar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThemeBlackNoTitleBarActivity.class);
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: Theme BlackNoTitleBarFullscreen", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThemeBlackNoTitleBarFullscreenActivity.class);
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: Theme Dialog", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThemeDialogActivity.class);
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: SingleTop", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SingleTopActivity.class);
                v.getContext().startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: SingleInstance", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TIActivity.class);
                startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: Task Affinity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TAActivity1.class);
                v.getContext().startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: Open in Application", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context app = getApplicationContext();
                Intent intent = new Intent(app, ThemeDialogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                app.startActivity(intent);
            }
        }));

        mItems.add(new TestItem("Activity: By Intent Filter", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.appone.intent_filter");
                intent.addCategory("category_demo");
                //Factory.startActivity(context, intent, "", "", IPluginManager.PROCESS_AUTO);
                v.getContext().startActivity(intent);
            }
        }));
        mItems.add(new TestItem("Activity: By Action", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.appone.action.theme_fullscreen");
                /*
                    若 Intent 中声明 category, manifest 中未声明，则无法找到 Activity;
                    若 manifest 中声明 category, Intent 中未声明，则可以找到 Activity;
                */
                intent.addCategory("com.example.appone.CATEGORY1");
                v.getContext().startActivity(intent);
            }
        }));

        //=======================================TO other plugin===============================//
        mItems.add(new TestItem("Activity: AppCompat (to Demo2)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RePlugin.startActivity(v.getContext(), new Intent(), "demo2", "com.qihoo360.replugin.sample.demo2.activity.appcompat.AppCompatActivityDemo");
            }
        }));

        mItems.add(new TestItem("Activity: DataBinding (to Demo2)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RePlugin.startActivity(v.getContext(), new Intent(), "demo2", "com.qihoo360.replugin.sample.demo2.databinding.DataBindingActivity");
            }
        }));
        mItems.add(new TestItem("Activity: startForResult (to Demo2)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("demo2", "com.qihoo360.replugin.sample.demo2.activity.for_result.ForResultActivity"));
                startActivityForResult(intent, REQUEST_CODE_APP_ONE);
                // 也可以这么用
                // RePlugin.startActivityForResult(MainActivity.this, intent, REQUEST_CODE_APP_ONE);
            }
        }));

        mItems.add(new TestItem("Activity: By Action (to Demo2)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.qihoo360.replugin.sample.demo2.action.theme_fullscreen_2");
                RePlugin.startActivity(v.getContext(), intent, "demo2", null);
            }
        }));


        //======================================= Other Components==============================//
        mItems.add(new TestItem("Broadcast: Send (to All)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.qihoo360.repluginapp.replugin.receiver.ACTION1");
                intent.putExtra("name", "jerry");
                sendBroadcast(intent);
            }
        }));
        mItems.add(new TestItem("Service: Start (at :p0 process)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PluginDemoService1.class);
                intent.setAction("action1");
                startService(intent);
            }
        }));
        mItems.add(new TestItem("Service: Implicit Start (at :UI process)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setPackage("com.example.appone");
                intent.setAction("com.example.appone.service.action.XXXX");
                startService(intent);
            }
        }));
        mItems.add(new TestItem("Provider: Query (at UI process)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.qihoo360.replugin.sample.demo1.provider2/" + "test");

                ContentValues cv = new ContentValues();
                cv.put("name", "RePlugin Team");
                cv.put("address", "beijing");

                Uri urii = v.getContext().getContentResolver().insert(uri, cv);
                Log.d("a4", "result=" + urii);
                if (urii != null) {
                    Toast.makeText(v.getContext(), urii.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "null", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        // ===================================Communication===================================//
        mItems.add(new TestItem("Use Host Method: Direct use (Ex. TimeUtils)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 此为RePlugin的另一种做法，可直接调用宿主的Utils
                // 虽然不是很推荐（版本控制问题，见FAQ），但毕竟需求较大，且我们是“相对安全的共享代码”方案，故可以使用
                final String curTime = TimeUtils.getNowString();
                if (!TextUtils.isEmpty(curTime)) {
                    Toast.makeText(v.getContext(), "current time: " + TimeUtils.getNowString(), Toast.LENGTH_SHORT).show();
                    // 打印其ClassLoader
                    Log.d("MainActivity", "Use Host Method: cl=" + TimeUtils.class.getClassLoader());
                } else {
                    Toast.makeText(v.getContext(), "Failed to obtain current time(from host)", Toast.LENGTH_SHORT).show();
                }
            }
        }));
        mItems.add(new TestItem("Use Demo2 Method: Reflection (Recommend)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这是RePlugin的推荐玩法：反射调用Demo2，这样"天然的"做好了"版本控制"
                // 避免出现我们当年2013年的各种问题
                ClassLoader cl = RePlugin.fetchClassLoader("demo2");
                if (cl == null) {
                    Toast.makeText(v.getContext(), "Not install Demo2", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Class clz = cl.loadClass("com.qihoo360.replugin.sample.demo2.MainOneApp");
                    Method m = clz.getDeclaredMethod("helloFromDemo1", Context.class, String.class);
                    m.invoke(null, v.getContext(), "Demo1");
                } catch (Exception e) {
                    // 有可能Demo2根本没有这个类，也有可能没有相应方法（通常出现在"插件版本升级"的情况）
                    Toast.makeText(v.getContext(), "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }));
        mItems.add(new TestItem("Resources: Use demo2's layout", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout contentView = RePlugin.fetchViewByLayoutName("demo2", "from_demo1", null);
                if (contentView == null) {
                    Toast.makeText(v.getContext(), "from_demo1 Not Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog d = new Dialog(v.getContext());
                d.setContentView(contentView);
                d.show();
            }
        }));
        mItems.add(new TestItem("Binder: Fast-Fetch (to Demo2)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBinder b = RePlugin.fetchBinder("demo2", "demo2test");
                if (b == null) {
                    return;
                }
                IAppOne iAppOne = IAppOne.Stub.asInterface(b);
                try {
                    iAppOne.hello("helloooooooooooo");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }));

        //===================================aar========================================//
        mItems.add(new TestItem("AAR Activity: Standard", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LibMainActivity.class);
                startActivity(intent);
            }
        }));

        // dump
        mItems.add(new TestItem("dump Detail", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // 打印RePlugin版本号
//                String version = RePlugin.getVersion();
//                Toast.makeText(MainActivity.this, "RePlugin v:" + version, Toast.LENGTH_SHORT).show();
//
//                // dump详细的运行信息到PrintWriter
//                PrintWriter writer = null;
//                try {
//                    writer = new PrintWriter("/sdcard/dump.txt");
//                    RePlugin.dump(null, writer, null);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (writer != null) {
//                        writer.close();
//                    }
//                }
            }
        }));

        //===================================PreferenceActivity================================//
        mItems.add(new TestItem("Preference Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PrefActivity2.class);
                startActivity(intent);
            }
        }));

        // ====================================WebView============================================//
        mItems.add(new TestItem("WebView Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                startActivity(intent);
            }
        }));

        // ====================================FileProvider=======================================//
        mItems.add(new TestItem("FileProvider Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FileProviderActivity.class);
                startActivity(intent);
            }
        }));

        // ====================================Notification=======================================//
        mItems.add(new TestItem("Send Notification", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyUtils.sendNotification(getApplicationContext());
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_APP_ONE && resultCode == RESULT_CODE_APP_ONE) {
            Toast.makeText(this, data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mIntentReceiver);
        super.onDestroy();
    }

    private class TestAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public TestItem getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new Button(mContext);
            }

            TestItem item = getItem(position);
            ((Button) convertView).setText(item.title);
            convertView.setOnClickListener(item.mClickListener);
            return convertView;
        }
    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context ctx, Intent intent) {
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }

            if (ACTION_START_NOTIFY_UI.equals(action)) {
                StartNotifyUI(ctx, intent.getStringExtra(NOTIFY_KEY));
            }
        }
    };

    private void StartNotifyUI(Context ctx, String extra) {
        Toast.makeText(ctx, extra, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, NotifyActivity.class);
        intent.putExtra(NOTIFY_KEY, extra);
        startActivity(intent);
    }
}
