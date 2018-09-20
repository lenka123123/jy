package sinia.com.baihangeducation.club.clubdetail.interfaces;

import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;

public interface ClubDetailListener {
    void setSchookList(ClubDetailBean list);

    void setSchookListFail(String meg);

}
