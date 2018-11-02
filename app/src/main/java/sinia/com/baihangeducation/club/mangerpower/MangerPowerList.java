package sinia.com.baihangeducation.club.mangerpower;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MangerPowerList {

    public String name;

    public List<PowerList> list;

    public class PowerList {
        public String permission_id;
        public String permission_name;
        public String checked;  // 是否选中 0：未选中 1：选中
    }

}
