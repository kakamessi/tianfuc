package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class MusicActivity extends BaseMidiActivity {

    //默认伴弹状态，  0伴弹，播放   1跟弹，
    int state = 0;

    Button btn_play = null;
    Button btn_pause = null;

    Button btn_banzou = null;
    Button btn_gentan = null;

    Button btn_startAB = null;
    Button btn_endAB = null;

    Button btn_lefthand = null;
    Button btn_righthand = null;

    Button[] mBtns = null;

    String fileName = "";
    String filePath = "";
    String fileDir = "";
    String fileUrl = "";

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
        btn_pause = (Button) vc.findViewById(R.id.button1);
        btn_play = (Button) vc.findViewById(R.id.button2);

        btn_startAB = (Button) vc.findViewById(R.id.button3);
        btn_endAB = (Button) vc.findViewById(R.id.button4);

        btn_banzou = (Button) vc.findViewById(R.id.button5);
        btn_gentan = (Button) vc.findViewById(R.id.button6);

        btn_lefthand = (Button) vc.findViewById(R.id.button7);
        btn_righthand = (Button) vc.findViewById(R.id.button8);
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
        //伴弹
        btn_banzou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBanZou(0);
            }
        });
        //跟弹
        btn_gentan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGenTan(0);
            }
        });
        btn_lefthand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setHand(1);
            }
        });
        btn_righthand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setHand(0);
            }
        });

        mFrameLayout.addView(vc, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 150, Gravity.BOTTOM));

        mBtns = new Button[]{btn_play,btn_pause,
                btn_banzou,btn_gentan,
                btn_lefthand,btn_righthand,btn_startAB,btn_endAB};


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

        if(player!=null){
            player.pause();
            player = null;
        }
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    //设置伴奏模式
    public void setBanZou(int hand){
        if(null!=player){
            state = 0;
            player.setHand(2);
            player.setMode(0);
        }
    }

    //设置跟弹模式
    public void setGenTan(int hand){
        if(null!=player){
            state = 1;
            player.setHand(2);
            player.setMode(1);
        }
    }

    //开始模式
    public void mPlay(){
        if(null!=player){
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
            player.unsetAB();
            setBtnEnable(null);
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

                if(state==0) {
                    //midi发声
                    midiO.sendMidiNoteOn(0x00, (byte) 0x00, note, 0x32);
                    if(ison==1){
                        led.setLedStatus(true,true,note);
                    }else{
                        led.setLedStatus(true,false,note);
                    }


                }else if(state==1){
                    //亮灯
                    if(ison==1){
                        led.setLedStatus(true,true,note);
                    }else{
                        led.setLedStatus(true,false,note);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_righthand.setText(note + "-" + state );
                    }
                });


            }
            public void onEnd() {
                Log.e("musicscoreplayer", ">---<backonend");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFinishedState();
                        btn_play.setText("onEnd-");
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
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                btn_lefthand.setText("" + note);
//            }
//        });
    }

    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOff(sender, cable, channel, note, velocity);
        player.sendMidi(0,note,0);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                btn_lefthand.setText("" + note);
//            }
//        });
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


}


