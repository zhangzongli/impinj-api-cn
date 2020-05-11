package com.example.sdksamples;

import com.impinj.octane.AntennaChangeListener;
import com.impinj.octane.AntennaEvent;
import com.impinj.octane.ImpinjReader;


public class AntennaChangeListenerImplementation implements
        AntennaChangeListener {

    // 天线更换监听事件
    @Override
    public void onAntennaChanged(ImpinjReader reader, AntennaEvent e) {
        System.out.println("Antenna Change--port: " + e.getPortNumber()
                + " state: " + e.getState().toString());
    }
}
