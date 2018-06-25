package com.piano.android.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.chico.common.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.di.component.DaggerActivityComponent;
import com.piano.android.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import jp.kshoji.driver.midi.device.MidiDeviceConnectionWatcher;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import jp.kshoji.driver.midi.listener.OnMidiDeviceAttachedListener;
import jp.kshoji.driver.midi.listener.OnMidiDeviceDetachedListener;

/**
 * @author: chicochen
 * @date: 2018/4/21 下午7:32
 * @describe:
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 获取传递数据
     */
    public void getIntentData() {
    }

    /**
     * 获取布局文件ID
     *
     * @return
     */
    public abstract int getContentViewId();

    /**
     * 初始化Activity
     */
    public abstract void initActivity();

    /**
     * dagger2入注
     *
     * @param component
     */
    public void injectActivityComponent(ActivityComponent component) {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        if (getContentViewId() != -1) {
            setContentView(getContentViewId());
        }
        ButterKnife.bind(this);
        ActivityComponent component = DaggerActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .build();
        injectActivityComponent(component);
        setStatusBar();
        initActivity();
    }


    protected void setToolbar(Toolbar toolbar, TextView titleView, String title) {
        setToolbar(toolbar, titleView, title, false);
    }


    protected void setToolbar(Toolbar toolbar, TextView titleView, String title, boolean isReturn) {
        setToolbar(toolbar, titleView, title, false, false);
    }


    protected void setToolbar(Toolbar toolbar, TextView titleView, String title, boolean isReturn, boolean dark) {
        if (toolbar == null) {
            return;
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME);


        if (isReturn && !dark) {
            toolbar.setNavigationIcon(R.drawable.icon_back_light);
        }

        if (isReturn && dark) {
            toolbar.setNavigationIcon(R.drawable.icon_back_dark);
        }

        if (titleView == null) {
            return;
        }

        if (TextUtils.isEmpty(title)) {
            return;
        }

        titleView.setText(title);

    }


    /**
     * 设置状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 1);
        StatusBarUtil.setLightMode(this);
    }

    /**
     * 跳转
     *
     * @param activity
     * @param cls
     */
    protected void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param activity
     * @param cls
     * @param requestCode
     */
    protected void startActivity(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转
     *
     * @param activity
     * @param cls
     * @param bundle
     */
    protected void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 检查权限
     *
     * @param permissions
     */
    protected void checkPermission(String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }

            if (permissions != null && permissionList.size() > 0) {
                ActivityCompat.requestPermissions(this,
                        permissionList.toArray(new String[permissionList.size()])
                        , Constant.REQUEST_CODE_PERMISSION);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != Constant.REQUEST_CODE_PERMISSION) {
            return;
        }

        if (permissions.length <= 0 || grantResults.length <= 0) {
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            if (Manifest.permission.CAMERA.equals(permissions[i])) {
                if (grantResults.length >= i && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShortToast(this, R.string.system_permission_limit);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private LoadingDialog loadingDialog;

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (deviceConnectionWatcher != null) {
            deviceConnectionWatcher.stop();
        }
        deviceConnectionWatcher = null;

    }

    private MidiDeviceConnectionWatcher deviceConnectionWatcher = null;
    protected void requestUsbPermission(){
        UsbManager usbManager = (UsbManager) getApplicationContext().getSystemService(Context.USB_SERVICE);
        OnMidiDeviceAttachedListener lis = new OnMidiDeviceAttachedListener() {
            @Override
            public void onDeviceAttached(@NonNull UsbDevice usbDevice) {
            }
            @Override
            public void onMidiInputDeviceAttached(@NonNull MidiInputDevice midiInputDevice) {
            }
            @Override
            public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
            }
        };
        OnMidiDeviceDetachedListener dlis = new OnMidiDeviceDetachedListener() {
            @Override
            public void onDeviceDetached(@NonNull UsbDevice usbDevice) {
            }
            @Override
            public void onMidiInputDeviceDetached(@NonNull MidiInputDevice midiInputDevice) {
            }
            @Override
            public void onMidiOutputDeviceDetached(@NonNull MidiOutputDevice midiOutputDevice) {
            }
        };
        deviceConnectionWatcher = new MidiDeviceConnectionWatcher(getApplicationContext(), usbManager, lis, dlis);
    }


}
