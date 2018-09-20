package sinia.com.baihangeducation.club.applyclublist.interfaces;

import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;

public interface ApplyClubListListener {
    void setSchookList(ApplyClubListBean list, int maxpage);

    void setSchookListFail(String meg);

}
