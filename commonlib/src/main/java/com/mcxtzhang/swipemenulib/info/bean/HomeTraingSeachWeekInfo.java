package com.mcxtzhang.swipemenulib.info.bean;


import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingSeachWeekInfo implements BaseFilter {
    public int cycle_id;
    public String cycle_name;

    @Override
    public String getFilterStr() {
        return cycle_name ;
    }

    @Override
    public int getFilterId() {
        return cycle_id;
    }
}
