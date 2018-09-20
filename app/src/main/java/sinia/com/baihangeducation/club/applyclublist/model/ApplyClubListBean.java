package sinia.com.baihangeducation.club.applyclublist.model;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ApplyClubListBean {
    public int page;
    public int perpage;
    public int count;
    public List<ApplyList> list;

    public class ApplyList {
        public String id;
        public String nickname;
        public String avatar;
    }


}
