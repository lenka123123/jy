package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.GetKeyWorldInfo;

/**
 * Created by Administrator on 2018/4/17.
 */

public interface GetKeyWorldView extends BaseView{
    /**
     * 获取关键词成功
     */
    void getKeyWorldSuccess( List<GetKeyWorldInfo> mGetKeyWorldInfo);
}
