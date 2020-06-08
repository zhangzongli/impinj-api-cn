package com.impinj.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 读取标签数据缓存
 */
public class ReadTagsDataCache {

    private static final Logger log = LoggerFactory.getLogger(ReadTagsDataCache.class);

    /** 读取标签集合 */
    private static Set<String> tagsIdSet = new LinkedHashSet<String>();

    /**
     * 方向map 即标签被读写器填写读取的顺序
     */
    private static Map<String, List<String>> directionMap = new LinkedHashMap<String, List<String>>();

    /** 触控页面展示数据 */
    private static Set<Object> pageShowData = new LinkedHashSet<Object>();


    /**
     * 增加标签id
     * @param tagId
     */
    public static void add(String tagId, short antennaPortNumber) {
        String antennaPortString = String.valueOf(antennaPortNumber);
        if (!tagsIdSet.contains(tagId)) {
            // TODO: 2020/5/20 查询数据库 获取该标签id关联的设备信息。

            log.info(String.format("读取新标签->标签id:%s", tagId));
            tagsIdSet.add(tagId);
            pageShowData.add(tagId);
            // 标签被读取天线顺序
            if (directionMap.keySet().contains(tagId)) {
                List<String> directionList = directionMap.get(tagId);
                if (directionList.get(directionList.size() - 1) != antennaPortString) {
                    directionList.add(antennaPortString);
                }
            }else {
                List<String> directionList = new ArrayList<String>();
                directionList.add(antennaPortString);
                directionMap.put(tagId, directionList);
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
