package sinia.com.baihangeducation.newcampus.tabs.fun.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.example.framwork.adapter.listview.CommonAdapter;
import com.example.framwork.utils.UserInfo;
import com.lzy.ninegrid.ImageInfo;
import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.MyApplication;

import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.info.bean.PraiseListInfo;
import sinia.com.baihangeducation.newcampus.tabs.friend.NineGridView;
import sinia.com.baihangeducation.newcampus.tabs.friend.NineImageAdapter;
import sinia.com.baihangeducation.newcampus.tabs.fun.interfaces.GetShowListener;
import sinia.com.baihangeducation.newcampus.tabs.fun.model.FunCampusModel;
import sinia.com.baihangeducation.newcampus.tabs.fun.presenter.ApproveListener;
import sinia.com.baihangeducation.newcampus.tabs.fun.presenter.FunItemActionPresenter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.newcampus.view.IIsShowInput;
import sinia.com.baihangeducation.release.adapter.PhotoShowDialog;
import sinia.com.baihangeducation.supplement.base.CommonModel;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.AdapterUtils;

public class NewCampusFunAdapter extends SuperBaseAdapter<FunContantInfo> {
    private Context context;
    private FunItemActionPresenter presenter;               //点赞
    private View contentViewAction;                         //点赞 分享 评论 view
    private PopupWindow windowAction;                       //点赞 分享 评论 popwindow
    private View contentViewShare;                          //分享 view
    private PopupWindow windowShare;                        //分享 popwindow
    private View contentViewInput;
    private PopupWindow windowInput;
    private String prsiserName = "";                        //点赞人名字
    private View parentView;                                //Activity父布局

    private IIsShowInput iIsShowInputView;                  //是否显示输入框接口
    private CommonAdapter adapter;
    private boolean showFllow;
    private int showNum;
    private int dynamic_id;
    private TextView tvShowNum;

    public NewCampusFunAdapter(Context context, List<FunContantInfo> data, View view, IIsShowInput iIsShowInputView, boolean b) {
        super(context, data);
        this.context = context;
        this.presenter = new FunItemActionPresenter((Activity) context);
        this.parentView = view;
        this.iIsShowInputView = iIsShowInputView;
        this.iIsShowInputView = iIsShowInputView;
        this.showFllow = b;
    }

    @Override
    protected int getItemViewLayoutId(int position, FunContantInfo item) {
        return R.layout.firend_campus_list_item;
    }

