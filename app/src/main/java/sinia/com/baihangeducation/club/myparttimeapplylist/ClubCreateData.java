package sinia.com.baihangeducation.club.myparttimeapplylist;

import java.util.List;


public class ClubCreateData {
    public int count;                               //总数
    public int page;                                //页码
    public int perpage;                             //每页数
    public List<ApplyPerson> list;        //数据

    public static class ApplyPerson {
        public String add_time;//": "2018-11-16",
        public String age;//": "未知",
        public String apply_id;//": "228",
        public String avatar;//": "http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_avatar/eed4487e31444044f8c1e032725acb82.png",
        public String gender;//": "女",
        public String nickname;//": "smileforme",
        public String school_name;//": "南京大学"
        public String jmessage_phone;//": 手机号
    }
}
