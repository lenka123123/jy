package sinia.com.baihangeducation.newcampus.interfaces;


import sinia.com.baihangeducation.newcampus.info.HomeInfoShowBean;
import sinia.com.baihangeducation.newcampus.info.HomeInfoShowTobBean;

public interface HomeDeatilPagerListener {
    void setHomePagerSuccessListener(HomeInfoShowBean homePager);
    void setHomePagerTobSuccessListener(HomeInfoShowTobBean homePager);

    void homePagerFailListener();

}
