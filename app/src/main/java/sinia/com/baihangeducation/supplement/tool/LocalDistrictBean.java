package sinia.com.baihangeducation.supplement.tool;

import java.io.Serializable;

/**
 * Created by wanjingyu on 2016/6/6.
 */


public class LocalDistrictBean implements Serializable {
    public String district_id;
    public String district_name;
    public boolean isSelected;

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return district_name;
    }
}