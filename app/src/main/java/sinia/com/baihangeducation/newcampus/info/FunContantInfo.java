package sinia.com.baihangeducation.newcampus.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;
import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import sinia.com.baihangeducation.newcampus.info.bean.PraiseListInfo;

public class FunContantInfo {
    public int dynamic_id;                  //趣事内容ID
    public String topic_title;                  //趣事内容
    public int publish_user_id;             //发布者ID
    public String publish_user_avatar;      //发布者头像
    public String publish_user_nickname;    //发布者昵称
    public String content;                  //内容
    public String is_praise;                   //是否已点赞    ( 1是2否 改值需要登录状态，否则默认是2 )
    public String is_follow;                   //关注    ( 1是2否 改值需要登录状态，否则默认是2 )
    public String add_time;                 //发布时间
    public String share_url;                //分享链接
    public List<SingleImageBean> images_list;   //图片
    public List<PraiseListInfo> praise_list;    //点赞列表
    public List<CommentInfo> comment_list;      //评论列表
    public List<SingleImageBean> thumbnail_list;//缩略图

    public String praise_num;  // 点赞数
    public String comment_num;  //评论数
    public String share_num;  //分享数
}
