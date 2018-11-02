package sinia.com.baihangeducation.club.myclub.help;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MyHelplList {
    public int page;
    public int perpage;
    public int count;
    public List<Help> list;

    public class Help {
        public String support_id ;
        public String status ;
        public String name ;
        public String mobile  ;
        public String title   ;
        public String club_id   ;
        public String money   ;
        public String requirement   ;
        public String reason   ;
        public String club_name   ;
        public String school_name   ;

    }

}
