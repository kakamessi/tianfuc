package com.piano.android.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.piano.android.R;
import com.wanaka.musiccore.app.MusicCoreActivity;
import com.wanaka.musiccore.app.MusicScorePlayer;

public class MusicActivity extends MusicCoreActivity {


    String path = "";
    MusicScorePlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new MusicScorePlayer();
        path = Environment.getExternalStorageDirectory().getPath() + "/zz3.xml";


        ViewGroup vc = (ViewGroup) this.getLayoutInflater().inflate(R.layout.activity_note, null);
        Button b1 = (Button) vc.findViewById(R.id.button1);
        Button b2 = (Button) vc.findViewById(R.id.button2);
        Button b3 = (Button) vc.findViewById(R.id.button3);

        Button b4 = (Button) vc.findViewById(R.id.button4);
        Button b5 = (Button) vc.findViewById(R.id.button5);
        Button b6 = (Button) vc.findViewById(R.id.button6);

        Button b7 = (Button) vc.findViewById(R.id.button7);
        Button b8 = (Button) vc.findViewById(R.id.button8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.play();
                Toast.makeText(MusicActivity.this, "player.play();  播放", 0).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setAB();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.unsetAB();
            }
        });
        //伴弹
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player.setMode(0);
                Toast.makeText(MusicActivity.this, "player.setMode(2);;  伴弹", 0).show();

            }
        });
        //跟弹
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setMode(1);
                Toast.makeText(MusicActivity.this, "player.setMode(1);;  跟弹", 0).show();

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player.setHand(1);
                Toast.makeText(MusicActivity.this, "player.setHand(0);  左手", 0).show();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MusicActivity.this, "player.setHand(0);  右手", 0).show();
                player.setHand(0);

            }
        });

        mFrameLayout.addView(vc, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 250, Gravity.BOTTOM));
        pp();


    }


    void pp() {
        //播放类 用于曲谱显示相关功能性实现 实时处理音频 同步曲谱数据
        player.init();

        //设置播放类的监听，需要开线程，自定义状态，比如在分析模式mode3下不断检测pause状态,触发analyse类的调用分析
        player.setListener(new MusicScorePlayer.MusicScorePlayerListener() {
            public void onABOK() {
                Log.e("musicscoreplayer", ">---<backa");
            }
            public void onMidiRecieved(int note, int ison) {
                Log.e("musicscoreplayer", ">---<backMidiRecieved:" + note);
            }
            public void onEnd() {
                Log.e("musicscoreplayer", ">---<backonend");
            }
        });

        player.load(path, 0, 200);

        //0右手 //1左手 // 2 双手（默认双手）
        //player.setHand(0);

        //0 演奏 伴奏
        player.setMode(0);
        player.setTempo(1);
        player.play();

    }






}
