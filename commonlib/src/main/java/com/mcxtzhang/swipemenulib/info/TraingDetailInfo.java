package com.mcxtzhang.swipemenulib.info;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TraingDetailInfo implements Serializable {
    public int collect_id;                      //收藏ID
    public int is_collect;                      //是否收藏 1是2否
    public String train_address;                //授课地址
    public String train_class_date;             //上课时间
    public String train_class_duration;            //时长
    public String train_class_intro;            //课程介绍（富文本）
    public String train_class_num;                 //课时数
    public int train_id;                        //培训ID
    public int train_is_coupon;                 //是否可用优惠券 1是2否
    public String train_price;                  //价格
    public String train_share_url;              //分享URL
    public String train_sub_title;              //副标题
    public String train_tel;                    //联系电话
    public String train_title;                  //标题
    public String industry_id;                  //行业ID 多个逗号分隔    ( 2018.06.22迭代 )
    public String level_id;                  //级别ID 多个逗号分隔    ( 2018.06.22迭代 )
    public String cycle_id;                  //周期ID 多个逗号分隔    ( 2018.06.22迭代 )
    public int province_id;                  //省份ID    ( 2018.06.22迭代 )
    public int city_id;                  //市区ID    ( 2018.06.22迭代 )
    public int area_id;                  //县区ID    ( 2018.06.22迭代 )
    public String address_only;                  //地址（不含省市区）    ( 2018.06.22迭代 )
    public String cover;                  //封面图
}
