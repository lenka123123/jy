package sinia.com.baihangeducation.club.clubactive.model;

import java.util.List;


public class ActiveListData {
    public int count;                               //总数
    public int page;                                //页码
    public int perpage;                             //每页数
    public List<ActiveList> list;        //数据

    public static class ActiveList {
        public String activity_id;
        public String type;
        public String name;
        public String cover ;
        public String start_time;
        public String addr;
        public String expenditure;
        public String join_num;
        public String club_name;
        public int status;

    }
}
