package sinia.com.baihangeducation.reconsitution.tabs.pay.tab;

import java.util.List;


public class PayDetail {
    public int count;                               //总数
    public int page;                                //页码
    public int perpage;                             //每页数
    public List<Detail> list;        //数据

    public static class Detail {
        public String order_id;
        public int order_raiders_id;
        public String order_title;
        public String order_type; //  ( 1：秘籍；2：试卷； )
        public String order_no;// 订单编号
        public String order_add_time;
        public String order_total_fee;
        public String order_pay_type;//支付类型    ( 1：支付宝；2：微信；3：优惠券全额支付 )
        public String order_pay_status;//支付状态    ( 1：待支付；2：已支付；3：失败；4：已取消 )

    }
}
