package sinia.com.baihangeducation.home.present;

import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;

import java.util.List;

public interface GetIndustryListener  {

    void getDataSuccess(List<IndustryListInfoHome> listInfos);
    void getDataFaile();
}
