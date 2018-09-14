package com.mcxtzhang.swipemenulib.customview.listcustomlist;

import java.util.List;


public class DiscountDetail {
    public int count;                               //总数
    public int page;                                //页码
    public int perpage;                             //每页数
    public int use_num;                             //
    public int no_use_num;                             //
    public int expired_num;                             //

    public List<Discount> list;        //数据

    public static class Discount {
        public String coupon_id;
        public String coupon_name;
        public String use_time;
        public String start_time;
        public String end_time;
        public String coupon_price;
    }
}
