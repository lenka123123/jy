package sinia.com.baihangeducation.club.applyclublist.interfaces;

import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;

public interface GetPersonListener {

    void showGetPersonSuccess(GetPersonList getPersonList, int max);

    void showGetPersonFail(String msg);

}
