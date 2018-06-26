package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.chico.common.CacheUtils;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BaseMidiActivity;
import com.piano.android.common.Constant;
import com.piano.android.common.DownloadFileUtil;
import com.piano.android.common.pianoled.Iled;
import com.piano.android.common.pianoled.OulaikeLedImpl;
import com.piano.android.widget.LoadingDialog;
import com.wanaka.musiccore.app.MusicScorePlayer;

import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;


/*
*
*       1，初始化 播放状态   需要点击开始
*
*       2， 任意状态间切换模式后 需要再次点击开始
*
*       3， 暂停按钮用于 停止播放模式，  其他模式下调用无影响
*
*
*
* */
public class MusicActivity extends BaseMidiActivity{

    // 1 左手 0 右手
    int handCode = 2;
    // 0 伴奏  1跟奏
    int typeCode = 0;


    //---------跟弹 伴弹
    Button button_finish = null;

    Button btn_play = null;
    Button btn_pause = null;
    Button btn_startAB = null;
    Button btn_endAB = null;
    Button[] mBtns = null;

    //---------左手 右手
    CheckBox cb_bantan = null;
    CheckBox cb_gentan = null;
    CheckBox cb_left = null;
    CheckBox cb_right = null;
    CheckBox cb_both = null;
    CheckBox[] boxType = new CheckBox[2];
    CheckBox[] handType = new CheckBox[3];

    //--------播放参数
    String fileName = "";
    String filePath = "";
    String fileDir = "";
    String fileUrl = "";

    //---------- 渲染
    MusicScorePlayer player;
    MidiOutputDevice midiO;
    Iled led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileName = getIntent().getStringExtra(Constant.INTENT_FILE_NAME);
        filePath = CacheUtils.getFilePath(this, Constant.CACHE_FILE_DIR) + "/" + fileName;
        fileDir = CacheUtils.getFilePath(this, Constant.CACHE_FILE_DIR);
        fileUrl = BuildConfig.BASE_FILE_URL + fileName;

        player = new MusicScorePlayer();

