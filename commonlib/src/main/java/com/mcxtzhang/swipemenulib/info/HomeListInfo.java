package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.HomeJobListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeNewsInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

/**
 * Created by Administrator on 2018/4/22.
 */

public class HomeListInfo {
    public List<JobBangClassADListInfo> ad_list;            //广告
    public HomeJobListInfo job_list;                  //兼职列表
    public List<HomeNewsInfo> news_list;                    //每日分享列表

    public String article_pic;
    public  List<IndustryListInfo>industry_list;





}
