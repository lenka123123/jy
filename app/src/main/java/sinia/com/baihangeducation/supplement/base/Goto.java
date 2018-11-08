package sinia.com.baihangeducation.supplement.base;

import android.content.Context;
import android.content.Intent;

import com.example.framwork.utils.BaseGoto;

import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.club.addclub.AddCLubActivity;
import sinia.com.baihangeducation.club.applyclublist.ApplyClubListActivity;
import sinia.com.baihangeducation.club.applyclublist.ClubPersonClubListActivity;
import sinia.com.baihangeducation.club.clubactive.ClubActiveDetailActivity;
import sinia.com.baihangeducation.club.clubannouncedetail.ClubAnnounceDetailActivity;
import sinia.com.baihangeducation.club.clubcomment.CommentActivity;
import sinia.com.baihangeducation.club.clubdetail.ClubDetailActivity;
import sinia.com.baihangeducation.club.clubpaint.ClubPaintActivity;
import sinia.com.baihangeducation.club.clubschoollist.ClubDisparkActivity;
import sinia.com.baihangeducation.club.clubschoollist.ClubSchoolListActivity;
import sinia.com.baihangeducation.club.clubsendannounce.ClubSendAnnounceActivity;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorActiveActivity;
import sinia.com.baihangeducation.club.editorclubactive.ClubShowActiveActivity;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerActivity;
import sinia.com.baihangeducation.club.mangerpower.MangerPowerActivity;
import sinia.com.baihangeducation.club.myclub.help.ClubApplyHelpActivity;
import sinia.com.baihangeducation.club.myclub.help.ClubApplyHelpShowActivity;
import sinia.com.baihangeducation.club.myclub.help.ClubHelpActivity;
import sinia.com.baihangeducation.club.myclub.myactive.ClubMyActiveActivity;
import sinia.com.baihangeducation.club.myclub.myclub.ClubMyClubActivity;
import sinia.com.baihangeducation.club.myclub.myparttime.ClubMyPartTimeActivity;
import sinia.com.baihangeducation.club.notice.ClubNoticeActivity;
import sinia.com.baihangeducation.club.personcenter.PersonCenterActivity;
import sinia.com.baihangeducation.club.searchschool.SearchListActivity;
import sinia.com.baihangeducation.club.systemmessage.ClubSystemListActivity;
import sinia.com.baihangeducation.find.activity.JobBangPayDetailActivity;
import sinia.com.baihangeducation.home.activity.CompleteMajorStageActivity;
import sinia.com.baihangeducation.home.activity.PartTimeJobCLubDetailActivity;
import sinia.com.baihangeducation.home.activity.PortionActivity;
import sinia.com.baihangeducation.mine.activity.MySendCommentActivity;
import sinia.com.baihangeducation.newcampus.activity.CommentPageActivity;
import sinia.com.baihangeducation.newcampus.activity.HomePageActivity;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.view.GetFriendListActivity;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusForActivity;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.MyCoffers;
import sinia.com.baihangeducation.reconsitution.tabs.pay.tab.PaiedListActivity;
import sinia.com.baihangeducation.reconsitution.tabs.selectcity.SelectCityActivity;
import sinia.com.baihangeducation.release.activity.FullTimeActivity;
import sinia.com.baihangeducation.receiver.JPushDialogActivity;
import sinia.com.baihangeducation.supplement.web.ShareWebViewActivity;
import sinia.com.baihangeducation.release.activity.BelongIndutryActivity;
import sinia.com.baihangeducation.release.activity.ReleaseJobInfoActivity;
import sinia.com.baihangeducation.release.activity.ReleaseTrainingAcgtivity;
import sinia.com.baihangeducation.release.activity.SchoolFunActivity;
import sinia.com.baihangeducation.release.activity.SiftUrNeedActivity;
import sinia.com.baihangeducation.release.activity.TagActivity;
import sinia.com.baihangeducation.find.campus.activity.CampusInterestingDetailActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyUCenterEditTrainingActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyUCenterInfoActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyUCenterReleaseAllTimeActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyUCenterReleaseTrainingActivity;
import sinia.com.baihangeducation.find.activity.JobBangClassActivity;
import sinia.com.baihangeducation.find.activity.JobBangClassSecretActivity;
import sinia.com.baihangeducation.find.activity.JobBangClassStrategyActivity;
import sinia.com.baihangeducation.find.activity.JobBangClassTestActivity;
import sinia.com.baihangeducation.find.activity.JobBangDetailActivity;
import sinia.com.baihangeducation.find.activity.SearchActivity;
import sinia.com.baihangeducation.find.activity.SearchReasultActivity;
import sinia.com.baihangeducation.find.activity.ShareEveryDayActivity;
import sinia.com.baihangeducation.find.activity.ShareEveryDayDetailActivity;
import sinia.com.baihangeducation.home.activity.AllJobDetailActivity;
import sinia.com.baihangeducation.home.activity.CompanyDetailActivity;
import sinia.com.baihangeducation.home.activity.CompanyJoinInActivity;
import sinia.com.baihangeducation.home.activity.CompleteInfoActivity;
import sinia.com.baihangeducation.home.activity.CompleteMajorStageThreeActivity;
import sinia.com.baihangeducation.home.activity.CompleteSchoolActivity;
import sinia.com.baihangeducation.home.activity.HomeAndFindHelpEachOtherActivity;
import sinia.com.baihangeducation.home.activity.HomeAndFindHelpEachOtherDetailActivity;
import sinia.com.baihangeducation.home.activity.HomeHunterActivity;
import sinia.com.baihangeducation.home.activity.HomePartTimeActivity;
import sinia.com.baihangeducation.home.activity.HomeTraingActivity;
import sinia.com.baihangeducation.home.activity.MessageActivity;
import sinia.com.baihangeducation.home.activity.PartTimeJobDetailActivity;
import sinia.com.baihangeducation.home.activity.ReCommendedActivity;
import sinia.com.baihangeducation.home.activity.TraingDetailActivity;
import sinia.com.baihangeducation.home.activity.TraingJoinInActivity;

