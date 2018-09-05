package sinia.com.baihangeducation.mine.model;

import android.app.Activity;

import sinia.com.baihangeducation.MyApplication;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

/**
 * Created by Administrator on 2018/4/4.
 */

public class ContrastResumeInfo {
    public static MyResumInfo myResumInfo;
    public static MyResumInfo myResumInfoCopy;

    public ContrastResumeInfo(Activity activity) {
    }

    public ContrastResumeInfo(MyResumInfo info) {
        myResumInfo = info;
        myResumInfoCopy = info;
    }

    public void contras() {



//        if (myResumInfo == null) {
//            Log.i("", "contras: 111111");
//        }
//        if (myResumInfo.address == null) {
//            Log.i("", "contras: dfdfsd");
//        }
//        myResumInfo.address = myResumInfoCopy.address;                          //详细地址
//        myResumInfo.area_id = myResumInfoCopy.area_id;                          //区ID
//        myResumInfo.area_name = myResumInfoCopy.area_name;  //省市区名称
//        myResumInfo.birthday = myResumInfoCopy.birthday;    //生日
//        myResumInfo.city_id = myResumInfoCopy.city_id;//市ID
//        myResumInfo.education_exp = myResumInfoCopy.education_exp;//教育经历
//        myResumInfo.evaluation = myResumInfoCopy.evaluation;//自我评价
//        myResumInfo.gender = myResumInfoCopy.gender;//性别 1男2女3保密
//        myResumInfo.gender_name = myResumInfoCopy.gender_name;//性别名称
//        myResumInfo.graduated = myResumInfoCopy.graduated;//性别名称
//        myResumInfo.graduated_name = myResumInfoCopy.graduated_name;//人员类型名称
//        myResumInfo.health_photo = myResumInfoCopy.health_photo;//健康证照片
//        myResumInfo.intact = myResumInfoCopy.intact;//完成度
//        myResumInfo.job_exp = myResumInfoCopy.job_exp;//工作经历
//        myResumInfo.name = myResumInfoCopy.name;//姓名
//        myResumInfo.province_id = myResumInfoCopy.province_id;//省份ID
//        myResumInfo.resume_id = myResumInfoCopy.resume_id;//简历ID
//        myResumInfo.student_photo = myResumInfoCopy.student_photo;//学生照片
//        myResumInfo.tel = myResumInfoCopy.tel;//联系电话
//        myResumInfo.user_avatar = myResumInfoCopy.user_avatar;  //用户头像
//        myResumInfo.user_id = myResumInfoCopy.user_id;//用户编号
//        myResumInfo.user_nickname = myResumInfoCopy.user_nickname;//用户昵称
//        myResumInfo.health_no = myResumInfoCopy.health_no;//健康证号码
//        myResumInfo.student_no = myResumInfoCopy.student_no;//学生证号码
    }
}
