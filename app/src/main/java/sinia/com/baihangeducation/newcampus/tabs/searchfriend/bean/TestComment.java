package sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean;

import java.util.List;

public class TestComment {


    private String count;
    private String page;
    private String perpage;
    private List<ListBean> list;


    public static class ListBean {

        private String comment_add_date;
        private String comment_content;
        private String comment_id;
        private String comment_is_author;
        private String comment_is_praise;
        private String comment_parent_id;
        private String comment_praise_num;
        private String comment_reply_level;
        private String comment_type;
        private String comment_type_id;
        private String comment_user_avatar;
        private String comment_user_id;
        private String comment_user_nickname;
        private List<?> comment_child_list;


    }
}
