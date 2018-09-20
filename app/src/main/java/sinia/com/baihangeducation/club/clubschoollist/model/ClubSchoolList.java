package sinia.com.baihangeducation.club.clubschoollist.model;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ClubSchoolList {
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
        public String fluctuate;//( 正数：上升，负数：下降，0：无变化 )

    }

}
