package sinia.com.baihangeducation.supplement.tool;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wanjingyu on 2016/6/6.
 */
public class LocalProvinceBean implements Serializable {
    public String province_id;
    public String province_name;
    public ArrayList<LocalCityBean> city_list;
    public boolean isSelected;


    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return province_name;
    }
}
