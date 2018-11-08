package sinia.com.baihangeducation.club.club.model;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

import java.util.List;

import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ClubHomeInfo {

    public List<JobBangClassADListInfo> ad_list;            //广告

    public class AdList {
        public String type;//类型1：跳转APP内容；2：跳转H5 URL；）
        public String cover_url;  //广告图片地址
        public String plate;  // 1兼职 2全职 3培训 4转让 5互助 6App内容板块 7趣事 8每日分享    ( 仅type 为1时有效 )
        public String type_id;//对应板块ID    ( 仅type 为1时有效 )
        public String title;  //广告标题
        public String is_need_login;//打开该广告是否需要强制登录 1是2否
        public String url;//跳转的URL地址    ( 仅type为2时有效 )
    }

    public List<LateList> late_list;

    public class LateList {
        public String id;//
        public String name;
        public String logo;  //
        public String member_num;//
    }

    public List<School> list;

    public class School {
        public String id;
        public String name;
        public String logo;
        public String member_num;
        public String income;
        public String new_order;
        public String is_show;
        public String is_apply;
        public String fluctuate;//( 正数：上升，负数：下降，0：无变化 )

    }

    public UserInfo user_info;

    public class UserInfo {
        public String is_edit;
        public String school_id;
        public String school_name;

        public String club_num;
        public String activity_num;
        public String job_num;
        public String support_num;
    }
}
