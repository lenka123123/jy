package sinia.com.baihangeducation.release.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.release.info.ReleaseTrainingListInfo;
import sinia.com.baihangeducation.release.view.IGetReleaseTrainingInfoView;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class ReleaseTrainingPresenter extends BasePresenter {
    private IGetReleaseTrainingInfoView view;
    private Activity activity;

    public ReleaseTrainingPresenter(Activity activity, IGetReleaseTrainingInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void getReleaseTrainingInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getTrainOptionList", "publish", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("培训列表数据", bean.toString());
                ReleaseTrainingListInfo info = bean.parseObject(ReleaseTrainingListInfo.class);
                view.getReleaseTrainingInfoSuccess(info);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }

    public void releaseTrainingInfo() {
        if (!AccountManger.checkReleaseTraining(activity, view.getReleaseTrainingTitle(), view.getReleaseTrainingSubTitle(), view.getReleaseTrainingProvinceId(),
                view.getReleaseTrainingAdress(), view.getReleaseTrainingIndustryId(), view.getReleaseTrainingLevelId(), view.getReleaseTrainingCycleId(),
                view.getReleaseTrainingClassNum(), view.getReleaseTrainingClassDuration(), view.getReleaseTrainingPrice(), view.getReleaseTrainingClassDate(),
                view.getReleaseTrainingIsCoupon(), view.getReleaseTrainingCover(), view.getReleaseTrainingTel(), view.getReleaseTrainingClassIntro())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addTrain", "publish", true);
        info.put("industry_id", view.getReleaseTrainingIndustryId());
        info.put("level_id", view.getReleaseTrainingLevelId());
        info.put("cycle_id", view.getReleaseTrainingCycleId());
        info.put("title", view.getReleaseTrainingTitle());
        info.put("sub_title", view.getReleaseTrainingSubTitle());
        info.put("price", view.getReleaseTrainingPrice());
        info.put("province_id", view.getReleaseTrainingProvinceId());
        info.put("city_id", view.getReleaseTrainingCityId());
        info.put("area_id", view.getReleaseTrainingAreaId());
        info.put("address", view.getReleaseTrainingAdress());
        info.put("tel", view.getReleaseTrainingTel());
        info.put("class_num", view.getReleaseTrainingClassNum());
        info.put("class_duration", view.getReleaseTrainingClassDuration());
        info.put("class_date", view.getReleaseTrainingClassDate());
        info.put("class_intro", view.getReleaseTrainingClassIntro());
        info.put("is_coupon", view.getReleaseTrainingIsCoupon());

        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getReleaseTrainingCover())) {
            request.add("cover", new FileBinary(new File(view.getReleaseTrainingCover())));
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("发布培训", bean.toString() + "图片");
                    view.releaseSuccess();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showErrorToast(activity, error);
                }

                @Override
                public void requestFinish() {
                    view.hideLoading();
                }
            });
        }
    }

    public void editTrainingInfo() {
        if (!AccountManger.checkReleaseTraining(activity, view.getReleaseTrainingTitle(), view.getReleaseTrainingSubTitle(), view.getReleaseTrainingProvinceId(),
                view.getReleaseTrainingAdress(), view.getReleaseTrainingIndustryId(), view.getReleaseTrainingLevelId(), view.getReleaseTrainingCycleId(),
                view.getReleaseTrainingClassNum(), view.getReleaseTrainingClassDuration(), view.getReleaseTrainingPrice(), view.getReleaseTrainingClassDate(),
                view.getReleaseTrainingIsCoupon(), view.getReleaseTrainingCover(), view.getReleaseTrainingTel(), view.getReleaseTrainingClassIntro())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "addTrain", "publish", true);
        info.put("industry_id", view.getReleaseTrainingIndustryId());
        info.put("level_id", view.getReleaseTrainingLevelId());
        info.put("cycle_id", view.getReleaseTrainingCycleId());
        info.put("title", view.getReleaseTrainingTitle());
        info.put("sub_title", view.getReleaseTrainingSubTitle());
        info.put("price", view.getReleaseTrainingPrice());
        info.put("province_id", view.getReleaseTrainingProvinceId());
        info.put("city_id", view.getReleaseTrainingCityId());
        info.put("area_id", view.getReleaseTrainingAreaId());
        info.put("address", view.getReleaseTrainingAdress());
        info.put("tel", view.getReleaseTrainingTel());
        info.put("class_num", view.getReleaseTrainingClassNum());
        info.put("class_duration", view.getReleaseTrainingClassDuration());
        info.put("class_date", view.getReleaseTrainingClassDate());
        info.put("class_intro", view.getReleaseTrainingClassIntro());
        info.put("is_coupon", view.getReleaseTrainingIsCoupon());
        info.put("id", view.getReleaseTrainingTrainId());

        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getReleaseTrainingCover())) {
            request.add("cover", new FileBinary(new File(view.getReleaseTrainingCover())));
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("编辑培训", bean.toString() + "图片");
                    view.releaseSuccess();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showErrorToast(activity, error);
                }

                @Override
                public void requestFinish() {
                    view.hideLoading();
                }
            });
        } else {
            post(info, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    Log.i("编辑培训", bean.toString() + "图片");
                    view.releaseSuccess();
                }

                @Override
                public void requestFailed(String error) {
                    Toast.getInstance().showErrorToast(activity, error);
                }

                @Override
                public void requestFinish() {
                    view.hideLoading();
                }
            });
        }
    }
}
