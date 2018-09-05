package sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean;

import java.util.ArrayList;
import java.util.List;

public class CommentListBean {


    public String count;
    public String page;
    public String perpage;
    public List<CommentList> list = new ArrayList<>();

    public   class CommentList {

        public String comment_add_date;
        public String comment_content;
        public String comment_id;
        public String comment_is_author;
        public String comment_is_praise;
        public String comment_parent_id;
        public String comment_praise_num;
        public String comment_reply_level;
        public String comment_type;
        public String comment_type_id;
        public String comment_user_avatar;
        public String comment_user_id;
        public String comment_user_nickname;
        public List<ChildCommentList> comment_child_list;


    }


//
//    public String page;
//    public String perpage;
//    public String count;
//    public List<CommentList> list =new ArrayList<>();
//
//    public class CommentList {
//
//        public String comment_add_date;// "9小时前",
//        public List<ChildCommentList> comment_child_list;// [],
//        public String comment_content;// "与香菇君",
//        public String comment_id;// "336",
//        public String comment_is_author;// "1",
//        public String comment_is_praise;// "1",
//        public String comment_parent_id;// "0",
//        public String comment_praise_num;// "0",
//        public String comment_reply_level;// "1",
//        public String comment_type;// "7",
//       //todo  public String comment_reply_nickname;// "7",
//        public String comment_type_id;// "268",
//        public String comment_user_avatar;// "http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_avatar/4508aefa9407bdafd44e4ce63300a88f.jpeg",
//        public String comment_user_id;// "8",
//        public String comment_user_nickname;// "沧海一粟"
//
//
//
//
//        public class ChildCommentList {
//            public String comment_add_date;// "周六",
//            public List<ChildCommentList> comment_child_list;// [],
//            public String comment_content;// "什么",
//            public String comment_id;// "335",
//            public String comment_is_author;// "1",
//            public String comment_parent_id;// "334",
//            public String comment_reply_level;// "2",
//            public String comment_type;// "7",
//            public String comment_type_id;// "268",
//            public String comment_user_avatar;// "http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_avatar/4508aefa9407bdafd44e4ce63300a88f.jpeg",
//            public String comment_user_id;// "8",
//            public String comment_user_nickname;// "沧海一粟"  comment_user_nickname
//            public String comment_reply_id;// "沧海一粟"
//            public String comment_reply_nickname;
//
//        }
//
//    }

}




