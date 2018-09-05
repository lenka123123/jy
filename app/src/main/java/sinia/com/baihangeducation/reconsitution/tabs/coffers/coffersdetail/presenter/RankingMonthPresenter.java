package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.presenter;

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
public class RankingMonthPresenter implements RankingContract.Presenter {

    private RankingModel rankingModel;
    private BringCoffersFragment dayRankingFragment;


    public RankingMonthPresenter(RankingModel rankingModel, BringCoffersFragment dayRankingFragment) {
        this.rankingModel = rankingModel;
        this.dayRankingFragment = dayRankingFragment;
    }

    @Override
    public void getDateRecentMemberManageList(int type, int page) {
        rankingModel.getCompanyUCenterInfo(type, page, new ObtainRankingDataListener() {


            @Override
            public void onSuccess(CoffersDetail successMessage, int myIndex) {
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
