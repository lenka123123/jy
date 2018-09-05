package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class GetShareEveryDayInfo {
    public int news_id;                                     //资讯ID
    public String news_title;                               //标题
    public String news_short_info;                          //简介
    public String news_cover;                               //封面条
    public List<GetShareEveryDayTagInfo> news_tag_list;    //标签信息
    public String news_add_date;                            //发布日期
    public String news_look_num;                            //看了多少次
}
