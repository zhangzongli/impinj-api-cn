package com.impinj;

import com.impinj.octane.ImpinjReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 写标签并且锁标签
 */
public class WriteTagsAndLock {

    private static final Logger log = LoggerFactory.getLogger(WriteTagsAndLock.class);

    public static void main(String[] args) {
        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
