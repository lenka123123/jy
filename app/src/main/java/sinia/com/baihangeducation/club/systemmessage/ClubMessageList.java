package sinia.com.baihangeducation.club.systemmessage;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ClubMessageList {
    public int page;
    public int perpage;
    public int count;
    public List<SystemMessage> list;

    public static class SystemMessage {
        public String system_message_id;
        public String title;
        public String content;
        public String date;
        public MyKeywords keywords;

        public static class MyKeywords {
            public String type;
            public String category;
            public String category_id;
            public String check_type;
            public String club_name;
        }

    }

}
