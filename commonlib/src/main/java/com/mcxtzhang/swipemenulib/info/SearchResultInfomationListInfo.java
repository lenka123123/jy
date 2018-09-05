package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.SearchResultInfomationInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public class SearchResultInfomationListInfo {
    public int count;                                       //总数
    public List<SearchResultInfomationInfo> list;          //数据
    public int page;                                        //页码
    public int perpage;                                     //总页数
}
