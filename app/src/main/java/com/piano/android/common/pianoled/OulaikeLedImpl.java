package com.piano.android.common.pianoled;

import jp.kshoji.driver.midi.device.MidiOutputDevice;

public class OulaikeLedImpl implements Iled {

    public static byte[] ACTION_KEEP_ALIVE ={(byte) 0xF0, 0x50, 0x00, 0x00, 0x00, 0x00, (byte) 0xF7};


    MidiOutputDevice midiO;
    BeatThread bt;

    public OulaikeLedImpl(MidiOutputDevice midi) {
        midiO = midi;
    }

    @Override
    public void setLedStatus(boolean isRed, boolean isOn, int index) {
        midiO.sendMidiSystemExclusive(0, getlightCode(index, isRed, isOn));
    }

    @Override
    public void offAll() {

    }


    /**
     * F0 4D 4C 4C 45 15 01 F7    键盘左起第一键对应的红灯亮
     * F0 4D 4C 4C 45 15 00 F7    键盘左起第一键对应的红灯熄灭
     * F0 4D 4C 4C 45 15 11 F7    键盘左起第一键对应的蓝灯亮
     * F0 4D 4C 4C 45 15 10 F7    键盘左起第一键对应的蓝灯熄灭
     *
     * @param note(21 - 108)
     * @param isRed
     * @return
     */
    private byte[] getlightCode(int note, boolean isRed, boolean isOn) {

        byte mNote = (byte) note;
        byte mOn;
        if (isRed) {
            if (isOn) {
                mOn = 0x01;
            } else {
                mOn = 0x00;
            }
        } else {
            if (isOn) {
                mOn = 0x11;
            } else {
                mOn = 0x10;
            }
        }

        byte[] codes = {(byte) 0xF0, 0x4D, 0x4C, 0x4C, 0x45, mNote, mOn, (byte) 0xF7};

        return codes;
    }

    @Override
    public void keepAlive(){
        bt = new BeatThread(midiO);
        bt.start();
    }

    public class BeatThread extends Thread{
        private MidiOutputDevice midi;
        public BeatThread(MidiOutputDevice mOutputDevice){
            midi = mOutputDevice;
        }
        public volatile boolean exit = false;
        public void run()
        {
            while (!exit){
                try {
                    Thread.sleep(2000);
                    if(midi!=null) {
                        midi.sendMidiSystemExclusive(0, ACTION_KEEP_ALIVE);
                        //midi.sendMidiNoteOn(0x1b, (byte)0xbF, 0x07, 0x00);

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopBeatThread(){
        if(bt!=null) {
            bt.exit = true;
            bt.interrupt();
            try {
                bt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
