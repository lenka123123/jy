package com.mcxtzhang.swipemenulib.info.bean;


import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeTraingSeachOrderInfo implements BaseFilter {
    public int order_id;
    public String order_name;

    @Override
    public String getFilterStr() {
        return order_name;
    }

    @Override
    public int getFilterId() {
        return order_id;
    }
}
