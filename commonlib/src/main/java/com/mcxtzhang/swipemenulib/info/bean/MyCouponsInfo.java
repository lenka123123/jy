package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyCouponsInfo {
    public int count;              //总数
    public int expired_num;        //过期数量
    public int no_use_num;        //未用数量
    public int page;               //页码
    public int perpage;               //每页数
    public int use_num;               //已用数量
    public List<MyCouponsItemInfo> list;
}
