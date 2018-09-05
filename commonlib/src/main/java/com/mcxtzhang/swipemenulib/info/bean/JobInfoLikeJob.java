package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class JobInfoLikeJob {
    public String job_id;
    public String job_type;
    public String job_title;
    public String job_look_num;
    public String job_company_id;
    public List<HomeJobTagInfo> job_tag_list;               //标签列表    ( 仅type为1、2时返回 )

}
