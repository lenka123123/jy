package com.mcxtzhang.swipemenulib.info.bean;

import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingSeachIndustryInfo implements BaseFilter {
    public int industry_id;
    public String industry_name;

    @Override
    public String getFilterStr() {
        return industry_name;
    }

    @Override
    public int getFilterId() {
        return industry_id;
    }
}
