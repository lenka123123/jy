package sinia.com.baihangeducation.club.clubschoollist.interfaces;

import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;

public interface setSchoolListListener {
    void setSchookList(ClubSchoolList list, int maxpage);

    void setSchookListFail(String meg);

}
