package sinia.com.baihangeducation.mine.model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by LackAi on 2017/4/11.
 */

public class AccountManger {


    /**
     * 获取用户信息
     *
     * @param context
     */
    public static void getUserInfo(Context context) {
        if (SPUtils.getInstance().contains(context, Constants.USER_INFO)) {
            //从缓存中获取用户信息
            UserInfo userInfo = (UserInfo) SPUtils.getInstance().readObject(context, Constants.USER_INFO);
            //如果缓存中的用户信息为空则return
            if (userInfo == null) {
                return;
            }
            //将用户信息存入MyApplication
            MyApplication application = (MyApplication) context.getApplicationContext();
            application.setUserInfo(userInfo);
        }
    }

    public static void getCommonInfo(Context context) {

    }

    /**
     * 获取用户简历信息
     *
     * @param context
     */
    public static void getUserResumeInfo(Context context) {
        if (SPUtils.getInstance().contains(context, Constants.USER_RESUMEINFO)) {
            //从缓存中获取用户信息
            MyResumInfo userResumeInfo = (MyResumInfo) SPUtils.getInstance().readObject(context, Constants.USER_RESUMEINFO);
            //如果缓存中的用户信息为空则return
            if (userResumeInfo == null) {
                Log.i("数据是空的", "空的");
                return;
            }
            //将用户信息存入MyApplication
            MyApplication application = (MyApplication) context.getApplicationContext();
            application.setUserResumeInfo(userResumeInfo);
            Log.i("数据是空的", "成功");
        }
    }

    /**
     * 获取用户简历信息
     *
     * @param context
     */
    public static void getUserResumeInfoCopy(Context context) {
        if (SPUtils.getInstance().contains(context, Constants.USER_RESUMEINFOCOPY)) {
            //从缓存中获取用户信息
            MyResumInfo userResumeInfo = (MyResumInfo) SPUtils.getInstance().readObject(context, Constants.USER_RESUMEINFOCOPY);
            //如果缓存中的用户信息为空则return
            if (userResumeInfo == null) {
                return;
            }
            //将用户信息存入MyApplication
            MyApplication application = (MyApplication) context.getApplicationContext();
            application.setUserResumeInfoCopy(userResumeInfo);
        }
    }


    /**
     * 更新用户信息
     *
     * @param context
     * @param editUserInfo
     */
//    public static void updateUserInfo(Context context, UserInfo editUserInfo) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        UserInfo userInfo = application.getUserInfo();
//        userInfo.nickname = editUserInfo.nickname;
//        userInfo.avatar = editUserInfo.avatar;
//        userInfo.province_id = editUserInfo.province_id;
//        userInfo.city_id = editUserInfo.city_id;
//        userInfo.area_id = editUserInfo.area_id;
//        userInfo.area = editUserInfo.area;
//        userInfo.birthday = editUserInfo.birthday;
//        userInfo.contact = editUserInfo.contact;
//        userInfo.beans_num = editUserInfo.beans_num;
//        userInfo.gender = editUserInfo.gender;
//        userInfo.auth_status = editUserInfo.auth_status;
//
//        SPUtils.getInstance().saveObject(context, Constants.USER_INFO, userInfo);
//    }

    /**
     * 清空用户信息
     *
     * @param context
     */
    public static void clearUserInfo(Context context) {
        SPUtils.getInstance().remove(context, Constants.USER_INFO);
        SPUtils.getInstance().remove(context, Constants.USER_ACCOUNT);
        MyApplication application = (MyApplication) context.getApplicationContext();
//        application.setUserInfo(null);
        EventBus.getDefault().post("logout_success");
    }


