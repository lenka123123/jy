package sinia.com.baihangeducation.supplement.tool;

import android.content.Context;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.utils.DateUtil;
import com.example.framwork.utils.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sinia.com.baihangeducation.R;

/**
 * Created by ${wjw} on 2016/5/24.
 */
public class PickerUtils {

    /**
     * 初始化性别选择框
     */
    public static OptionsPickerView initPickerSex(Context context, final TextView view) {
        final ArrayList<String> sexPicker = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            sexPicker.add(context.getResources().getStringArray(R.array.sex)[i]);
        }
        OptionsPickerView pvOptions = new OptionsPickerView(context);
        pvOptions.setPicker(sexPicker);
        pvOptions.setRelativeLayoutButtonBg(R.color.color_eeeeee_gray);
        pvOptions.setPickerViewBg(R.color.white);
        pvOptions.setBtnCancelTextColor(R.color.bgColor_overlay);
        pvOptions.setBtnSubmitTextColor(R.color.color_red);
        pvOptions.setCyclic(false, true, true);
        pvOptions.setCancelable(true);
        //设置默认选中的三级项目
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = sexPicker.get(options1);
                view.setText(tx);
            }
        });
        return pvOptions;
    }


    public static TimePickerView initTimePicker(final Context context, final TextView view) {
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 56, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (DateUtil.getInstance().compareDate(DateUtil.getInstance().getTime(date))) {
                    Toast.getInstance().showWarningToast(context, "生日不能大于今天");
                } else {
                    view.setText(DateUtil.getInstance().getTime(date));
                }
            }
        });
        return pvTime;
    }


    public static TimePickerView initTimePickerAll(final Context context, final TextView view) {
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 56, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                view.setText(DateUtil.getInstance().getHHmm(date));
//                if (DateUtil.getInstance().compareDate(DateUtil.getInstance().getTime(date))) {
//                    Toast.getInstance().showWarningToast(context, "生日不能大于今天");
//                } else {
//
//                }
            }
        });
        return pvTime;
    }

    public static TimePickerView initTimeHoursPicker(final Context context, final TextView view) {
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.HOURS_MINS);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(1, 24);
//        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                view.setText(DateUtil.getInstance().getHHmmWithNoYYMMDD(date));
            }
        });
        return pvTime;
    }

    /**
     * 时间范围，最小
     *
     * @param context
     * @param view
     * @return
     */
    public static TimePickerView initMinTimePicker(final Context context, final TextView view) {
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 56, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (!DateUtil.getInstance().compareDate(DateUtil.getInstance().getTime(date))) {
                    Toast.getInstance().showWarningToast(context, "起始日期不能小于今天");
                } else {
                    view.setText(DateUtil.getInstance().getTime(date));
                }
            }
        });
        return pvTime;
    }

    /**
     * 时间范围，最大
     *
     * @param context
     * @param view
     * @return
     */
    public static TimePickerView initMaxTimePicker(final Context context, final TextView view, final String date1) {
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 56, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (!DateUtil.getInstance().compareDate(DateUtil.getInstance().getTime(date), date1)) {
                    Toast.getInstance().showWarningToast(context, "结束日期不能小于起始日期");
                } else {
                    view.setText(DateUtil.getInstance().getTime(date));
                }
            }
        });
        return pvTime;
    }

    public interface OnCityClickListener {
        void onCityClick(String provinceId, String cityId, String districtId);
    }

    public static String getLocationNameByID(final Context context,String provinceiD, String cityID, String distrId) {
        String locationName = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            //选项选择器
            final ArrayList<LocalProvinceBean> options1Items = new ArrayList<>();
            ArrayList<LocalCityBean> options2Items = new ArrayList<>();
            ArrayList<LocalDistrictBean> options3Items = new ArrayList<>();
            options1Items.addAll(BaseResponseBean.parseArray(stringBuilder.toString(), LocalProvinceBean.class));

            //通过省ID循环查找 得到省份名字
            for (int i = 0; i < options1Items.size(); i++) {
                if (provinceiD==options1Items.get(i).province_id||provinceiD.equals(options1Items.get(i).province_id)){
                    locationName = options1Items.get(i).province_name;
                    if (options1Items.get(i).city_list != null)
                        options2Items = options1Items.get(i).city_list;
                }
            }

            //通过城市ID循环查找 得到城市名字
            for (int i = 0; i < options2Items.size(); i++) {
                if (cityID==options2Items.get(i).city_id||cityID.equals(options2Items.get(i).city_id)){
                    locationName = locationName+" "+options2Items.get(i).city_name;
                    if (options2Items.get(i).district_list != null)
                        options3Items = options2Items.get(i).district_list;
                }
            }

            //通过区ID循环查找 得到区名字
            for (int i = 0; i < options3Items.size(); i++) {
                if (distrId==options3Items.get(i).district_id||distrId.equals(options3Items.get(i).district_id)){
                    locationName = locationName+" "+options3Items.get(i).district_name;
                }
            }



        }catch (IOException e) {
            e.printStackTrace();
        }

        return locationName;
    }


    public static OptionsPickerView initCityPicker(final Context context, final TextView view, final OnCityClickListener mOnCityClickListener) {
        final OptionsPickerView pvOptions = new OptionsPickerView(context);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            //选项选择器
            final ArrayList<LocalProvinceBean> options1Items = new ArrayList<>();
            final ArrayList<ArrayList<LocalCityBean>> options2Items = new ArrayList<>();
            final ArrayList<ArrayList<ArrayList<LocalDistrictBean>>> options3Items = new ArrayList<>();
            options1Items.addAll(BaseResponseBean.parseArray(stringBuilder.toString(), LocalProvinceBean.class));
            //选项2
            for (int i = 0; i < options1Items.size(); i++) {
                ArrayList<LocalCityBean> citys = options1Items.get(i).city_list;
                if (citys != null)
                    options2Items.add(citys);
            }
            //选项3
            if (options2Items.size() != 0) {
                for (int i = 0; i < options2Items.size(); i++) {
                    ArrayList<ArrayList<LocalDistrictBean>> options3Items_01 = new ArrayList<>();
                    if (options2Items.get(i) != null) {
                        int districts = options2Items.get(i).size();
                        for (int j = 0; j < districts; j++) {
                            options3Items_01.add(options2Items.get(i).get(j).district_list);
                        }
                        options3Items.add(options3Items_01);
                    }
                }
            }
            //三级联动效果
            pvOptions.setPicker(options1Items, options2Items, options3Items, true);
            pvOptions.setRelativeLayoutButtonBg(R.color.color_eeeeee_gray);
            pvOptions.setPickerViewBg(R.color.white);
            pvOptions.setBtnCancelTextColor(R.color.bgColor_overlay);
            pvOptions.setBtnSubmitTextColor(R.color.color_red);
            pvOptions.setCyclic(false, false, false);
            pvOptions.setCancelable(true);
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).province_name + " "
                            + options2Items.get(options1).get(option2).city_name + " "
                            + options3Items.get(options1).get(option2).get(options3).district_name;

                    if (mOnCityClickListener != null) {
                        mOnCityClickListener.onCityClick(options1Items.get(options1).province_id, options2Items.get(options1).get(option2).city_id, options3Items.get(options1).get(option2).get(options3).district_id);
                    }
                    view.setText(tx);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pvOptions;
    }

//    /**
//     * 支付方式
//     */
//    public static OptionsPickerView initPayPick(Context context, final TextView view) {
//        final ArrayList<String> payPicker = new ArrayList<>();
//        for (int i = 0; i < 1; i++) {
//            payPicker.add_friend(context.getResources().getStringArray(com.liangmaitong.user.R.array.pay)[i]);
//        }
//        OptionsPickerView pvOptions = new OptionsPickerView(context);
//        pvOptions.setPicker(payPicker);
//        pvOptions.setTitle("选择付款方式");
//        pvOptions.setCyclic(false, true, true);
//        //设置默认选中的三级项目
//        pvOptions.setRelativeLayoutButtonBg(com.liangmaitong.user.R.color.black_20252E);
//        pvOptions.setPickerViewBg(com.liangmaitong.user.R.color.black_2c3039);
//        pvOptions.setBtnCancelTextColor(com.liangmaitong.user.R.color.gray_98);
//        pvOptions.setBtnSubmitTextColor(com.liangmaitong.user.R.color.t_bule_498bea);
//        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//                String tx = payPicker.get(options1);
//                view.setText(tx);
//            }
//        });
//        return pvOptions;
//    }

}
