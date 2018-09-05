package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;

/**
 * Created by Administrator on 2018/4/20.
 */

public interface AddCollctionView extends BaseView {

    /**
     * 收藏类型（1：全职 2：兼职；3：趣事；4:攻略 5试卷 6秘籍 7资讯（每日分享） 8培训）
     *
     * @return
     */
    String getAddCollctionType();

    /**
     * 对应类型ID编号
     *
     * @return
     */
    String getAddClloectionTpyeId();

    /**
     * 删除操作，ID
     *
     * @return
     */
    String getAddClloectionId();

    /**
     * 添加收藏成功
     */
    void addCollectionSuccess(AddCollectionSuccessInfo mAddCollectionSuccessInfo);

    /**
     * 删除收藏成功
     */
    void detelCollectionSuccess();
}