import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;
import com.mcxtzhang.swipemenulib.info.bean.CommentInfo;

import sinia.com.baihangeducation.mine.activity.AboutJobBangActivity;
import sinia.com.baihangeducation.mine.activity.AddJobExperienceActivity;
import sinia.com.baihangeducation.mine.activity.AlreadyGetTaskActivity;
import sinia.com.baihangeducation.mine.activity.BindingPhoneActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyEditJobActivity;
import sinia.com.baihangeducation.minecompany.avtivity.CompanyUCenterReleasePartTimeActivity;
import sinia.com.baihangeducation.mine.activity.ForgetPassword2Activity;
import sinia.com.baihangeducation.mine.activity.ForgetPasswordActivity;
import sinia.com.baihangeducation.mine.activity.FragmentMessageActivity;
import sinia.com.baihangeducation.mine.activity.FragmentMessageDetailActivity;
import sinia.com.baihangeducation.mine.activity.HelpEachOtherActivity;
import sinia.com.baihangeducation.mine.activity.InvitationActivity;
import sinia.com.baihangeducation.mine.activity.LinkUsActivity;
import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.mine.activity.MyApplyListActivity;
import sinia.com.baihangeducation.mine.activity.MyCollectionActivity;
import sinia.com.baihangeducation.mine.activity.MyCouponsActivity;
import sinia.com.baihangeducation.mine.activity.MyGrowthActivity;
import sinia.com.baihangeducation.mine.activity.MyReleaseActivity;
import sinia.com.baihangeducation.mine.activity.MyRelease_ReleaseActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeEditBaseInfoActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeEditEducationExpActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeEditEducationExpChoiceMajor_1Acitvity;
import sinia.com.baihangeducation.mine.activity.MyResumeEditEducationExpChoiceMajor_2Acitvity;
import sinia.com.baihangeducation.mine.activity.MyResumeEditEducationExpChoiceSchoolAcitvity;
import sinia.com.baihangeducation.mine.activity.MyResumeHealthCardActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeJobInfoActivity;
import sinia.com.baihangeducation.mine.activity.MyResumeStudentCardActivity;
import sinia.com.baihangeducation.mine.activity.MyReumeSelfAssessmentActivity;
import sinia.com.baihangeducation.mine.activity.MySendActivity;
import sinia.com.baihangeducation.mine.activity.MySettingActivity;
import sinia.com.baihangeducation.mine.activity.NewTaskActivity;
import sinia.com.baihangeducation.mine.activity.NewUserGuideActivity;
import sinia.com.baihangeducation.mine.activity.PartTimeActivity;
import sinia.com.baihangeducation.mine.activity.RealNameActivity;
import sinia.com.baihangeducation.mine.activity.RegisterActivity;
import sinia.com.baihangeducation.release.campus.ReleaseFunActivity;
import sinia.com.baihangeducation.mine.activity.ReleaseHelpEachOtherActivity;
import sinia.com.baihangeducation.mine.activity.ReleaseInterestingActiviyy;
import sinia.com.baihangeducation.mine.activity.SingleActivity;
import sinia.com.baihangeducation.mine.activity.TaskActivity;
import sinia.com.baihangeducation.mine.activity.TraingActivity;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.mine.activity.UnGetAuthCodeActivity;
import sinia.com.baihangeducation.mine.activity.UpdataPwdActivity;
import sinia.com.baihangeducation.mine.activity.UserRuleActivity;
import sinia.com.baihangeducation.mine.activity.WantJobActivity;

