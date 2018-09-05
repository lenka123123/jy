package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachLevelInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachWeekInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingSeachListInfo {
    public List<HomeTraingSeachWeekInfo> cycle_list;           //周期
    public List<HomeTraingSeachIndustryInfo> industry_list;           //行业
    public List<HomeTraingSeachLevelInfo> level_list;           //等级
    public List<HomeTraingSeachOrderInfo> order_list;           //排序
}
