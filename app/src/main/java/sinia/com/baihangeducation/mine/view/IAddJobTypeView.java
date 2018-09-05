package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.AddJobTimeInfoList;
import com.mcxtzhang.swipemenulib.info.bean.AddJobTypeInfoList;

/**
 * Created by Administrator on 2018/3/26.
 */

public interface IAddJobTypeView extends BaseView {

    void getJobTypeSuccess(List<AddJobTypeInfoList> typelist);

    void getJobTimeSuccess(List<AddJobTimeInfoList> timelist);

    void getJobTypeFail();
}
