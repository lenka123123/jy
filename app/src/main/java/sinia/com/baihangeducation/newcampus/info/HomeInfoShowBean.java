package sinia.com.baihangeducation.newcampus.info;

import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import java.util.List;

public class HomeInfoShowBean {
    public DynAmicList dynamic_list;

    public class DynAmicList {
        public String count;                           //总数
        public String page;                            //页码
        public String perpage;                         //每页数
        public List<FunContantInfo> list;

        public class InfoList {

            public List<SingleImageBean> images_list;
            public List<SingleImageBean> thumbnail_list;

            public String dynamic_id;// "276",
            public String publish_user_id;// "8",
            public String publish_user_avatar;// "http:\/\/yun-attachment.oss-cn-hangzhou.aliyuncs.com\/njyb_avatar\/4508aefa9407bdafd44e4ce63300a88f.jpeg",
            public String publish_user_nickname;// "沧海一粟",
            public String content;// "今天",

            public String is_follow;// "2",
            public String is_praise;// "2",
            public String praise_num;// "1",
            public String comment_num;// "0",
            public String share_num;// "0",
            public String look_num;// "0",
            public String topic_id;// "0",
            public String topic_title;// "",
            public String add_time;// "1小时前",
            public String share_url;// "http:\/\/newwap.891jyb.com\/?app=share&act=dynamic&id=276"
        }

    }

    public UserInfoDetail info;

    public class UserInfoDetail {
        public String article_num;// "11",
        public String avatar;// "http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_avatar/6dd14258f0b9ea150bf4719f19f57509.jpeg",
        public String fans;// "1",
        public String follow;// "8",
        public String is_follow;// "2",
        public String nickname;// "jyb520",
        public String type;// "1",
        public String user_id;// "8"
    }

}