    @Override
    protected void convert(final BaseViewHolder holder, FunContantInfo item, int position) {
        if (item == null) return;
        holder.setOnClickListener(R.id.newcampayfunitem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iIsShowInputView.IsShowInput(false, item);
            }
        });


        //头像
        holder.setRoundImageUrl(R.id.newcampayfunitem_uesericon, item.publish_user_avatar, R.drawable.new_eorrlogo);
        clickImage(holder.getView(R.id.newcampayfunitem_uesericon), item);

        clickImage(holder.getView(R.id.linearLayout_chat_number), item);
        clickImage(holder.getView(R.id.all), item);
        clickImage(holder.getView(R.id.blank), item);
        clickOne(holder.getView(R.id.del), item, position);

        //昵称
        holder.setText(R.id.newcampayfunitem_uesername, item.publish_user_nickname);
        //内容
        final TextView addFollow = holder.getView(R.id.addfollow);
        final ImageView comment_flog = holder.getView(R.id.comment_flog);


        if (item.is_follow.equals("2")) {
            addFollow.setBackgroundResource(R.drawable.add_follow_ed);
            comment_flog.setVisibility(View.VISIBLE);
            addFollow.setText("");
        } else {
            addFollow.setBackgroundResource(R.drawable.attention_add);
            addFollow.setText("+关注");
            comment_flog.setVisibility(View.INVISIBLE);
            addFollow.setTextColor(Color.RED);
        }
        addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonModel commonModel = new CommonModel(context);
                commonModel.addFollow(String.valueOf(item.publish_user_id), new FollowDataListener() {
                    @Override
                    public void getHotFunDataSuccess(int flag) {
                        if (flag == 2) {
                            addFollow.setBackgroundResource(R.drawable.add_follow_ed);
                            addFollow.setText("");
                            comment_flog.setVisibility(View.VISIBLE);
                            item.is_follow = "2";
                        } else {
                            addFollow.setBackgroundResource(R.drawable.attention_add);
                            addFollow.setText("+关注");
                            comment_flog.setVisibility(View.INVISIBLE);
                            addFollow.setTextColor(Color.RED);
                            item.is_follow = "1";
                        }
                    }

                    @Override
                    public void getHotFunDataFail() {

                    }
                });
            }
        });


        TextView tvView = holder.getView(R.id.tv_ok);
        tvView.setText(item.praise_num);
        ImageView imageView = holder.getView(R.id.img_ok);
        if (item.is_praise.endsWith("2")) {
            imageView.setImageResource(R.drawable.chat_ok);
            tvView.setTextColor(Color.RED);
        } else {
            imageView.setImageResource(R.drawable.chat_no);
            tvView.setTextColor(Color.BLACK);
        }


        if (dianZan(holder, item, position)) {
//            item.praise_list.add_friend(dianZanInfo);
//            notifyDataSetChanged();
        }
        show(holder, item, position);

        holder.setText(R.id.tv_chat_number, item.comment_num);
        String shareNum = item.share_num;
        if (shareNum.equals(""))
            shareNum = "0";
        showNum = Integer.valueOf(shareNum);
        tvShowNum = holder.getView(R.id.tv_show);
        changeShowNum(showNum, item, false);
//        holder.setText(R.id.tv_show, item.share_num);

        holder.setText(R.id.tv_ok, item.praise_num);

        String content = item.content;
        String topic_title = item.topic_title;
        int index = content.indexOf(topic_title);
        //发布时间
        holder.setText(R.id.newcampayfunitem_time, item.add_time);