import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018.03.09.
 */

public class Goto extends BaseGoto {

    public static void toGuide(Context context) {
        Intent intent = new Intent(context, GuidePageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到登录页面
     *
     * @param context
     */
    public static void toLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到下线提示页面
     *
     * @param context
     */
    public static void toJPushDialogActivity(Context context) {

        Intent intent = new Intent(context, JPushDialogActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到注册页面
     *
     * @param context
     */
    public static void toRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到用户协议
     *
     * @param context
     */
    public static void toUserRuleActivity(Context context) {
        Intent intent = new Intent(context, UserRuleActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到忘记密码页面
     *
     * @param context
     */
    public static void toForgetPassword(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    /**
     * 到忘记密码页面第二页
     *
     * @param context
     */
    public static void toForgetPassword2(Context context, String phoneName, String AuthCode) {
        Intent intent = new Intent(context, ForgetPassword2Activity.class);
        intent.putExtra("PhoneName", phoneName);
        intent.putExtra("AuthCode", AuthCode);
        context.startActivity(intent);
    }

    /**
     * 到无法获取验证码页面
     *
     * @param context
     */
    public static void toUnGetAuthCode(Context context) {
        Intent intent = new Intent(context, UnGetAuthCodeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转第三方绑定
     *
     * @param context
     * @param uid
     */
    public static void toThirdBind(Context context, String uid, String type) {
        Intent intent = new Intent(context, BindingPhoneActivity.class);
        intent.putExtra("UID", uid);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }

    /**
     * 跳转首页
     */
    public static void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转简历页面
     */
    public static void toMyResumeActivity(Context context) {
        Intent intent = new Intent(context, MyResumeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转简历页面
     */
    public static void toMyResumeActivity(Context context, String fromflag) {
        Intent intent = new Intent(context, MyResumeActivity.class);
        intent.putExtra("FROM", fromflag);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历编辑资本资料页面
     */
    public static void toEditMyResumeBaseInfo(Context context) {
        Intent intent = new Intent(context, MyResumeEditBaseInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历编辑教育经历页面
     */
    public static void toEditMyResumeEducationExp(Context context) {
        Intent intent = new Intent(context, MyResumeEditEducationExpActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历编辑教育经历选择学校页面
     */
    public static void toEditMyResumeEducationExpChoiceSchool(Context context, String msg) {
        Intent intent = new Intent(context, MyResumeEditEducationExpChoiceSchoolAcitvity.class);
        intent.putExtra("active", msg);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历编辑专业选择第一个页面
     */
    public static void toEditMyResumeEducationExpChoice_1Major(Context context) {
        Intent intent = new Intent(context, MyResumeEditEducationExpChoiceMajor_1Acitvity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历编辑专业选择第一个页面
     */
    public static void toEditMyResumeEducationExpChoice_2Major(Context context, String marjorID) {
        Intent intent = new Intent(context, MyResumeEditEducationExpChoiceMajor_2Acitvity.class);
        intent.putExtra("MAJORID", marjorID);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历学生证页面
     */
    public static void toEditMyResumeStudentCard(Context context) {
        Intent intent = new Intent(context, MyResumeStudentCardActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历健康证页面
     */
    public static void toEditMyResumeHealthCard(Context context) {
        Intent intent = new Intent(context, MyResumeHealthCardActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历健康证页面
     */
    public static void toMyReumeSelfAssessmentActivity(Context context) {
        Intent intent = new Intent(context, MyReumeSelfAssessmentActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历 工作经历list页面
     */
    public static void toMyResumeJobInfoActivity(Context context) {
        Intent intent = new Intent(context, MyResumeJobInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转我的简历添加工作简历页面
     */
    public static void toAddJobExperienceActivity(Context context) {
        Intent intent = new Intent(context, AddJobExperienceActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心   基本资料
     */
    public static void toUCentreBaseInfoActivity(Context context) {
        Intent intent = new Intent(context, UCentreBaseInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心我的优惠券页面
     */
    public static void toMyCouponsActivity(Context context) {
        Intent intent = new Intent(context, MyCouponsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心  设置 修改密码页面
     */
    public static void toUpdataPwdActivity(Context context) {
        Intent intent = new Intent(context, UpdataPwdActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心  设置 关于就业邦
     */
    public static void toAboutJobBangActivity(Context context) {
        Intent intent = new Intent(context, AboutJobBangActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心  设置 联系我们页面
     */
    public static void toLinkUsActivity(Context context) {
        Intent intent = new Intent(context, LinkUsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心  设置 页面
     */
    public static void toMySettingActivity(Context context) {
        Intent intent = new Intent(context, MySettingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转新手指南
     */
    public static void toNewUserGuideActivity(Context context) {
        Intent intent = new Intent(context, NewUserGuideActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心  实名认证页面
     */
    public static void toRealNameActivity(Context context) {
        Intent intent = new Intent(context, RealNameActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的发布页面
     */
    public static void toMyReleaseActivity(Context context) {
        Intent intent = new Intent(context, MyReleaseActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的发布 添加发布页面
     */
    public static void toMyRelease_ReleaseActivity(Context context) {
        Intent intent = new Intent(context, MyRelease_ReleaseActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的发布 添加发布  校园互助页面
     */
    public static void toReleaseHelpEachOtherActivity(Context context) {
        Intent intent = new Intent(context, ReleaseHelpEachOtherActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的发布 申请列表
     */
    public static void toMyApplyListActivity(Context context, int id) {
        Intent intent = new Intent(context, MyApplyListActivity.class);
        intent.putExtra("COOPID", id + "");
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的发布 添加发布  校园趣事页面
     */
    public static void toReleaseInterestingActiviyy(Context context) {
        Intent intent = new Intent(context, ReleaseInterestingActiviyy.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 消息
     */
    public static void toFragmentMessageActivity(Context context) {
        Intent intent = new Intent(context, FragmentMessageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 消息详情
     */
    public static void toFragmentMessageDetailActivity(Context context, FragmentMessageInfo info) {
        Intent intent = new Intent(context, FragmentMessageDetailActivity.class);
        intent.putExtra("MESSAGE", info);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的收藏
     */
    public static void toMyCollectionActivity(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的正常
     */
    public static void toMyGrowthActivity(Context context) {
        Intent intent = new Intent(context, MyGrowthActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的成长  做兼职
     */
    public static void toPartTimeActivity(Context context) {
        Intent intent = new Intent(context, PartTimeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 我的兼职
     */
    public static void toMySendCommentActivity(Context context) {
        Intent intent = new Intent(context, MySendCommentActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的成长  参加培训
     */
    public static void toTraingActivity(Context context) {
        Intent intent = new Intent(context, TraingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的成长  邀请好友
     */
    public static void toInvitationActivity(Context context) {
        Intent intent = new Intent(context, InvitationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的成长  校园互助
     */
    public static void toHelpEachOtherActivity(Context context) {
        Intent intent = new Intent(context, HelpEachOtherActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的成长  签到页面
     */
    public static void toSingleActivity(Context context) {
        Intent intent = new Intent(context, SingleActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 我的投递
     */
    public static void toMySendActivity(Context context) {
        Intent intent = new Intent(context, MySendActivity.class);
        context.startActivity(intent);
    }

    public static void toMySendActivityFull(Context context) {
        Intent intent = new Intent(context, MySendActivity.class);
        intent.putExtra("full", "full");
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 意向求职
     */
    public static void toWantJobActivity(Context context) {
        Intent intent = new Intent(context, WantJobActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转个人中心 任务
     */
    public static void toTaskActivity(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转任务 可领取的任务
     */
    public static void toNewTaskActivity(Context context) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转任务 已领取的任务
     */
    public static void toAlreadyGetTaskActivity(Context context) {
        Intent intent = new Intent(context, AlreadyGetTaskActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转任务 已领取的任务
     */
    public static void toSearchReasultActivity(Context context, String contant) {
        Intent intent = new Intent(context, SearchReasultActivity.class);
        intent.putExtra("keyword", contant);
        context.startActivity(intent);
    }

    /**
     * 跳转搜索页面
     */
    public static void toSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转每日分享
     */
    public static void toShareEveryDayActivity(Context context) {
        Intent intent = new Intent(context, ShareEveryDayActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转每日分享详情
     */
    public static void toShareEveryDayDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, ShareEveryDayDetailActivity.class);
        intent.putExtra("NEWSID", id + "");
        context.startActivity(intent);
    }

    /**
     * 跳转校园趣事详情页面
     */
    public static void toCampusInterestingDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, CampusInterestingDetailActivity.class);
        intent.putExtra("FUNID", id + "");
        context.startActivity(intent);
    }

    /**
     * 跳转每日分享详情
     * id:咨询ID
     * cocllectionType：收藏种类
     * messageId：留言ID
     */
    public static void toJobBangDetailActivity(Context context, int id, int cocllectionType, int messageId, String typename) {
        Intent intent = new Intent(context, JobBangDetailActivity.class);
        intent.putExtra("RAIDERID", id + "");
        intent.putExtra("collectionID", cocllectionType + "");
        intent.putExtra("messageID", messageId + "");
        intent.putExtra("typename", typename);
        context.startActivity(intent);
    }

    /**
     * 跳转每日分享详情
     * id:咨询ID
     * cocllectionType：收藏种类
     * messageId：留言ID
     */
    public static void toJobBangDetailActivity(Context context, int id, String typename, int cocllectionType, int messageId) {
        //
        Intent intent = new Intent(context, JobBangDetailActivity.class);
        intent.putExtra("RAIDERID", id + "");
        intent.putExtra("collectionID", cocllectionType + "");
        intent.putExtra("messageID", messageId + "");
        intent.putExtra("typename", typename);
        context.startActivity(intent);
    }


    /**
     * 跳转每日分享详情
     * id:咨询ID
     * cocllectionType：收藏种类
     * messageId：留言ID
     */
    public static void toJobBangPayDetailActivity(Context context, int id, String typename, int cocllectionType, int messageId) {
        Intent intent = new Intent(context, JobBangPayDetailActivity.class);
        intent.putExtra("RAIDERID", id + "");
        intent.putExtra("collectionID", cocllectionType + "");
        intent.putExtra("messageID", messageId + "");
        intent.putExtra("typename", typename);
        context.startActivity(intent);
    }

    /**
     * 跳转就业邦学堂
     */
    public static void toJobBangClassActivity(Context context) {
        Intent intent = new Intent(context, JobBangClassActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转就业邦学堂 攻略干货
     */
    public static void toJobBangClassStrategyActivity(Context context) {
        Intent intent = new Intent(context, JobBangClassStrategyActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转就业邦学堂 职场秘籍
     */
    public static void toJobBangClassSecretActivity(Context context) {
        Intent intent = new Intent(context, JobBangClassSecretActivity.class);
        context.startActivity(intent);
    }


    /**
     * 跳转就业邦学堂 考研试卷
     */
    public static void toJobBangClassTestActivity(Context context) {
        Intent intent = new Intent(context, JobBangClassTestActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页或发现跳转校园互助
     */
    public static void toHomeAndFindHelpEachOtherActivity(Context context) {
        Intent intent = new Intent(context, HomeAndFindHelpEachOtherActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页 推荐页面
     */
    public static void toReCommendedActivity(Context context) {
        Intent intent = new Intent(context, ReCommendedActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页 培训页面
     */
    public static void toHomeTraingActivity(Context context) {
        Intent intent = new Intent(context, HomeTraingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页 兼职页面
     */
    public static void toHomePartTimeActivity(Context context) {
        Intent intent = new Intent(context, HomePartTimeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页 兼职页面
     */
    public static void toHomePartTimeActivity(Context context, String indutryId, String money_id, String areaId, String worktime_id,
                                              String distance_id, String sex_id, String pubtime_id) {
        Intent intent = new Intent(context, HomePartTimeActivity.class);

        // industryId, money_id, areaId, worktime_id,distance_id,sex_id,pubtime_id
        intent.putExtra("indutryId", indutryId);
        intent.putExtra("money_id", money_id);
        intent.putExtra("areaId", areaId);
        intent.putExtra("worktime_id", worktime_id);
        intent.putExtra("distance_id", distance_id);
        intent.putExtra("sex_id", sex_id);
        intent.putExtra("pubtime_id", pubtime_id);
        context.startActivity(intent);
    }

    /**
     * 首页 猎头页面
     */
    public static void toHomeHunterActivity(Context context) {
        Intent intent = new Intent(context, HomeHunterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页 猎头页面
     */
    public static void toHomeHunterActivity(Context context, String indutryId, String salaryId, String areaId, String orderId) {
        Intent intent = new Intent(context, HomeHunterActivity.class);
        intent.putExtra("INDUSTRYID", indutryId);
        intent.putExtra("SALARYID", salaryId);
        intent.putExtra("AREAID", areaId);
        intent.putExtra("ORDERID", orderId);
        context.startActivity(intent);
    }

    /**
     * 首页 企业入住
     */
    public static void toCompanyJoinInActivity(Context context) {
        Intent intent = new Intent(context, CompanyJoinInActivity.class);
        context.startActivity(intent);
    }

    /**
     * 兼职详情
     */
    public static void toPartTimeJobDetailActivityForHome(Context context, int id, int type) {
        Intent intent = new Intent(context, PartTimeJobDetailActivity.class);
        intent.putExtra("JOBID", id + "");
        intent.putExtra("phone", "123");
        if (type == 3) {
            intent.putExtra("club", "club");
        } else {
            intent.putExtra("club", "");
        }

        context.startActivity(intent);
    }

    /**
     * 兼职详情
     */
    public static void toPartTimeJobDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, PartTimeJobDetailActivity.class);
        intent.putExtra("JOBID", id + "");
        intent.putExtra("club", "");
        context.startActivity(intent);
    }

    /**
     * club  兼职详情
     */
    public static void toPartTimeJobDetailActivityForClub(Context context, int id, String phone) {
        Intent intent = new Intent(context, PartTimeJobDetailActivity.class);
        intent.putExtra("JOBID", id + "");
        intent.putExtra("club", "club");
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    /**
     * 兼职俱乐部详情
     */
    public static void toPartTimeJobClubDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, PartTimeJobCLubDetailActivity.class);
        intent.putExtra("JOBID", id + "");
        context.startActivity(intent);
    }

    /**
     * 全职详情
     */
    public static void toAllJobDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, AllJobDetailActivity.class);
        intent.putExtra("JOBID", id + "");
        context.startActivity(intent);
    }

    /**
     * 培训详情
     */
    public static void toTraingDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, TraingDetailActivity.class);
        intent.putExtra("TRAINID", id + "");
        context.startActivity(intent);
    }

    /**
     * 公司详情
     */
    public static void toCompanyDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, CompanyDetailActivity.class);
        intent.putExtra("COMPANYID", id + "");
        context.startActivity(intent);
    }

    /**
     * 互助详情
     */
    public static void toHomeAndFindHelpEachOtherDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, HomeAndFindHelpEachOtherDetailActivity.class);
        intent.putExtra("cooperation_id", id + "");
        context.startActivity(intent);
    }

    /**
     * 培训 立即报名
     */
    public static void toTraingJoinInActivity(Context context, TraingDetailInfo info) {
        Intent intent = new Intent(context, TraingJoinInActivity.class);
        intent.putExtra("TRAININGINFO", info);
        context.startActivity(intent);
    }

    /**
     * 留言 带父级评论
     */
    public static void toMessageActivity(Context context, CommentInfo info) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("COMMENT", info);
        context.startActivity(intent);
    }

    /**
     * 留言 带父级评论
     */
    public static void toMessageActivity(Context context, int id, String type) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("typeid", id + "");
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料页面
     */
    public static void toCompleteInfoActivity(Context context) {
        Intent intent = new Intent(context, CompleteInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料学校页面
     */
    public static void toCompleteSchoolActivity(Context context) {
        Intent intent = new Intent(context, CompleteSchoolActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业一页面
     */
    public static void toCompleteMajor_1Activity(Context context) {
        Intent intent = new Intent(context, CompleteMajorStageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toCompleteMajor_2Activity(Context context, String marjorID) {
        Intent intent = new Intent(context, CompleteMajorStageThreeActivity.class);
        intent.putExtra("MAJORID", marjorID);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toSchoolFunActivity(Context context) {
        Intent intent = new Intent(context, SchoolFunActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toSiftUrNeedActivity(Context context, String flag) {
        if (flag.equals("1")) { //我要找全职
            Intent intent = new Intent(context, FullTimeActivity.class);
            intent.putExtra("FLAG", flag);
            context.startActivity(intent);

        } else {

            Intent intent = new Intent(context, SiftUrNeedActivity.class);
            intent.putExtra("FLAG", flag);
            context.startActivity(intent);
        }
    }

    /**
     * 发布职位  发布兼职
     */
    public static void toReleaseJobInfoActivity(Context context) {
        Intent intent = new Intent(context, ReleaseJobInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toBelongIndutryActivity(Context context) {
        Intent intent = new Intent(context, BelongIndutryActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toTagActivity(Context context) {
        Intent intent = new Intent(context, TagActivity.class);
        context.startActivity(intent);
    }

    /**
     * 首页跳转完善资料专业二页面
     */
    public static void toReleaseTrainingAcgtivity(Context context) {
        Intent intent = new Intent(context, ReleaseTrainingAcgtivity.class);
        context.startActivity(intent);
    }

    /**
     *
     */
    public static void toReleaseFunActivity(Context context) {
        Intent intent = new Intent(context, ReleaseFunActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转Mywebview
     *
     * @param context
     * @param title
     * @param url
     * @param bgId    标题栏背景id 如果不需要传0
     */

    public static void toShareWebViewActivity(Context context, String title, String url, int bgId) {
        Intent intent = new Intent(context, ShareWebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("title_bg", bgId);
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心发布的兼职页面
     *
     * @param context
     */
    public static void toCompanyUCenterReleasePartTimeActivity(Context context) {
        Intent intent = new Intent(context, CompanyUCenterReleasePartTimeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心发布的兼职编辑页面
     *
     * @param context
     */
    public static void toCompanyEditJobActivity(Context context, int jobId) {
        Intent intent = new Intent(context, CompanyEditJobActivity.class);
        intent.putExtra("MYRELEASEJOBID", jobId + "");
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心发布的全职页面
     *
     * @param context
     */
    public static void toCompanyUCenterReleaseAllTimeActivity(Context context) {
        Intent intent = new Intent(context, CompanyUCenterReleaseAllTimeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心发布的培训页面
     *
     * @param context
     */
    public static void toCompanyUCenterReleaseTrainingActivity(Context context) {
        Intent intent = new Intent(context, CompanyUCenterReleaseTrainingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心发布的培训页面
     *
     * @param context
     */
    public static void toCompanyUCenterEditTrainingActivity(Context context, String triaingId) {
        Intent intent = new Intent(context, CompanyUCenterEditTrainingActivity.class);
        intent.putExtra("TRAININGID", triaingId);
        context.startActivity(intent);
    }

    /**
     * 跳转企业版个人中心编辑企业信息页面
     *
     * @param context
     */
    public static void toCompanyUCenterInfoActivity(Context context) {
        Intent intent = new Intent(context, CompanyUCenterInfoActivity.class);
        context.startActivity(intent);
    }


    public static void toHomePage(FunContantInfo item, Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);

        intent.putExtra("dynamic_id", item.dynamic_id + "");
        intent.putExtra("user_id", item.publish_user_id + "");
        context.startActivity(intent);
    }

    public static void toHomePageAgain(String user_id, Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        intent.putExtra("dynamic_id", "");
        intent.putExtra("user_id", user_id);
        context.startActivity(intent);
    }


    public static void toAddFriend(Context context) {
        Intent intent = new Intent(context, GetFriendListActivity.class);
        context.startActivity(intent);


    }


    public static void toChatPage(FunContantInfo item, Context context) {

        Intent intent = new Intent(context, CommentPageActivity.class);

        intent.putExtra("dynamic_id", item.dynamic_id + "");
        intent.putExtra("user_id", item.publish_user_id + "");


        context.startActivity(intent);
    }

    //我的金库
    public static void toMyCoffersActivity(Context context) {
        Intent intent = new Intent(context, MyCoffers.class);
        context.startActivity(intent);
    }

    //选择城市
    public static void toSelectCityActivity(Context context) {
        Intent intent = new Intent(context, SelectCityActivity.class);
        context.startActivity(intent);
    }

    //我的购买
    public static void toMyPayActivity(Context context) {
        Intent intent = new Intent(context, PaiedListActivity.class);
        context.startActivity(intent);
    }

    //club 详情
    public static void toClubDetailActivity(Context context, String club_id) {
        Intent intent = new Intent(context, ClubDetailActivity.class);
        intent.putExtra("club_id", club_id);
        context.startActivity(intent);
    }

    //club 搜索
    public static void toClubSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchListActivity.class);
        context.startActivity(intent);
    }

    //club 热门社团
    public static void toClubSchoolListActivity(Context context) {
        Intent intent = new Intent(context, ClubSchoolListActivity.class);
        context.startActivity(intent);
    }

    //club 申请列表
    public static void toApplyClubListActivity(Context context, String clubId) {
        Intent intent = new Intent(context, ApplyClubListActivity.class);
        intent.putExtra("club_id", clubId);
        context.startActivity(intent);
    }

    //club 成员列表
    public static void toClubPersonClubListActivity(Context context, String clubId, boolean drop, boolean setClubApply, String is_chairman) {
        Intent intent = new Intent(context, ClubPersonClubListActivity.class);
        intent.putExtra("is_chairman", is_chairman);
        intent.putExtra("club_id", clubId);
        intent.putExtra("drop", drop);
        intent.putExtra("setClubApply", setClubApply);
        context.startActivity(intent);
    }

    //club 公告编辑
    public static void toClubSendAnnounceActivity(Context context, String typeName, String clubId, String notice_id, String title, String content,
                                                  boolean dropSchoolNotice, boolean editSchoolNotice, boolean pushSchoolNotice) {
        Intent intent = new Intent(context, ClubSendAnnounceActivity.class);
        intent.putExtra("club_id", clubId);
        intent.putExtra("typeName", typeName);
        intent.putExtra("notice_id", notice_id);

        intent.putExtra("title", title);
        intent.putExtra("content", content);

        intent.putExtra("dropSchoolNotice", dropSchoolNotice);
        intent.putExtra("editSchoolNotice", editSchoolNotice);
        intent.putExtra("pushSchoolNotice", pushSchoolNotice);

        context.startActivity(intent);
    }

    //club 公告详情
    public static void toClubAnnounceDetailActivity(Context context, String type, String clubid, String notice_id,
                                                    boolean dropNotice, boolean editNotice, boolean dropSchoolNotice,
                                                    boolean editSchoolNotice, boolean pushSchoolNotice) {
        Intent intent = new Intent(context, ClubAnnounceDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("club_id", clubid);
        intent.putExtra("notice_id", notice_id);
        intent.putExtra("dropNotice", dropNotice);
        intent.putExtra("editNotice", editNotice);

        intent.putExtra("dropSchoolNotice", dropSchoolNotice);
        intent.putExtra("editSchoolNotice", editSchoolNotice);
        intent.putExtra("pushSchoolNotice", pushSchoolNotice);
        /**
         *
         power.contains("dropSchoolNotice"),
         power.contains("editSchoolNotice"),
         power.contains("pushSchoolNotice")
         */
        context.startActivity(intent);
    }

    //club 编辑简介
    public static void toClubNoticeActivity(Context context, String clubid, String introduce) {
        Intent intent = new Intent(context, ClubNoticeActivity.class);
        intent.putExtra("club_id", clubid);
        intent.putExtra("introduce", introduce);
        context.startActivity(intent);
    }

    //club 编辑简介
    public static void toCommentActivity(Context context, String job_title, String job_city_name, String job_money, int job_apply_id, String iscommnent) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("job_title", job_title);
        intent.putExtra("job_city_name", job_city_name);
        intent.putExtra("job_money", job_money);
        intent.putExtra("job_apply_id", job_apply_id);
        intent.putExtra("is_comment", iscommnent);
        context.startActivity(intent);
    }


    //club 热门活动
    public static void toHotActive(Context context, String type, String clubid) {
        Intent intent = new Intent(context, ClubActiveDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("clubid", clubid);
        context.startActivity(intent);
    }

    //club  热门活动
    public static void toShowHotActive(Context context, String activity_id) {
        Intent intent = new Intent(context, ClubShowActiveActivity.class);
        intent.putExtra("activity_id", activity_id);
        context.startActivity(intent);
    }

    //club 编辑热门活动
    public static void toEditorActive(Context context, String clubid) {
        Intent intent = new Intent(context, ClubEditorActiveActivity.class);
        intent.putExtra("clubid", clubid);
        context.startActivity(intent);
    }

    //club 社团兼职
    public static void toClubPart(Context context, boolean pushJob) {
        Intent intent = new Intent(context, ClubPaintActivity.class);
        intent.putExtra("pushJob", pushJob);
        context.startActivity(intent);
    }

    //club 权限任命
    public static void toClubPower(Context context, String id) {
        Intent intent = new Intent(context, SettingPowerActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    //club 添加俱乐部
    public static void addClub(Context context) {
        Intent intent = new Intent(context, AddCLubActivity.class);

        context.startActivity(intent);
    }

    //club 职位管理
    public static void toMangerPower(Context context, String club_id) {
        Intent intent = new Intent(context, MangerPowerActivity.class);
        intent.putExtra("club_id", club_id);
        context.startActivity(intent);
    }

    //club 社团个人中心
    public static void toPersonScenter(Context context, String other_id, String name, String phone) {
        Intent intent = new Intent(context, PersonCenterActivity.class);
        intent.putExtra("other_id", other_id);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    //club 社团-我的兼职
    public static void toMyPartTime(Context context ,String other_id) {
        Intent intent = new Intent(context, ClubMyPartTimeActivity.class);
        intent.putExtra("other_id", other_id);
        context.startActivity(intent);
    }

    //club 社团-我的兼职
    public static void toMyClub(Context context ,String other_id) {
        Intent intent = new Intent(context, ClubMyClubActivity.class);
        intent.putExtra("other_id", other_id);
        context.startActivity(intent);
    }

    //club 社团-我的兼职活动
    public static void toMyClubActivity(Context context,String other_id) {
        Intent intent = new Intent(context, ClubMyActiveActivity.class);
        intent.putExtra("other_id", other_id);
        context.startActivity(intent);
    }

    //club 社团-我的兼职活动
    public static void toMyHelpActivity(Context context, String other_id) {
        Intent intent = new Intent(context, ClubHelpActivity.class);
        intent.putExtra("other_id", other_id);
        context.startActivity(intent);
    }

    //club 社团-申请赞助
    public static void toApplyHelp(Context context) {
        Intent intent = new Intent(context, ClubApplyHelpActivity.class);
        context.startActivity(intent);
    }

    //club 社团-申请赞助详情
    public static void toApplyHelpShow(Context context, String support_id) {
        Intent intent = new Intent(context, ClubApplyHelpShowActivity.class);
        intent.putExtra("support_id", support_id);
        context.startActivity(intent);
    }

    //club 社团-申请赞助
    public static void toSystemMeaage(Context context) {
        Intent intent = new Intent(context, ClubSystemListActivity.class);
        context.startActivity(intent);
    }

    //club  保险申明链接
    public static void toSystemMeaagePotion(Context context, String url) {
        Intent intent = new Intent(context, PortionActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    //朋友圈
    public static void toFriend(Context context) {
        Intent intent = new Intent(context, FunCampusForActivity.class);
        context.startActivity(intent);
    }

    //朋友圈
    public static void toSendFunDispark(Context context) {
        Intent intent = new Intent(context, ClubDisparkActivity.class);
        context.startActivity(intent);
    }

}
