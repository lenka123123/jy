package sinia.com.baihangeducation.club.addclub;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class AddClubList {

    public List<ClubType> club_type_list ;

    public class ClubType {
        public String type ;
        public String type_name ;
    }
    public List<SchoolList> school_list  ;

    public class SchoolList {
        public String school_id  ;
        public String school_name  ;
    }

}
