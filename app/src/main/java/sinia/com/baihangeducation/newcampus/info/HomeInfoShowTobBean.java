package sinia.com.baihangeducation.newcampus.info;

import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import java.util.List;

public class HomeInfoShowTobBean {
    public  DynAmicList dynamic_list;
    public  UserInfoTobDetail info;
    public  FunContantInfo hot_dynamic;
    public class DynAmicList {
        public String count;                           //总数
        public String page;                            //页码
        public String perpage;                         //每页数
        public List<FunContantInfo> list;

    }



    public class UserInfoTobDetail {


        public String topic_comment_num;//  "77",
        public String topic_id;// ": "16",
        public String topic_introduce;// ": "传递教育服务信息，关注就业邦社交，获取最新教育信息",
        public String topic_logo;// ": "http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_logo/de052aabaaae433ecd55a6a20758b19c.jpg",
        public String topic_look_num;// ": "77",
        public String topic_title;// ": "#教育小邦手#"
    }

}
