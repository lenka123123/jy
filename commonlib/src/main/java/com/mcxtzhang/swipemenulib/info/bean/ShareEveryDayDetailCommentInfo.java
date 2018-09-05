package com.mcxtzhang.swipemenulib.info.bean;

/**
 * Created by Administrator on 2018/4/19.
 */

public class ShareEveryDayDetailCommentInfo {
    public int comment_id;                              //评论ID
    public int comment_parent_id;                       //评论父级ID
    public int comment_reply_level;                     //评论级别
    public int comment_type;                            //评论类型 1早读趣事 2秘籍 3攻略 4试卷 5互助
    public int comment_user_id;                         //评论用户ID
    public String comment_user_nickname;                //评论者昵称
    public String comment_user_avatar;                  //评论者头像
    public String comment_add_date;                     //评论时间
    public int son_comment_num;                         //子评论数量    ( 仅1级评论会返回 )
    public String comment_content;                      //评论内容
}
