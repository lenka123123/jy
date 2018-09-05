package sinia.com.baihangeducation.newcampus.info;

import java.util.List;

public class HomePager {

    public int count;                           //总数
    public int perpage;                         //每页数

    public  UserInfoes info;//

    public class UserInfoes {
        public String user_id;// "int",
        public String nickname;// "string",
        public String avatar;// "string",
        public String follow;// "int",
        public String fans;// "int",
        public String type;// "int",
        public String article_num;// "int",
        public String is_follow;// "int"
    }

    public List<UserDynamicList> dynamic_list;

    public class UserDynamicList {
        public String count;// "int",
        public String page;// "int",
        public String perpage;// "int",
        public List<ChatList> list;

        public class ChatList {
            public String dynamic_id;// "int",
            public String publish_user_id;// "int",
            public String publish_user_avatar;// "string",
            public String publish_user_nickname;// "string",
            public String content;// "string",
            public String is_praise;// "int",
            public String is_follow;// "int",
            public String praise_num;// "int",
            public String comment_num;// "int",
            public String share_num;// "int",
            public String topic_id;// "int",
            public String add_time;// "string",
            public String share_url;// "string",
            public List<ImagesList> images_list;
            public List<ThumbnailList> thumbnail_list;

            public class ImagesList {
                public String url;// "string"

            }

            public class ThumbnailList {
                public String url;// "string"
            }

        }
    }
}