package com.impinj.cache;

import com.impinj.model.TagDataModel;
import com.impinj.octane.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 读取标签数据缓存
 */
public class TagsDataCache {

    private static final Logger log = LoggerFactory.getLogger(TagsDataCache.class);

    /** 读取标签集合 */
    private static Map<String, TagDataModel> tagDataMap = new LinkedHashMap<String, TagDataModel>();

    /** 触控页面展示数据 */
    private static Set<Object> pageShowData = new LinkedHashSet<Object>();


    public static void addTagData(Tag tag) {
        String tagId = String.valueOf(tag.getTid());
        // todo 增加一次拿取最大数check

        if (tagDataMap.keySet().contains(tagId)) {
            TagDataModel tagDataModel = tagDataMap.get(tagId);
            if (tag.isAntennaPortNumberPresent()) {
                List<String> antennaPortList = tagDataModel.getAntennaPortList();

                tagDataModel.setAntennaPortList(new ArrayList<String>() {
                    {
                        add(String.valueOf(tag.getAntennaPortNumber()));
                    }
                });
                log.info(String.format("antennaPort: %s", tag.getAntennaPortNumber()));
            }

        }else {
            TagDataModel tagDataModel = new TagDataModel();

            if (tag.isFastIdPresent()) {
                tagDataModel.setTid(tag.getTid().toHexString());
                log.info(String.format("fast_id: %s", tag.getTid().toHexString()));
            }

            if (tag.isAntennaPortNumberPresent()) {
                tagDataModel.setAntennaPortList(new ArrayList<String>() {
                    {
                        add(String.valueOf(tag.getAntennaPortNumber()));
                    }
                });
                log.info(String.format("antennaPort: %s", tag.getAntennaPortNumber()));

                // 根据第一个读取到的天线端口号 来判断是否出入库
            }
        }
    }

    /**
     * 获取页面展示数据
     * @return
     */
    public static Set<Object> getPageShowData() {
        return pageShowData;
    }

    /**
     * 展示标签被读写器读取到的顺序
     * @return
     */
    public static Map<String, List<String>> getDirectionMap() {
        return directionMap;
    }

    /**
     * 清空
     */
    public static void clearAll() {
        tagsIdSet.clear();
        pageShowData.clear();
        log.info("ReadTagsDataCache 清理成功");
    }
}
