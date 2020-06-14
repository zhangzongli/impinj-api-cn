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

    /**
     * 读取标签集合
     * key： tid
     * value: 数据model
     */
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
                List<String> inOrOutHouseList = tagDataModel.getInOrOuthousetList();
                // 通过天线端口号判断 标签位于库房哪一端
                String inOrOutOfHouse = JudgeInOrOutByAntennaPortUtil.judgeInOrOurOfHouseByFirstAntennaPort(port);
                // 依然是被同一端的天线读取到，不作处理。只有被不同端的天线读取到，读取list 才进行新增
                if (!inOrOutHouseList.get(inOrOutHouseList.size() - 1).equals(inOrOutOfHouse)) {
                    log.info(String.format("标签是被 %s 端的天线扫描到", inOrOutOfHouse));
                    inOrOutHouseList.add(inOrOutOfHouse);
                    // 获取当前操作时间
                    tagDataModel.setReadTime(new Date());
                }
            }
            // 根据室内外天线读取顺序列表，判断该设备的出入库状态
            InOrOutEnum inOrOutStatus = JudgeInOrOutByAntennaPortUtil.judgeInOrOurByAntennaList(tagDataModel.getInOrOuthousetList());
            tagDataModel.setInOrOut(inOrOutStatus);

            // TODO: 2020/6/13 根据编号 查询数据库 展示页面数据

            // TODO: 2020/6/13 查询数据库，判断设备当前的出入库状态。若不符合，则语音提醒。最终关门时，进行钉钉或短信提醒管理员

            tagDataMap.put(tagId, tagDataModel);

        }else {

            // 获取当前时间。
            Date nowDate = new Date();

            TagDataModel tagDataModel = new TagDataModel();
            tagDataModel.setReadTime(nowDate);

            if (tag.isFirstSeenTimePresent()) {
//                tagDataModel.setReadTime();
                log.info(String.format("first: %s", tag.getFirstSeenTime().ToString()));
            }

            if (tag.isFastIdPresent()) {
                tagDataModel.setTid(tag.getTid().toHexString());
                log.info(String.format("fast_id: %s", tag.getTid().toHexString()));
            }

            if (tag.isAntennaPortNumberPresent()) {

                // 通过天线端口号判断 标签位于库房哪一端
                String inOrOutOfHouse = JudgeInOrOutByAntennaPortUtil.judgeInOrOurOfHouseByFirstAntennaPort(port);
                log.info(String.format("标签是被 %s 端的天线扫描到", inOrOutOfHouse));
                tagDataModel.setInOrOuthousetList(new ArrayList<String>() {
                    {
                        add(inOrOutOfHouse);
                    }
                });
            }

            // TODO: 2020/6/13 根据编号 查询数据库 展示页面数据

            // TODO: 2020/6/13 查询数据库，判断设备当前的出入库状态。若不符合，则语音提醒。最终关门时，进行钉钉或短信提醒管理员

            tagDataMap.put(tagId, tagDataModel);
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
    public static String getDirection() {
        List<String> resultList = new ArrayList<String>();
        for (String key : tagDataMap.keySet()) {
            String info = String.format("tid： %s  出入库状态: %S 读取顺序： %s",  key, tagDataMap.get(key).getInOrOut().getCode(), String.join(",", tagDataMap.get(key).getInOrOuthousetList()));
            resultList.add(info);
        }
        return String.join(",", resultList);
    }

    /**
     * 清空
     */
    public static void clearAll() {
        tagDataMap = new LinkedHashMap<String, TagDataModel>();
        pageShowData.clear();
        log.info("TagsDataCache 读取数据缓存 清理成功！！！");
    }
}
