package sinia.com.baihangeducation.newcampus.info;

import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import java.util.List;

public class HomePagerDetaileee {

    public Funinfoes list;

    public class Funinfoes {
        public String dynamic_id;//"int;//
        public String publish_user_id;//"int;//
        public String publish_user_avatar;//"string;//
        public String publish_user_nickname;//"string;//
        public String content;//"string;//
        public String is_praise;//"int;//
        public String is_follow;//"int;//
        public String praise_num;//"int;//
        public String comment_num;//"int;//
        public String share_num;//"int;//
        public String topic_id;//"int;//
        public String add_time;//"string;//
        public String share_url;//"string;//


        public List<SingleImageBean> images_list;
        public List<SingleImageBean> thumbnail_list;



        public CommentList  comment_list;

        public class CommentList {

            public String count;//"int;//
            public String page;//"int;//
            public String perpage;//"int;//
            public List list;

            public class List {
                public String comment_id;//"int;//
                public String comment_parent_id;//"int;//
                public String comment_reply_level;//"int;//
                public String comment_type;//"int;//
                public String comment_content;//"string;//
                public String comment_user_id;//"int;//
                public String comment_user_nickname;//"string;//
                public String comment_user_avatar;//"string;//
                public String comment_add_date;//"string;//
                public String comment_is_author;//"int;//
                public String comment_is_praise;//"int;//
                public String comment_praise_num;//"int;//
                public String comment_child_list;//"int;//
                public String comment_reply_id;//"int;//
                public String comment_reply_nickname;//"int"
            }
        }
    }
}