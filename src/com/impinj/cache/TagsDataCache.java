package com.impinj.cache;

import com.impinj.enums.InOrOutEnum;
import com.impinj.model.TagDataModel;
import com.impinj.octane.Tag;
import com.impinj.util.JudgeInOrOutByAntennaPortUtil;
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

        String port = String.valueOf(tag.getAntennaPortNumber());
        log.info(String.format("antennaPort: %s", tag.getAntennaPortNumber()));

        if (tagDataMap.keySet().contains(tagId)) {
            TagDataModel tagDataModel = tagDataMap.get(tagId);
            if (tag.isAntennaPortNumberPresent()) {
                List<InOrOutEnum> antennaPortList = tagDataModel.getInOrOutAntennatList();
                InOrOutEnum inOrOutEnum = JudgeInOrOutByAntennaPortUtil.judgeInOrOurByFirstAntennaPort(port);
                log.info(String.format("标签是被 %s 的天线扫描到", inOrOutEnum.getCode()));
                antennaPortList.set(antennaPortList.size(), inOrOutEnum);
            }

        }else {
            TagDataModel tagDataModel = new TagDataModel();

            if (tag.isFastIdPresent()) {
                tagDataModel.setTid(tag.getTid().toHexString());
                log.info(String.format("fast_id: %s", tag.getTid().toHexString()));
            }

            if (tag.isAntennaPortNumberPresent()) {

                InOrOutEnum inOrOutEnum = JudgeInOrOutByAntennaPortUtil.judgeInOrOurByFirstAntennaPort(port);
                log.info(String.format("标签是被 %s 的天线扫描到", inOrOutEnum.getCode()));
                tagDataModel.setInOrOutAntennatList(new ArrayList<InOrOutEnum>() {
                    {
                        add(inOrOutEnum);
                    }
                });

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
