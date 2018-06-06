package com.piano.android.base;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import jp.kshoji.driver.midi.device.MidiDeviceConnectionWatcher;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import jp.kshoji.driver.midi.listener.OnMidiDeviceAttachedListener;
import jp.kshoji.driver.midi.listener.OnMidiDeviceDetachedListener;
import jp.kshoji.driver.midi.listener.OnMidiInputEventListener;

public abstract class BaseMidiActivity extends BaseActivity implements OnMidiDeviceDetachedListener, OnMidiDeviceAttachedListener, OnMidiInputEventListener {

    /**
     * Implementation for single device connections.
     *
     * @author K.Shoji
     */
    final class OnMidiDeviceAttachedListenerImpl implements OnMidiDeviceAttachedListener {

        @Override
        public void onDeviceAttached(@NonNull UsbDevice usbDevice) {
            // deprecated method.
            // do nothing
        }

        @Override
        public void onMidiInputDeviceAttached(@NonNull final MidiInputDevice midiInputDevice) {
            if (BaseMidiActivity.this.midiInputDevice != null) {
                return;
            }
            midiInputDevice.setMidiEventListener(BaseMidiActivity.this);
            BaseMidiActivity.this.midiInputDevice = midiInputDevice;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseMidiActivity.this.onMidiInputDeviceAttached(midiInputDevice);
                }
            });
        }

        @Override
        public void onMidiOutputDeviceAttached(@NonNull final MidiOutputDevice midiOutputDevice) {
            if (BaseMidiActivity.this.midiOutputDevice != null) {
                return;
            }

            BaseMidiActivity.this.midiOutputDevice = midiOutputDevice;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseMidiActivity.this.onMidiOutputDeviceAttached(midiOutputDevice);
                }
            });
        }
    }

    /**
     * Implementation for single device connections.
     *
     * @author K.Shoji
     */
    final class OnMidiDeviceDetachedListenerImpl implements OnMidiDeviceDetachedListener {

        @Override
        public void onDeviceDetached(@NonNull UsbDevice usbDevice) {
            // deprecated method.
            // do nothing
        }

        @Override
        public void onMidiInputDeviceDetached(@NonNull final MidiInputDevice midiInputDevice) {
            if (BaseMidiActivity.this.midiInputDevice != null && BaseMidiActivity.this.midiInputDevice == midiInputDevice) {
                BaseMidiActivity.this.midiInputDevice = null;
            }
            midiInputDevice.setMidiEventListener(null);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseMidiActivity.this.onMidiInputDeviceDetached(midiInputDevice);
                }
            });
        }

        @Override
        public void onMidiOutputDeviceDetached(@NonNull final MidiOutputDevice midiOutputDevice) {
            if (BaseMidiActivity.this.midiOutputDevice != null && BaseMidiActivity.this.midiOutputDevice == midiOutputDevice) {
                BaseMidiActivity.this.midiOutputDevice = null;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseMidiActivity.this.onMidiOutputDeviceDetached(midiOutputDevice);
                }
            });
        }
    }

    MidiInputDevice midiInputDevice = null;
    MidiOutputDevice midiOutputDevice = null;
    OnMidiDeviceAttachedListener deviceAttachedListener = null;
    OnMidiDeviceDetachedListener deviceDetachedListener = null;
    private MidiDeviceConnectionWatcher deviceConnectionWatcher = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initMidi(){
        UsbManager usbManager = (UsbManager) getApplicationContext().getSystemService(Context.USB_SERVICE);
        deviceAttachedListener = new BaseMidiActivity.OnMidiDeviceAttachedListenerImpl();
        deviceDetachedListener = new BaseMidiActivity.OnMidiDeviceDetachedListenerImpl();
        deviceConnectionWatcher = new MidiDeviceConnectionWatcher(getApplicationContext(), usbManager, deviceAttachedListener, deviceDetachedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (deviceConnectionWatcher != null) {
            deviceConnectionWatcher.stop();
        }
        deviceConnectionWatcher = null;

        midiInputDevice = null;
        midiOutputDevice = null;
    }


    /**
     * Suspends receiving/transmitting MIDI messages.
     * All events will be discarded until the devices being resumed.
     */
    protected final void suspendMidiDevices() {
        if (midiInputDevice != null) {
            midiInputDevice.suspend();
        }

        if (midiOutputDevice != null) {
            midiOutputDevice.suspend();
        }
    }

    /**
     * Resumes from {@link #suspendMidiDevices()}
     */
    protected final void resumeMidiDevices() {
        if (midiInputDevice != null) {
            midiInputDevice.resume();
        }

        if (midiOutputDevice != null) {
            midiOutputDevice.resume();
        }
    }

    /**
     * Get MIDI output device, if available.
     *
     * @return MidiOutputDevice, null if not available
     */
    @Nullable
    public final MidiOutputDevice getMidiOutputDevice() {
        if (deviceConnectionWatcher != null) {
            deviceConnectionWatcher.checkConnectedDevicesImmediately();
        }

        return midiOutputDevice;
    }

    @Override
    public void onMidiRPNReceived(@NonNull MidiInputDevice sender, int cable, int channel, int function, int valueMSB, int valueLSB) {
        // do nothing in this implementation
    }

    @Override
    public void onMidiNRPNReceived(@NonNull MidiInputDevice sender, int cable, int channel, int function, int valueMSB, int valueLSB) {
        // do nothing in this implementation
    }

    @Override
    public void onMidiRPNReceived(@NonNull MidiInputDevice sender, int cable, int channel, int function, int value) {
        // do nothing in this implementation
    }

    @Override
    public void onMidiNRPNReceived(@NonNull MidiInputDevice sender, int cable, int channel, int function, int value) {
        // do nothing in this implementation
    }


    //===============================================================
    @Override
    public void onDeviceAttached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceAttached(@NonNull MidiInputDevice midiInputDevice) {

    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {

    }

    @Override
    public void onDeviceDetached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceDetached(@NonNull MidiInputDevice midiInputDevice) {

    }

    @Override
    public void onMidiOutputDeviceDetached(@NonNull MidiOutputDevice midiOutputDevice) {

    }

    @Override
    public void onMidiMiscellaneousFunctionCodes(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiCableEvents(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiSystemCommonMessage(@NonNull MidiInputDevice sender, int cable, byte[] bytes) {

    }

    @Override
    public void onMidiSystemExclusive(@NonNull MidiInputDevice sender, int cable, byte[] systemExclusive) {

    }

    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {

    }

    @Override
    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {

    }

    @Override
    public void onMidiPolyphonicAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int note, int pressure) {

    }

    @Override
    public void onMidiControlChange(@NonNull MidiInputDevice sender, int cable, int channel, int function, int value) {

    }

    @Override
    public void onMidiProgramChange(@NonNull MidiInputDevice sender, int cable, int channel, int program) {

    }

    @Override
    public void onMidiChannelAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int pressure) {

    }

    @Override
    public void onMidiPitchWheel(@NonNull MidiInputDevice sender, int cable, int channel, int amount) {

    }

    @Override
    public void onMidiSingleByte(@NonNull MidiInputDevice sender, int cable, int byte1) {

    }

    @Override
    public void onMidiTimeCodeQuarterFrame(@NonNull MidiInputDevice sender, int cable, int timing) {

    }

    @Override
    public void onMidiSongSelect(@NonNull MidiInputDevice sender, int cable, int song) {

    }

    @Override
    public void onMidiSongPositionPointer(@NonNull MidiInputDevice sender, int cable, int position) {

    }

    @Override
    public void onMidiTuneRequest(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiTimingClock(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiStart(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiContinue(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiStop(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiActiveSensing(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiReset(@NonNull MidiInputDevice sender, int cable) {

    }

}