    /**
     * 检查忘记密码信息
     *
     * @param context
     * @param phone
     * @param password
     * @param vcode
     * @return
     */
    public static boolean checkupForgetPassword(Context context, String phone, String password, String vcode) {
        if (phone == null || TextUtils.isEmpty(phone)) {
            Toast.getInstance().showToastView(context, "手机号不能为空");
            return false;
        }
        if (password == null || TextUtils.isEmpty(password)) {
            Toast.getInstance().showToastView(context, "密码不能为空");
            return false;
        }
        if (vcode == null || TextUtils.isEmpty(vcode)) {
            Toast.getInstance().showToastView(context, "验证码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 检查登录信息
     *
     * @param context
     * @param phone
     * @param pwd
     * @return
     */
    public static boolean checkupLogin(Context context, String phone, String pwd) {
        if (phone == null || TextUtils.isEmpty(phone)) {
            Toast.getInstance().showToastView(context, "手机号不能为空");
            return false;
        }
        if (pwd == null || TextUtils.isEmpty(pwd)) {
            Toast.getInstance().showToastView(context, "密码不能为空");
        }
        if (pwd.length() < 6) {
            Toast.getInstance().showToastView(context, "密码错误");
        }
        return true;
    }

    public static boolean checkupUpdateBussiness(Context context, String cityId, String name, String contact) {
        if (cityId == null || TextUtils.isEmpty(cityId)) {
            Toast.getInstance().showToastView(context, "请选择所在地");
            return false;
        }
        if (name == null || TextUtils.isEmpty(name)) {
            Toast.getInstance().showToastView(context, "请输入昵称");
            return false;
        }
        if (contact == null || TextUtils.isEmpty(contact)) {
            Toast.getInstance().showToastView(context, "请填写联系人");
            return false;
        }
        return true;
    }

    public static boolean checkupUpdateProgress(Context context, String district_id, String nickname, String gender, String birthday) {
        if (district_id == null || TextUtils.isEmpty(district_id)) {
            Toast.getInstance().showToastView(context, "请选择所在地址");
            return false;
        }
        if (nickname == null || TextUtils.isEmpty(nickname)) {
            Toast.getInstance().showToastView(context, "请输入昵称");
            return false;
        }
        if (gender == null || TextUtils.isEmpty(gender)) {
            Toast.getInstance().showToastView(context, "请选择性别");
            return false;
        }
        if (birthday == null || TextUtils.isEmpty(birthday)) {
            Toast.getInstance().showToastView(context, "请选择生日");
            return false;
        }
        return true;
    }

    /**
     * 修改密码
     *
     * @param context
     * @param pwd
     * @param pedAgain
     * @param code
     * @return
     */
    public static boolean checkupUpadtePwd(Context context, String pwd, String pedAgain, String code) {
        if (pwd.length() < 6) {
            Toast.getInstance().showToastView(context, "请输入6-12位密码");
            return false;
        }
        if (code == null || TextUtils.isEmpty(code)) {
            Toast.getInstance().showToastView(context, "验证码不能为空");
            return false;
        }
        if (!pwd.equals(pedAgain)) {
            Toast.getInstance().showToastView(context, "两次密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 注册
     *
     * @param c
     * @param phone
     * @param code
     * @param pwd
     * @param pwdAgain
     * @return
     */
    public static boolean checkupRegisterInfo(Context c, String phone, String code, String pwd, String pwdAgain, boolean isread) {
        if (phone == null || TextUtils.isEmpty(phone)) {
            Toast.getInstance().showToastView(c, "请输入手机号");
            return false;
        } else if (code == null || TextUtils.isEmpty(code)) {
            Toast.getInstance().showToastView(c, "请输入验证码");
            return false;
        } else if (pwd == null || TextUtils.isEmpty(pwd)) {
            Toast.getInstance().showToastView(c, "请输入密码");
            return false;
        } else if (pwd.length() < 6 || pwdAgain.length() < 6) {
            Toast.getInstance().showToastView(c, "请输入6-12位密码");
            return false;
        } else if (!pwd.equals(pwdAgain)) {
            Toast.getInstance().showToastView(c, "两次密码不一致");
            return false;
        } else if (!isread) {
            Toast.getInstance().showToastView(c, "请仔细阅读就业邦用户协议");
            return false;
        }
        return true;
    }

    /**
     * 第三方绑定
     *
     * @return
     */
    public static boolean checkThridBind(Activity context, String phone, String code) {
        if (TextUtils.isEmpty(code)) {
            Toast.getInstance().showToastView(context, "请输入验证码");
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            Toast.getInstance().showToastView(context, "请输入手机号码");
            return false;
        }
        return true;
    }

    /**
     * 第三方绑定
     *
     * @return
     */
    public static boolean checkFindPwd_2(Activity context, String pwd, String pwd2) {
        if (TextUtils.isEmpty(pwd)) {
            Toast.getInstance().showToastView(context, "请输入密码");
            return false;
        } else if (TextUtils.isEmpty(pwd2)) {
            Toast.getInstance().showToastView(context, "请输入密码第二次密码");
            return false;
        } else if (!pwd.equals(pwd2)) {
            Toast.getInstance().showToastView(context, "两次密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 实名认证
     *
     * @return
     */
    public static boolean checkRealName(Activity context, String name, String idcardnum, String idimgon, String idimgoff, String idimgonhand) {
        if (TextUtils.isEmpty(name)) {
            Toast.getInstance().showToastView(context, "请输入您的真实姓名");
            return false;
        } else if (TextUtils.isEmpty(idcardnum)) {
            Toast.getInstance().showToastView(context, "请输入您的身份证号码");
            return false;
        } else if (TextUtils.isEmpty(idimgon)) {
            Toast.getInstance().showToastView(context, "请上传身份证正面照");
            return false;
        } else if (TextUtils.isEmpty(idimgoff)) {
            Toast.getInstance().showToastView(context, "请上传身份证反面照");
            return false;
        } else if (TextUtils.isEmpty(idimgonhand)) {
            Toast.getInstance().showToastView(context, "请上传手持身份证照片");
            return false;
        }
//        else if (!isIDNumber(idcardnum)) {
//            Toast.getInstance().showToastView(context, "请输入正确的身份证号码");
//            return false;
//        }
        return true;
    }

    /**
     * 发布互助验证
     *
     * @return
     */
    public static boolean checkReleaHelpEachOther(Activity context, String type, String needPeople, String isPay, String gender, String price, String title, String content) {
        if (TextUtils.isEmpty(type)) {
            Toast.getInstance().showToastView(context, "请选择发布互助的类型");
            return false;
        } else if (TextUtils.isEmpty(needPeople)) {
            Toast.getInstance().showToastView(context, "请输入求助人数");
            return false;
        } else if (TextUtils.isEmpty(isPay)) {
            Toast.getInstance().showToastView(context, "请选择付费要求");
            return false;
        } else if (TextUtils.isEmpty(gender)) {
            Toast.getInstance().showToastView(context, "请选择性别要求");
            return false;
        } else if (TextUtils.isEmpty(title)) {
            Toast.getInstance().showToastView(context, "请输入标题");
            return false;
        } else if (TextUtils.isEmpty(content)) {
            Toast.getInstance().showToastView(context, "请输入内容");
            return false;
        }
        if (isPay.equals("付费")) {
            if (TextUtils.isEmpty(price)) {
                Toast.getInstance().showToastView(context, "请输入金额");
                return false;
            }
        }
        return true;
    }

    /**
     * 发布趣事验证
     *
     * @return
     */
    public static boolean checkReleaInteresting(Activity context, String img, String title, String contant) {
        if (TextUtils.isEmpty(img)) {
            Toast.getInstance().showToastView(context, "请上传图片");
            return false;
        } else if (TextUtils.isEmpty(title)) {
            Toast.getInstance().showToastView(context, "请填写标题");
            return false;
        } else if (TextUtils.isEmpty(contant)) {
            Toast.getInstance().showToastView(context, "请输入内容");
            return false;
        }
        return true;
    }

    /**
     * 检查我的简历编辑基本资料信息
     *
     * @return
     */
    public static boolean checkEditMyResumeBaseInfo(Activity context, String name, String tel) {
        if (TextUtils.isEmpty(name)) {
            Toast.getInstance().showToastView(context, "请输入你的真实姓名");
            return false;
        } else if (TextUtils.isEmpty(tel) && !isTel(tel)) {
            Toast.getInstance().showToastView(context, "请输入正确的电话号码");
            return false;
        }
        return true;
    }

    /**
     * 检查我的简历上传学生证
     *
     * @return
     */
    public static boolean checkMyResumeUpStudentCard(Activity context, String studentCardNum, String studentCardImg) {
        if (TextUtils.isEmpty(studentCardNum)) {
            Toast.getInstance().showToastView(context, "请输入你的学生证号码");
            return false;
        } else if (TextUtils.isEmpty(studentCardImg)) {
            Toast.getInstance().showToastView(context, "请选择你的学生证照片");
            return false;
        }
        return true;
    }

    /**
     * 检查我的简历上传学生证
     *
     * @return
     */
    public static boolean checkMyResumeUpHealthCard(Activity context, String healthCardNum, String healthCardImg) {
        if (TextUtils.isEmpty(healthCardNum)) {
            Toast.getInstance().showToastView(context, "请输入你的健康证号码");
            return false;
        } else if (TextUtils.isEmpty(healthCardImg)) {
            Toast.getInstance().showToastView(context, "请选择你的健康证照片");
            return false;
        }
        return true;
    }

    /**
     * 检查我的简历添加工作简历
     *
     * @return
     */
    public static boolean checkCompanyInfo(Activity context, String adressid, String adressDetail, String companyTel, String name, String tel) {
        if (TextUtils.isEmpty(adressid)) {
            Toast.getInstance().showToastView(context, "请选择城市");
            return false;
        } else if (TextUtils.isEmpty(adressDetail)) {
            Toast.getInstance().showToastView(context, "请输入详细地址");
            return false;
        } else if (TextUtils.isEmpty(companyTel)) {
            Toast.getInstance().showToastView(context, "请填写公司电话");
            return false;
        } else if (TextUtils.isEmpty(name)) {
            Toast.getInstance().showToastView(context, "请填写联系人姓名");
            return false;
        } else if (TextUtils.isEmpty(tel)) {
            Toast.getInstance().showToastView(context, "请填写联系人电话");
            return false;
        }
        return true;
    }

    /**
     * 检查企业个人中心企业提交信息
     *
     * @return
     */
    public static boolean checkMyResumeAddJobExp(Activity context, String jobType, String job, String jobTime, String jobDetail) {
        if (TextUtils.isEmpty(jobType)) {
            Toast.getInstance().showToastView(context, "请选择你的岗位类型");
            return false;
        } else if (TextUtils.isEmpty(job)) {
            Toast.getInstance().showToastView(context, "请填写你的岗位");
            return false;
        } else if (TextUtils.isEmpty(jobTime)) {
            Toast.getInstance().showToastView(context, "请选择你的工作时间");
            return false;
        } else if (TextUtils.isEmpty(jobDetail)) {
            Toast.getInstance().showToastView(context, "请填写职位介绍");
            return false;
        }
        return true;
    }

    /**
     * 检查企业入住
     *
     * @return
     */
    public static boolean checkCompanyJoinIn(Activity context, String logo, String legalName, String licenseName, String adressDetail, String companyTel, String headName, String headTel, String licensePhoto, String isTraning) {
        if (TextUtils.isEmpty(logo)) {
            Toast.getInstance().showToastView(context, "请上传企业LOGO");
            return false;
        } else if (TextUtils.isEmpty(legalName)) {
            Toast.getInstance().showToastView(context, "请输入法人姓名");
            return false;
        } else if (TextUtils.isEmpty(licenseName)) {
            Toast.getInstance().showToastView(context, "请输入营业执照名称");
            return false;
        } else if (TextUtils.isEmpty(adressDetail)) {
            Toast.getInstance().showToastView(context, "请输入你的详细地址");
            return false;
        } else if (TextUtils.isEmpty(companyTel)) {
            Toast.getInstance().showToastView(context, "请输入企业电话");
            return false;
        } else if (TextUtils.isEmpty(headName)) {
            Toast.getInstance().showToastView(context, "请输入负责人姓名");
            return false;
        } else if (TextUtils.isEmpty(headTel)) {
            Toast.getInstance().showToastView(context, "请输入负责人电话");
            return false;
        } else if (TextUtils.isEmpty(licensePhoto)) {
            Toast.getInstance().showToastView(context, "请上企业营业执照传");
            return false;
        } else if (TextUtils.isEmpty(isTraning)) {
            Toast.getInstance().showToastView(context, "请选择你的机构类型");
            return false;
        }
        return true;
    }

    /**
     * 检查发布职位
     *
     * @return
     */
    public static boolean checkReleaseJobInfo(Activity context,
                                              String jobChoice,
                                              String compangName,
                                              String adress,
                                              String adressDetail,
                                              String needPeopleMax,
                                              String ageLower,
                                              String getGender,
                                              String getExp,
                                              String getSalaryType,
                                              String getMoneyUpper,
                                              String getJobTag,
                                              String getJobType,
                                              String getTimeEnd,
                                              String getIsContinue,
                                              String getIsInterview,
                                              String getLinkPerson,
                                              String getLinkType,
                                              String getReleaseContent,
                                              String getContant
    ) {
        if (TextUtils.isEmpty(jobChoice)) {
            Toast.getInstance().showToastView(context, "请选择职位类型");
            return false;
        } else if (TextUtils.isEmpty(compangName)) {
            Toast.getInstance().showToastView(context, "请输入招聘名称");
            return false;
        } else if (TextUtils.isEmpty(adress)) {
            Toast.getInstance().showToastView(context, "请选择工作地址");
            return false;
        } else if (TextUtils.isEmpty(adressDetail)) {
            Toast.getInstance().showToastView(context, "请输入你的详细地址");
            return false;
        } else if (TextUtils.isEmpty(needPeopleMax)) {
            Toast.getInstance().showToastView(context, "请输入招聘人数");
            return false;
        } else if (TextUtils.isEmpty(ageLower)) {
            Toast.getInstance().showToastView(context, "请输入招聘年龄限制");
            return false;
//        } else if (TextUtils.isEmpty(getGender)) {
//            Toast.getInstance().showToastView(context, "请选择性别限制");
//            return false;
        } else if (TextUtils.isEmpty(getExp)) {
            Toast.getInstance().showToastView(context, "请选择经验要求");
            return false;
        } else if (TextUtils.isEmpty(getSalaryType)) {
            Toast.getInstance().showToastView(context, "请选择薪资类型");
            return false;
        } else if (TextUtils.isEmpty(getMoneyUpper)) {
            Toast.getInstance().showToastView(context, "请输入薪资待遇");
            return false;
        } else if (TextUtils.isEmpty(getJobTag)) {
            Toast.getInstance().showToastView(context, "请选择岗位类型");
            return false;
        } else if (TextUtils.isEmpty(getJobType)) {
            Toast.getInstance().showToastView(context, "请选择所属行业");
            return false;
        } else if (TextUtils.isEmpty(getTimeEnd)) {
            Toast.getInstance().showToastView(context, "请选上班时间");
            return false;
        } else if (TextUtils.isEmpty(getIsContinue)) {
            Toast.getInstance().showToastView(context, "请选择是否连做");
            return false;
        } else if (TextUtils.isEmpty(getIsInterview)) {
            Toast.getInstance().showToastView(context, "请选择是否需要面试");
            return false;
        } else if (TextUtils.isEmpty(getLinkPerson)) {
            Toast.getInstance().showToastView(context, "请填写联系人");
            return false;
        } else if (TextUtils.isEmpty(getLinkType)) {
            Toast.getInstance().showToastView(context, "请填写联系方式");

            return false;
        } else if (TextUtils.isEmpty(getReleaseContent)) {
            Toast.getInstance().showToastView(context, "请填写联系人电话");
            return false;
        }
//        else if (TextUtils.isEmpty(getContant)) {
//            Toast.getInstance().showToastView(context, "请填写职位描述");
//            return false;
//        }
//        if (!jobChoice.equals("1")) {
//            if (TextUtils.isEmpty(moneyPart)) {
//                Toast.getInstance().showToastView(context, "请填写薪资待遇");
//                return false;
//            } else if (TextUtils.isEmpty(genderPart)) {
//                Toast.getInstance().showToastView(context, "请选择性别要求");
//                return false;
//            } else if (TextUtils.isEmpty(workDateMinPart)) {
//                Toast.getInstance().showToastView(context, "请选择起始上班日期");
//                return false;
//            } else if (TextUtils.isEmpty(workDateMaxPart)) {
//                Toast.getInstance().showToastView(context, "请选择结束上班日期");
//                return false;
//            }
//        } else {
//            if (TextUtils.isEmpty(moneyAllMin) || TextUtils.isEmpty(moneyAllMax)) {
//                Toast.getInstance().showToastView(context, "请填写薪资待遇范围");
//                return false;
//            }

        return true;
    }

    /**
     * 检查发布培训
     *
     * @return
     */
    public static boolean checkReleaseTraining(Activity context, String title, String coTitle, String cityId,
                                               String adressDetail, String belongIndutry, String belongLevel, String belongCycle,
                                               String classNum, String classLong, String classPrice, String classDate,
                                               String isCoupon, String cover, String tel, String contant) {
        if (TextUtils.isEmpty(title)) {
            Toast.getInstance().showToastView(context, "请填写标题");
            return false;
        } else if (TextUtils.isEmpty(coTitle)) {
            Toast.getInstance().showToastView(context, "请填写副标题");
            return false;
        } else if (TextUtils.isEmpty(cityId)) {
            Toast.getInstance().showToastView(context, "请选择你的城市");
            return false;
        } else if (TextUtils.isEmpty(adressDetail)) {
            Toast.getInstance().showToastView(context, "请输入你的详细地址");
            return false;
        } else if (TextUtils.isEmpty(belongIndutry)) {
            Toast.getInstance().showToastView(context, "请选择所属行业");
            return false;
        } else if (TextUtils.isEmpty(belongLevel)) {
            Toast.getInstance().showToastView(context, "请选择所属等级");
            return false;
        } else if (TextUtils.isEmpty(belongCycle)) {
            Toast.getInstance().showToastView(context, "请选择所属周期");
            return false;
        } else if (TextUtils.isEmpty(classNum)) {
            Toast.getInstance().showToastView(context, "请填写课时数");
            return false;
        } else if (TextUtils.isEmpty(classLong)) {
            Toast.getInstance().showToastView(context, "请填写课时长");
            return false;
        } else if (TextUtils.isEmpty(classPrice)) {
            Toast.getInstance().showToastView(context, "请输入课程价格");
            return false;
        } else if (TextUtils.isEmpty(classDate)) {
            Toast.getInstance().showToastView(context, "请选择上课时间");
            return false;
        }
//        else if (TextUtils.isEmpty(isCoupon)) {
//            Toast.getInstance().showToastView(context, "请选择是否可以使用优惠券");
//            return false;
//        }
//        else if (TextUtils.isEmpty(cover)) {
//            Toast.getInstance().showToastView(context, "请选择封面图");
//            return false;
//        }
        else if (TextUtils.isEmpty(tel)) {
            Toast.getInstance().showToastView(context, "请输入联系电话");
            return false;
        } else if (TextUtils.isEmpty(contant)) {
            Toast.getInstance().showToastView(context, "请输入课程介绍");
            return false;
        }
        return true;
    }

    /**
     * 检查我的简历添加工作简历
     *
     * @return
     */
    public static boolean checkMarkTraining(Activity context, String realname, String contact_tel) {
        if (TextUtils.isEmpty(realname)) {
            Toast.getInstance().showToastView(context, "请输入你的真实姓名");
            return false;
        } else if (TextUtils.isEmpty(contact_tel)) {
            Toast.getInstance().showToastView(context, "请输入你的手机号码");
            return false;
        }
        return true;
    }

    /**
     * email判断
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * email判断
     *
     * @param tel
     * @return
     */
    public static boolean isTel(String tel) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(tel);
        b = m.matches();
        return b;
    }

    /**
     * 身份证判断
     *
     * @param IDNumber
     * @return
     */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }
}
