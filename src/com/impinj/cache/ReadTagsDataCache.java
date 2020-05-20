package com.impinj.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 读取标签数据缓存
 */
public class ReadTagsDataCache {

    private static final Logger log = LoggerFactory.getLogger(ReadTagsDataCache.class);

    /** 读取标签集合 */
    private static Set<String> tagsIdSet = new LinkedHashSet<String>();

    /** 触控页面展示数据 */
    private static Set<Object> pageShowData = new LinkedHashSet<Object>();


    /**
     * 增加标签id
     * @param tagId
     */
    public static void add(String tagId) {
        if (!tagsIdSet.contains(tagId)) {
            // TODO: 2020/5/20 查询数据库 获取该标签id关联的设备信息。

            log.info(String.format("读取新标签->标签id:%s", tagId));
            tagsIdSet.add(tagId);
            pageShowData.add(tagId);
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
     * 清空
     */
    public static void clearAll() {
        tagsIdSet.clear();
        pageShowData.clear();
        log.info("ReadTagsDataCache 清理成功");
    }
}
