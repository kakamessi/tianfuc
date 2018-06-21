package com.piano.android.common.pianoled;

public interface Iled {

    void setLedStatus(boolean isRed, boolean isOn ,int index);

    void offAll();

    void keepAlive();

}
