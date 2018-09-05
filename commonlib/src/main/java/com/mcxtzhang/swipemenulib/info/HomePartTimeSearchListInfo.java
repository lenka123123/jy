package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeSearchListInfo {
    public List<HomePartTimeSalaryInfo> money_list;         //薪资
    public List<HomeTraingSeachIndustryInfo> industry_list;           //行业
    public List<HomeTraingSeachOrderInfo> order_list;           //排序
    public List<HomePartTimeDistInfo> dist_list;
}
