package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces;


import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;

/**
 * 创建日期：2018/5/22 on 11:23
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingContract {

    public interface View {
        void showRankingList(CoffersDetail successMessage);

        void upDateRankingList(CoffersDetail successMessage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getDateRecentMemberManageList(int type, int page);
    }
}
