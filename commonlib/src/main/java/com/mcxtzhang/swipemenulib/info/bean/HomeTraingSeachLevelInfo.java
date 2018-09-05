package com.mcxtzhang.swipemenulib.info.bean;


import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingSeachLevelInfo implements BaseFilter {
    public int level_id;
    public String level_name;

    @Override
    public String getFilterStr() {
        return level_name;
    }

    @Override
    public int getFilterId() {
        return level_id;
    }
}
