package com.mcxtzhang.swipemenulib.info.bean;

import com.example.framwork.utils.UserInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FragmentMessageInfo implements Serializable {
    public int system_message_id;
    public String title;
    public String content;
    public String date;
    public int is_read;
}
