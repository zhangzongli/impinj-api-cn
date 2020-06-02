package com.impinj;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.SearchMode;
import com.impinj.octane.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 写标签
 */
public class WriteTags {

    private static final Logger log = LoggerFactory.getLogger(WriteTags.class);

    public static void main(String[] args) {
        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();

            Settings settings = impinjReader.queryDefaultSettings();

            settings.getAntennas().disableAll();
            settings.getAntennas().getAntenna((short) 1).setEnabled(true);

            settings.getReport().setIncludeAntennaPortNumber(true);
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);
            settings.setSearchMode(SearchMode.SingleTarget);
            settings.setSession(1);

            settings.getReport().setIncludePcBits(true);

        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
