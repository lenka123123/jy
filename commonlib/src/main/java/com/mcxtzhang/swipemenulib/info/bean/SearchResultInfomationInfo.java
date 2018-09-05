package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class SearchResultInfomationInfo {
    public String news_add_date;                                    //发布日期    ( 仅type 为5时返回 )
    public String news_cover;                                       //封面图    ( 仅type 为2、3、4时返回 )
    public int news_id;                                             //ID    (  仅type 为5时返回  )
    public String news_short_info;                                  //简介    ( 仅type 为5时返回 )
    public List<SearchResultInfomationTagInfo> news_tag_list;      //包含标签(仅type 为5时返回)
    public String news_title;                                       //标题    ( 仅type 为5时返回 )
}
