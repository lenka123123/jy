package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.bean.MyResumeEducation_expInfo;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/9.
 */

public class SubmitMyResumePresent extends BasePresenter {
    private MyApplication application;
    private MyResumInfo myResumInfo;
    private Activity activity;

    public SubmitMyResumePresent(Activity activity) {
        super(activity);
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
        myResumInfo = application.getUserResumeInfoCopy();
    }

    public void submutresume() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setMyResumeInfo", "ucenter", true);
        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);

        info.put("name", "022");
        info.put("gender", "男");
        Log.i("人员类型名称", "0");
        info.put("graduated", "0");
        info.put("birthday", "0");
        info.put("province_id", "0");
        info.put("city_id", "0");
        info.put("area_id", "0");
        info.put("address", "0");
        info.put("tel", "13902276741");

        MyResumeEducation_expInfo education_expInfo = new MyResumeEducation_expInfo();
        education_expInfo.education_id = "110";
        education_expInfo.education_name = "110";      //学历名称
        education_expInfo.major_id = "110";            //专业ID
        education_expInfo.major_name = "110";          //专业名称
        education_expInfo.school_id = "110";          //学校ID
        education_expInfo.school_name = "负担大学";       //学校名称

        JSONObject object1 = JSONObject.parseObject(JSON.toJSONString(education_expInfo));
        info.put("education_exp", object1);
//        JSONObject object2 = JSONObject.parseObject(JSON.toJSONString(myResumInfo.job_exp));
        JSONArray object2 = JSONArray.parseArray(JSON.toJSONString(MyResumInfo.job_exp));
        info.put("job_exp", object2);
        info.put("evaluation", myResumInfo.evaluation);
        info.put("student_photo", myResumInfo.student_photo);
        info.put("health_photo", myResumInfo.health_photo);
        info.put("student_no", myResumInfo.student_no);
        info.put("health_no", myResumInfo.health_no);
        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(myResumInfo.student_photo) && !TextUtils.isEmpty(myResumInfo.health_photo)) {
            if (myResumInfo.student_photo.substring(0, 4).equals("http")) {
                request.add("student_photo", "");
                request.add("health_photo", "");
            } else {
                request.add("student_photo", new FileBinary(new File(myResumInfo.student_photo)));
                request.add("health_photo", new FileBinary(new File(myResumInfo.health_photo)));
            }

            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("修改资料", bean.toString());
                    Toast.getInstance().showSuccessToast(activity, "您已成功提交认证资料，请耐心等候");
                    activity.finish();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showSuccessToast(activity, error);
                }

                @Override
                public void requestFinish() {
                }
            });
        } else {
            post(info, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("修改资料", bean.toString());
                    Toast.getInstance().showSuccessToast(activity, "您已成功提交认证资料，请耐心等候");
                    activity.finish();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showSuccessToast(activity, error);
                }

                @Override
                public void requestFinish() {
                }
            });
        }
    }


    public void submutresume(int add) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setMyResumeInfo", "ucenter", true);

        info.put("token", AppConfig.TOKEN);
        info.put("user_id", AppConfig.USERID);

        info.put("name", MyResumInfo.name);
        info.put("gender", MyResumInfo.gender);
        info.put("graduated", MyResumInfo.graduated);
        info.put("birthday", MyResumInfo.birthday);
        info.put("province_id", MyResumInfo.province_id);
        info.put("city_id", MyResumInfo.city_id);
        info.put("area_id", MyResumInfo.area_id);
        info.put("address", MyResumInfo.address);
        info.put("tel", MyResumInfo.tel);
        info.put("evaluation", MyResumInfo.evaluation);


        MyResumeEducation_expInfo education_expInfo = new MyResumeEducation_expInfo();

        education_expInfo.education_id = AppConfig.educationtextID;
        education_expInfo.education_name = AppConfig.educationtext;      //学历名称
        education_expInfo.major_id = AppConfig.SCHOOLMAGORID;            //专业ID
        education_expInfo.major_name = AppConfig.SCHOOLMAGOR;         //专业名称
        education_expInfo.school_id = AppConfig.SCHOOLNAMEID;         //学校ID
        education_expInfo.school_name = AppConfig.SCHOOLNAME;       //学校名称

//        education_expInfo.education_id = "110";
//        education_expInfo.education_name = "110";      //学历名称
//        education_expInfo.major_id = "110";            //专业ID
//        education_expInfo.major_name = "110";          //专业名称
//        education_expInfo.school_id = "110";          //学校ID
//        education_expInfo.school_name = "负担大学";       //学校名称

        JSONObject object1 = JSONObject.parseObject(JSON.toJSONString(education_expInfo));
        info.put("education_exp", object1);

        if (MyResumInfo.job_exp != null && MyResumInfo.job_exp.size() > 0) {
            JSONArray object2 = JSONArray.parseArray(JSON.toJSONString(MyResumInfo.job_exp));
            info.put("job_exp", object2);
        }


        Request<String> request = postFile(info);


        if (!MyResumInfo.student_photo.equals("") && !MyResumInfo.student_photo.startsWith("http")) {
            request.add("student_photo", new FileBinary(new File(MyResumInfo.student_photo)));
        }
        if (!MyResumInfo.health_photo.equals("") && !MyResumInfo.health_photo.startsWith("http")) {
            request.add("health_photo", new FileBinary(new File(MyResumInfo.health_photo)));
        }

        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("修改资料", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "您已成功提交认证资料，请耐心等候");
                activity.finish();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showSuccessToast(activity, error);
            }

            @Override
            public void requestFinish() {
            }
        });


//        Log.i("修改资料", "aaaaaaa");
//        post(info, new OnRequestListener() {
//            @Override
//            public void requestSuccess(BaseResponseBean bean) {
//                Log.i("修改资料", "aaaaaaa");
//                Toast.getInstance().showSuccessToast(activity, "您已成功提交认证资料，请耐心等候");
//
//                activity.finish();
//            }
//
//            @Override
//            public void requestFailed(String error) {
//                Toast.getInstance().showSuccessToast(activity, error);
//                Log.i("修改资料", "aaaaaaa");
//            }
//
//            @Override
//            public void requestFinish() {
//            }
//        });
    }

}
