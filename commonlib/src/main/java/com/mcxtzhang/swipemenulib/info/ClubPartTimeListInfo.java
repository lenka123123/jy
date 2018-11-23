package com.mcxtzhang.swipemenulib.info;

import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubPartTimeListInfo {
    //    public int city_id;                             //市编号
    public int count;                               //总数
    public int page;                               //页码
    public int perpage;                               //每页数
    public List<ClubPartInfo> list;                               //数据

    public static class ClubPartInfo {
        public String job_id;
        public String industry_id;
        public String title;
        public String money_total;
        public String money_name;
        public String look_num;
        public String add_time;
        public String add_date;
        public String city_name;
        public String industry_icon;


        public String industry_name;
        public String client_look_num;
        public String jmessage_phone;
        public String money_type_name;
        public String time_group;



    }
}