        initMidi();
        initView();

    }

    public void initView(){

        ViewGroup vc = (ViewGroup) this.getLayoutInflater().inflate(R.layout.activity_note, null);

        cb_bantan = (CheckBox) vc.findViewById(R.id.cb_bantan);
        cb_gentan = (CheckBox) vc.findViewById(R.id.cb_gentan);
        cb_left = (CheckBox) vc.findViewById(R.id.cb_left);
        cb_right = (CheckBox) vc.findViewById(R.id.cb_right);
        cb_both = (CheckBox) vc.findViewById(R.id.cb_both);

        CheckLisner1 c1 = new CheckLisner1();
        CheckLisner2 c2 = new CheckLisner2();
        cb_bantan.setOnCheckedChangeListener(c1);
        cb_gentan.setOnCheckedChangeListener(c1);
        cb_left.setOnCheckedChangeListener(c2);
        cb_right.setOnCheckedChangeListener(c2);
        cb_both.setOnCheckedChangeListener(c2);

        boxType[0] = cb_bantan;
        boxType[1] = cb_gentan;
        handType[0] = cb_left;
        handType[1] = cb_right;
        handType[2] = cb_both;

        btn_pause = (Button) vc.findViewById(R.id.button1);
        btn_play = (Button) vc.findViewById(R.id.button2);
        button_finish = (Button) vc.findViewById(R.id.button_finish);
        btn_startAB = (Button) vc.findViewById(R.id.button3);
        btn_endAB = (Button) vc.findViewById(R.id.button4);

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCosumenBackKey();
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPause();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlay();
            }
        });
        btn_startAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSetAB();
            }
        });
        btn_endAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEndAB();
            }
        });

        mFrameLayout.addView(vc, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 150, Gravity.BOTTOM));

        mBtns = new Button[]{btn_play,btn_pause};


        showLoading();
        downloadFile();
    }

    private void downloadFile() {

        if(CacheUtils.fileIsExists(filePath)){
            initPlayer();
            dismissLoading();
            return;
        }

        DownloadFileUtil.getInstance().download(fileUrl, fileDir,fileName, new DownloadFileUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                initPlayer();
                dismissLoading();
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadFailed() {
                dismissLoading();
                MusicActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //开始模式
    public void mPlay(){
        if(null!=player){
            player.setHand(handCode);
            player.setMode(typeCode);
            player.play();
            setBtnEnable(btn_pause);
        }
    }

    //暂停
    public void mPause(){
        if(null!=player){
            player.pause();
            setBtnEnable(null);
        }
    }

    public void mSetAB(){
        if(null!=player){
            player.setAB();
            setBtnEnable(btn_endAB);
        }
    }

    public void mEndAB(){
        if(null!=player){
//            player.unsetAB();
//            setBtnEnable(null);

            if(player!=null){
                player.pause();
                player = null;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.finish();

        }
    }

    public void setFinishedState(){
        setBtnEnable(null);
    }

    void initPlayer() {

        //播放类 用于曲谱显示相关功能性实现 实时处理音频 同步曲谱数据
        player.init();

        //设置播放类的监听，需要开线程，自定义状态，比如在分析模式mode3下不断检测pause状态,触发analyse类的调用分析
        player.setListener(new MusicScorePlayer.MusicScorePlayerListener() {
            public void onABOK() {
                Log.e("musicscoreplayer", ">---<backa");

            }
            public void onMidiRecieved(final int note, final int ison) {
                Log.e("musicscoreplayer", ">---<backMidiRecieved:" + note);

                if(midiO==null){
                    return;
                }

                if(typeCode==0) {
                    //midi发声
                    if(ison==1) {
                        midiO.sendMidiNoteOn(0x00, (byte) 0x00, note, 0x32);
                    }

                    if(ison==1){
                        led.setLedStatus(true,true,note);
                    }else{
                        led.setLedStatus(true,false,note);
                    }


                }else if(typeCode==1){
                    //亮灯
                    if(ison==1){
                        led.setLedStatus(true,true,note);
                    }else{
                        led.setLedStatus(true,false,note);
                    }
                }


            }
            public void onEnd() {
                Log.e("musicscoreplayer", ">---<backonend");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFinishedState();
                    }
                });
            }
        });

        player.load(filePath, 0, 150);

        //0右手 //1左手 // 2 双手（默认双手）
        player.setHand(2);
        //0 演奏 伴奏  1 跟弹
        player.setMode(0);
        player.setTempo(1);

    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
        super.onMidiOutputDeviceAttached(midiOutputDevice);
        midiO = midiOutputDevice;
        led = new OulaikeLedImpl(midiO);
        led.keepAlive();

    }

    @Override
    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOn(sender, cable, channel, note, velocity);

        player.sendMidi(0,note,50);

    }

    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOff(sender, cable, channel, note, velocity);

        player.sendMidi(0,note,0);

    }

    private void setBtnEnable(Button... btns){

        if(null==btns){
            for(Button btn1: mBtns){
                btn1.setEnabled(true);
            }

            btn_startAB.setEnabled(false);
            btn_endAB.setEnabled(false);
            return;
        }

        for(Button btn : btns){
            for(Button btn1: mBtns){
                if(btn.getId()==btn1.getId()){
                    btn1.setEnabled(true);
                }else{
                    btn1.setEnabled(false);
                }
            }
        }

        btn_startAB.setEnabled(false);
        btn_endAB.setEnabled(false);

        btn_endAB.setEnabled(true);
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

    class CheckLisner1 implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                for (int i = 0; i < boxType.length; i++) {
                    //不等于当前选中的就变成false
                    if (boxType[i].getText().toString().equals(compoundButton.getText().toString())) {
                        if(i==0){
                            typeCode = 0;
                        }else{
                            typeCode = 1;
                        }
                    }else{
                        boxType[i].setChecked(false);
                    }
                }
            }
        }
    }

    class CheckLisner2 implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                for (int i = 0; i < handType.length; i++) {
                    //不等于当前选中的就变成false
                    if (handType[i].getText().toString().equals(compoundButton.getText().toString())) {
                        if(i==0){
                            handCode = 1;
                        }else if(i==1){
                            handCode = 0;
                        }else{
                            handCode = 2;
                        }
                    }else{
                        handType[i].setChecked(false);
                    }
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
//do something
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isCosumenBackKey() {
        // 这儿做返回键的控制，如果自己处理返回键逻辑就返回true，如果返回false,代表继续向下传递back事件，由系统去控制
        if(player!=null){
            player.pause();
            player = null;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.finish();

        return true;
    }




}