//        //文本内容
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.title_color)), index, topic_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.setText(R.id.newcampayfunitem_caontant, spannableString);


        if (item.thumbnail_list != null && item.thumbnail_list.get(0) != null && item.thumbnail_list.get(0).url != null && item.thumbnail_list.get(0).url.length() > 1) {
            holder.setVisible(R.id.nine_grid_view, true);
            ((NineGridView) holder.getView(R.id.nine_grid_view)).setVisibility(View.VISIBLE);
            System.out.println(item.thumbnail_list.size() + "隐藏w " + position);
            clickNineGridView((NineGridView) holder.getView(R.id.nine_grid_view), item.thumbnail_list, item.images_list, position);
        } else {
            System.out.println(item.thumbnail_list.size() + "隐藏ee" + position);
            holder.setVisible(R.id.nine_grid_view, false);
            ((NineGridView) holder.getView(R.id.nine_grid_view)).setVisibility(View.GONE);
        }
    }

    private void changeShowNum(int showNum, FunContantInfo item, boolean is) {
        tvShowNum.setText(String.valueOf(showNum));
        item.share_num = String.valueOf(showNum);
        if (is)
            notifyDataSetChanged();
    }


    private void clickNineGridView(NineGridView nineGridView, List<SingleImageBean> tem, List<SingleImageBean> image, int position) {

        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < tem.size(); i++) {
            urlList.add(i, tem.get(i).url);
        }

        final ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        for (int i = 0; i < tem.size(); i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(tem.get(i).url);
            info.setBigImageUrl(image.get(i).url);
            imageInfo.add(info);
        }

        nineGridView.setAdapter(new NineImageAdapter(mContext, urlList));
        nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(mContext, imageInfo, position);
                photoShowDialog.show();
            }
        });

    }

    private void clickOne(View view, FunContantInfo item, int position) {
        if (position == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

    }

    private void clickImage(View view, final FunContantInfo item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.newcampayfunitem_uesericon)
                    Goto.toHomePage(item, context);

                AppConfig.mFunContantInfo = item;

                if (view.getId() == R.id.all || view.getId() == R.id.linearLayout_chat_number || view.getId() == R.id.blank)
                    Goto.toChatPage(item, context);
            }
        });
    }


    private void show(final BaseViewHolder holder, final FunContantInfo item, int position) {

        //分享

        holder.getView(R.id.linearLayout_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppConfig.ISlOGINED) {
                    addShareMeun(holder, item);
                } else {
                    new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Goto.toLogin(context);
                        }
                    }).setNegativeButton("取消", null).show();
                }

            }
        });

    }

    boolean returnFlag = false;

    public boolean dianZan(final BaseViewHolder holder, final FunContantInfo item, int position) {
        //点赞
        holder.getView(R.id.linearLayout_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppConfig.ISlOGINED) {

                    presenter.praise(1, item.dynamic_id, new ApproveListener() {
                        @Override
                        public void actionSuccess(int flag, int number) {
                            item.is_praise = flag + "";
                            item.praise_num = number + "";

                            ((TextView) (holder.getView(R.id.tv_ok))).setText(number + "");
                            if (flag == 2) {
                                ((ImageView) (holder.getView(R.id.img_ok))).setImageResource(R.drawable.chat_ok);
                                ((TextView) (holder.getView(R.id.tv_ok))).setTextColor(Color.RED);
                            } else {
                                ((ImageView) (holder.getView(R.id.img_ok))).setImageResource(R.drawable.chat_no);
                                ((TextView) (holder.getView(R.id.tv_ok))).setTextColor(Color.BLACK);
                            }
                        }

                        @Override
                        public void actionFail() {
                            returnFlag = false;
                        }
                    });

                } else {
                    new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Goto.toLogin(context);
                        }
                    }).setNegativeButton("取消", null).show();
                }
            }
        });
        return returnFlag;
    }


    /**
     * 分享popwindow
     */
    private void addShareMeun(BaseViewHolder holder, final FunContantInfo item) {
        if (windowAction != null) {
            windowAction.dismiss();
        }
        contentViewShare = LayoutInflater.from(context).inflate(
                R.layout.sharemenu, null);
        // 创建PopupWindow对象
        windowShare = new PopupWindow(contentViewShare, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        TextView qq = contentViewShare.findViewById(R.id.sharemeun_qqfriend);
        TextView qqZone = contentViewShare.findViewById(R.id.sharemeun_qqzone);
        TextView wechat = contentViewShare.findViewById(R.id.sharemeun_wechatfriend);
        TextView moment = contentViewShare.findViewById(R.id.sharemeun_moment);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doShare(SHARE_MEDIA.QQ, item);
            }
        });
        qqZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doShare(SHARE_MEDIA.QZONE, item);
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doShare(SHARE_MEDIA.WEIXIN, item);
            }
        });
        moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE, item);
            }
        });
        windowShare.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        windowShare.setOutsideTouchable(true);
        windowShare.setAnimationStyle(R.style.pop_anim);
        // 设置此参数获得焦点，否则无法点击
        windowShare.setFocusable(true);
        windowShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 分享
     *
     * @param
     */
    private void doShare(SHARE_MEDIA media, FunContantInfo item) {
        dynamic_id = item.dynamic_id;
        UMWeb web = new UMWeb(item.share_url);
        web.setTitle(item.content);//标题
        new ShareAction((Activity) context)
                .setPlatform(media)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        shownumb(dynamic_id, item);
//                        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();


                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param platform 平台类型
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
                    }
                })
                .share();
    }


    private void shownumb(int dynamic_id, FunContantInfo item) {

        FunCampusModel funCampusModel = new FunCampusModel((Activity) context);
        funCampusModel.setShowNum(dynamic_id, new GetShowListener() {
            @Override
            public void SuccessInfo(int share_num) {
                changeShowNum(share_num, item, true);
            }

            @Override
            public void ErrorInfo() {

            }
        });

    }
}
