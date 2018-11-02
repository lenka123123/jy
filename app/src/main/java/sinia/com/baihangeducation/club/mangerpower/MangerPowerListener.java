package sinia.com.baihangeducation.club.mangerpower;


import java.util.List;

import sinia.com.baihangeducation.club.addclub.AddClubList;

public interface MangerPowerListener {
    void setRequestSuccess(   List<MangerPowerList> msg);
    void setRequestFail();

}
