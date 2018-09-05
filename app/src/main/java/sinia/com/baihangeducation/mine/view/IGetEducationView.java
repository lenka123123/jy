package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.College;
import com.mcxtzhang.swipemenulib.info.bean.EducationInfo;
import com.mcxtzhang.swipemenulib.info.bean.Marjor;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface IGetEducationView extends BaseView {

    String getPage();

    String getItenmNum();

    /**
     * 获取父级ID
     * 获取专业第二层
     *
     * @return
     */
    String getFatherId();

    /**
     * 获取学校信息成功
     *
     * @param list
     */
    void getEducationSchoolSuccess(List<College> list,int maxpage);

    /**
     * 获取专业一级列表成功
     *
     * @param list
     */
    void getEducationMajor_1_Success(List<Marjor> list, int maxpage);
    /**
     * 获取专业二级列表成功
     *
     * @param list
     */
    void getEducationMajor_2_Success(List<Marjor> list,int maxpage);

    /**
     * 获取学历
     *
     */
    void getEducationSuccess( List<EducationInfo> educationInfos);

//    void getChoiceSuccess(List<College> list, int maxpage);
}
