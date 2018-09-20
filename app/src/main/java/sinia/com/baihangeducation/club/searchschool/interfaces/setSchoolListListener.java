package sinia.com.baihangeducation.club.searchschool.interfaces;

import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;

public interface setSchoolListListener {
    void setSchookList(ClubSchoolList list, int maxpage);

    void setSchookListFail(String meg);

}
