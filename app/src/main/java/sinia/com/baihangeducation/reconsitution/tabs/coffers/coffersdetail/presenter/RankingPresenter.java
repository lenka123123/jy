package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.presenter;


import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.AllCoffersFragment;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.ObtainRankingDataListener;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.RankingContract;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.RankingModel;

/**
 * 创建日期：2018/5/26 on 16:19
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingPresenter implements RankingContract.Presenter {

    private RankingModel rankingModel;
    private AllCoffersFragment allCoffersFragment;


    public RankingPresenter(RankingModel rankingModel, AllCoffersFragment allCoffersFragment) {
        this.rankingModel = rankingModel;
        this.allCoffersFragment = allCoffersFragment;
    }

    @Override
    public void getDateRecentMemberManageList(int type, int page) {
        rankingModel.getCompanyUCenterInfo(type, page, new ObtainRankingDataListener() {


            @Override
            public void onSuccess(CoffersDetail successMessage, int myIndex) {
                allCoffersFragment.showRankingList(successMessage);
            }

            @Override
            public void onError(String errorMessage) {
                allCoffersFragment.showError(errorMessage);
            }

            @Override
            public void onUpdate(int total, int current) {
                allCoffersFragment.upDateRanking(total, current);
            }
        });

    }
}
