package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model;

import java.util.List;

import sinia.com.baihangeducation.minecompany.info.ReleaseTraingListInfo;


public class CoffersDetail {
    public int count;                               //总数
    public int page;                                //页码
    public int perpage;                             //每页数
    public List<Coffers> list;        //数据

    public static class Coffers {
        public String id;
        public String type;
        public String title;
        public String befor;
        public String total;
        public String alert;
        public String add_time;

    }
}
