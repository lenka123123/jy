package sinia.com.baihangeducation.club.clubdetail.model;

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
        public String nature; //社团性质
        public String school_name;
        public String income;
        public String new_order;//
        public String role;// 角色  ( 1：成员 2：财务 3：副会长 4：会长 )
        //   public String power;//
        public String is_apply;//是否申请过该俱乐部    ( 0：已申请 1：未申请 2：社员 )
        public String jmessage_group_id; // 群聊id
    }

//    public AdminInfo admin_info;
//
//    public class AdminInfo {
//        public String id;
//        public String nickname;
//        public String avatar;
//        public String role;   // role  角色    ( 1：成员 2：财务 3：副会长 4：会长 )
//        public String role_name;//角色 ( 0：游客 1：成员 2：财务 3：副会长 4：会长 )
//    }

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
