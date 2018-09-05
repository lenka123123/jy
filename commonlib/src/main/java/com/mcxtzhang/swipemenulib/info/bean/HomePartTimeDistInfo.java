package com.mcxtzhang.swipemenulib.info.bean;


import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeDistInfo implements BaseFilter {
    public int dist_id;
    public String dist_name;

    @Override
    public String getFilterStr() {
        return dist_name;
    }

    @Override
    public int getFilterId() {
        return dist_id;
    }
}
