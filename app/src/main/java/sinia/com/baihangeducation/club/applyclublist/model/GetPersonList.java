package sinia.com.baihangeducation.club.applyclublist.model;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class GetPersonList {
    public int page;
    public int perpage;
    public int count;
    public List<PersonList> list;

    public class PersonList {
        public String id;
        public String nickname;
        public String role_name ;
        public String avatar  ;
        public String income  ;

    }
}
