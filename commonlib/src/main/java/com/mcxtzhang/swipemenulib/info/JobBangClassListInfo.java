package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassSecretListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassStrategyListInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobBangClassTestListInfo;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JobBangClassListInfo {
    public List<JobBangClassADListInfo> ad_list;                        //广告列表
    public List<JobBangClassSecretListInfo> strategy_list;            //攻略列表
    public List<JobBangClassSecretListInfo> cheats_list;                //秘籍列表
    public List<JobBangClassTestListInfo> test_list;                    //秘籍列表
}
