package sinia.com.baihangeducation.club.notice.model;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ClubDetailBean {

    public Club info;

    public class Club {
        public String id;
        public String name;
        public String logo;
        public String introduce;  // inreoduce
        public String member_num;
        public String income;
        public String new_order;//
        public String role;//
        public String power;//
        public String is_apply;//
    }

    public AdminInfo admin_info;

    public class AdminInfo {
        public String id;
        public String nickname;
        public String avatar;
        public String role;
        public String role_name;//角色 ( 0：游客 1：成员 2：财务 3：副会长 4：会长 )
    }

    public NoticeList notice_list;

    public class NoticeList {
        public String page;
        public String perpage;
        public String count;

        public List<Notice> list;

        public class Notice {
            public String id;
            public String title;//
            public String add_time;//
        }

    }


}
