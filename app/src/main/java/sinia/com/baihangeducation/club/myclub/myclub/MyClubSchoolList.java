package sinia.com.baihangeducation.club.myclub.myclub;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MyClubSchoolList {
    public int page;
    public int perpage;
    public int count;
    public List<School> list;

    public class School {
        public String id;
        public String name;
        public String logo;
        public String member_num;
        public String income;
        public String new_order;
        public String fluctuate;
        public String is_apply;
        public String jmessage_group_id;
        public String jmessage_owner;
        public String school_name;
        public String role_name;

    }

}
