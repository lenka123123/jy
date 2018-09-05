package sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean;

import java.util.List;

public class GetFriendListBean {



    public List<Recommend> recommend_list;
    public List<Like> like_list;
    public List<Distance> distance_list;
    public List<Possible> possible_list;

    public class Distance {
        public String avatar;// "",
        public String distance;// "125m",
        public String fans;// "0",
        public String follow;// "0",
        public String is_follow;// "1",
        public String nickname;// "jyb1035",
        public String type;// "1",
        public String user_id;// "38378"
    }



    public class Like {
        public String avatar;// "",
        public String distance;// "125m",
        public String fans;// "0",
        public String follow;// "0",
        public String is_follow;// "1",
        public String nickname;// "jyb1035",
        public String type;// "1",
        public String user_id;// "38378"
    }



    public class Possible {
     	      public String user_id;// "15247",
              public String nickname;// "苏瑾年",
              public String avatar;// "http:\/\/yun-attachment.oss-cn-hangzhou.aliyuncs.com\/njyb_avatar\/2072b5f296ed1e50576c2085f0d9dd87.png",
              public String follow;// "0",
              public String fans;// "1",
              public String type;// "1",
              public String possible;// "沧海一粟",
              public String is_follow;// "1"
    }




    public class Recommend {
        public String avatar;// "",
        public String distance;// "125m",
        public String fans;// "0",
        public String follow;// "0",
        public String is_follow;// "1",
        public String nickname;// "jyb1035",
        public String type;// "1",
        public String user_id;// "38378"
    }

}




