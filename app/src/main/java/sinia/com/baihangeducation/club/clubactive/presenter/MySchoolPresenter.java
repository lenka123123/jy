package sinia.com.baihangeducation.club.clubactive.presenter;

import sinia.com.baihangeducation.club.clubactive.MySchoolFragment;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.clubactive.model.ActiveModel;
import sinia.com.baihangeducation.club.clubactive.model.ObtainActiveDataListener;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.BringCoffersFragment;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.ObtainRankingDataListener;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.RankingContract;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.RankingModel;

/**
 * 创建日期：2018/5/26 on 16:19
 * 描述:
 * 作者:yuxd Administrator
 */
public class MySchoolPresenter implements RankingContract.Presenter {

    private ActiveModel rankingModel;
    private MySchoolFragment dayRankingFragment;


    public MySchoolPresenter(ActiveModel rankingModel, MySchoolFragment dayRankingFragment) {
        this.rankingModel = rankingModel;
        this.dayRankingFragment = dayRankingFragment;
    }

    @Override
    public void getDateRecentMemberManageList(int type, int page) {
        rankingModel.getCompanyUCenterInfo(type, page, new ObtainActiveDataListener() {


            @Override
            public void onSuccess(ActiveListData successMessage, int myIndex) {
                dayRankingFragment.showRankingList(successMessage);
            }

            @Override
            public void onError(String errorMessage) {
                dayRankingFragment.showError(errorMessage);
            }

            @Override
            public void onUpdate(int total, int current) {
                dayRankingFragment.upDateRanking(total, current);
            }
        });

    }
}
