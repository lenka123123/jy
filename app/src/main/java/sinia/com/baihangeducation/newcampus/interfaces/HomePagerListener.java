package sinia.com.baihangeducation.newcampus.interfaces;

import sinia.com.baihangeducation.newcampus.info.HomePager;
import sinia.com.baihangeducation.newcampus.info.HomePagerDetail;

public interface HomePagerListener {
    void setHomePagerSuccessListener(HomePagerDetail homePager);

    void homePagerFailListener();

}
