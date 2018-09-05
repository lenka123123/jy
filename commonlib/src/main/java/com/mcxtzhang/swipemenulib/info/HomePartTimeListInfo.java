package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeListInfo {
    public int city_id;                             //市编号
    public int count;                               //总数
    public int page;                               //页码
    public int perpage;                               //每页数
    public List<HomePartTimeInfo> list;                               //数据
}
