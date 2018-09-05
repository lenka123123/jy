package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.AddListInfo;

public interface IGetAddView extends BaseView {

    /**
     * 广告位位置    ( 首页：app_home_index_top 互助：app_help_index_top 学堂：app_find_index_top 发现：app_find_index_top_ex )
     * @return
     */
    String getAddPosition();

    void getAddSuccess(AddListInfo addListInfo);
}
