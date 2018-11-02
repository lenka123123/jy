package com.mcxtzhang.swipemenulib.info.bean;


import com.mcxtzhang.swipemenulib.interfaces.BaseFilter;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubPartTimeSalaryInfo implements BaseFilter {
    public int money_type_id ;
    public String money_type_name ;

    @Override
    public String getFilterStr() {
        return money_type_name ;
    }

    @Override
    public int getFilterId() {
        return money_type_id;
    }
}
