package com.impinj.listener;

import com.impinj.ReadTags;
import com.impinj.cache.ReadTagsDataCache;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

import java.util.List;

public class TagReportListenerImpl implements TagReportListener {

    @Override
    public void onTagReported(ImpinjReader impinjReader, TagReport tagReport) {
        List<Tag> tags = tagReport.getTags();
        for (Tag tag : tags) {
            if (tag.isFastIdPresent()) {
                ReadTagsDataCache.add(tag.getTid().toHexString());

                System.out.print("fast_id: " + tag.getTid().toHexString());
            }
            System.out.println("");
        }

    }
}
