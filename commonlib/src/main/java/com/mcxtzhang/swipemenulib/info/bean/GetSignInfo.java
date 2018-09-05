package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class GetSignInfo {
    public int continuity_num;      //连续签到天数
    public int total_num;      //总签到数
    public int is_signed;      //今日是否已签到    ( 1是2否 )
    public int growth_sign;      //签到成长值
    public List<String> list;               //日期数组
}
