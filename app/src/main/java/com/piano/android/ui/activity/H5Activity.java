package com.piano.android.ui.activity;

import com.piano.android.base.BaseMidiActivity;

public class H5Activity extends BaseMidiActivity {

//
//    public static final String URL_ROOT = BuildConfig.BASE_H5_URL;
//
//
//
//    @BindView(R.id.webView1)
//    WebView mWebview;
//
//    private WebSettings mWebSettings;
//    private MidiOutputDevice mOutputDevice;
//
//    //歌曲名称
//    private String name;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_h5;
//    }
//
//    @Override
//    public void initActivity() {
//        init();
//        initMidi();
//        mOutputDevice = getMidiOutputDevice();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//    }
//
//    @Override
//    public void getIntentData() {
//        super.getIntentData();
//        name = getIntent().getExtras().getString(Constant.INTENT_SONG_NAME);
//    }
//
//    private void init() {
//        mWebSettings = mWebview.getSettings();
//        mWebSettings.setJavaScriptEnabled(true);
//        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        mWebview.addJavascriptInterface(new H5Activity.PianoAction(), "android");
//        mWebview.loadUrl(URL_ROOT);
//        //设置WebChromeClient类
//        mWebview.setWebChromeClient(new WebChromeClient() {
//            //获取网站标题
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//            }
//            //获取加载进度
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//            }
//        });
//        //设置WebViewClient类
//        mWebview.setWebViewClient(new WebViewClient() {
//            //设置加载前的函数
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            }
//            //设置结束加载函数
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if(mWebview!=null){
//                    mWebview.loadUrl("javascript:getOneXml('" + name + "')");
//                }
//            }
//        });
//    }
//
//    //销毁Webview
//    @Override
//    protected void onDestroy() {
//        if (mWebview != null) {
//            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            mWebview.clearHistory();
//
//            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
//            mWebview.destroy();
//            mWebview = null;
//        }
//        super.onDestroy();
//    }
//
//    //--------------------------------------------------------------------------------------------------------
//    @Override
//    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
//        super.onMidiOutputDeviceAttached(midiOutputDevice);
//        Toast.makeText(this, "钢琴已连接", 0).show();
//        mOutputDevice = getMidiOutputDevice();
//
//        //MelodyU.getInstance().startBeatThread(mOutputDevice);
//    }
//
//    //note 21 -108 序号
//    @Override
//    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
//        super.onMidiNoteOn(sender, cable, channel, note, velocity);
//        // 我调用js方法
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mWebview.loadUrl("javascript:backStatusJS('" + note + "')");
//            }
//        });
//    }
//
//
//    public class PianoAction extends Object {
//
//        // 定义JS需要调用的方法  亮灯熄灯
//        // 被JS调用的方法必须加入@JavascriptInterface注解
//        @JavascriptInterface
//        //亮灯
//        public void setLight(int index,int isRed, int isOn){
//
//            boolean iRed = false;
//            boolean iOn = false;
//
//            if(isRed==1){
//                iRed = true;
//            }else{
//                iRed = false;
//            }
//
//            if(isOn==1){
//                iOn = true;
//            }else{
//                iOn = false;
//            }
//
//            if(null!=mOutputDevice) {
//                mOutputDevice.sendMidiSystemExclusive(0, getlightCode(index, iRed, iOn));
//            }
//
//        }
//    }
//
//
//    /**
//     * F0 4D 4C 4C 45 15 01 F7    键盘左起第一键对应的红灯亮
//     * F0 4D 4C 4C 45 15 00 F7    键盘左起第一键对应的红灯熄灭
//     * F0 4D 4C 4C 45 15 11 F7    键盘左起第一键对应的蓝灯亮
//     * F0 4D 4C 4C 45 15 10 F7    键盘左起第一键对应的蓝灯熄灭
//     *
//     * @param note(21 - 108)
//     * @param isRed
//     * @return
//     */
//    public static byte[] getlightCode(int note, boolean isRed, boolean isOn) {
//
//        byte mNote = (byte) note;
//        byte mOn;
//        if (isRed) {
//            if (isOn) {
//                mOn = 0x01;
//            } else {
//                mOn = 0x00;
//            }
//        } else {
//            if (isOn) {
//                mOn = 0x11;
//            } else {
//                mOn = 0x10;
//            }
//        }
//
//        byte[] codes = {(byte) 0xF0, 0x4D, 0x4C, 0x4C, 0x45, mNote, mOn, (byte) 0xF7};
//
//        return codes;
//    }
//



}


