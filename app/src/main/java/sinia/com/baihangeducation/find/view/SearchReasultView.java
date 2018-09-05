package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.SearchResultFunnyInfo;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultInfomationInfo;
import com.mcxtzhang.swipemenulib.info.bean.SearchResultSSTInfo;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface SearchReasultView extends BaseView {

    /**
     * 搜索类型 1趣事 2攻略 3试卷 4秘籍 5资讯
     *
     * @return
     */
    String getType();

    /**
     * 获取页码
     *
     * @return
     */
    String getPage();

    /**
     * 获取每页数
     */
    String getPerpage();

    /**
     * 获取关键词
     *
     * @return
     */
    String getKeyWord();

    /**
     * 获取趣事数据成功
     */
    void getSearchFunnyData(List<SearchResultFunnyInfo> list, int maxpage);

    /**
     * 获取攻略数据成功
     */
    void getSearchStrategyData(List<SearchResultSSTInfo> list, int maxpage);

    /**
     * 获取试卷数据成功
     */
    void getSearchTestData(List<SearchResultSSTInfo> list, int maxpage);

    /**
     * 获取秘籍数据成功
     */
    void getSearchSecretData(List<SearchResultSSTInfo> list, int maxpage);

    /**
     * 获取资讯数据成功
     */
    void getSearchInformationData(List<SearchResultInfomationInfo> list,int maxpage);

}
