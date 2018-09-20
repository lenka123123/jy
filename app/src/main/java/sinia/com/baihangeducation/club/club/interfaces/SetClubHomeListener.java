package sinia.com.baihangeducation.club.club.interfaces;

import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;

public interface SetClubHomeListener {
    void setClubHomeSuccess(ClubHomeInfo clubSchoolList);
    void setClubHomeFail(String msg);

}
