package sinia.com.baihangeducation.reconsitution.tabs.selectcity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.GridViewSim;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import static sinia.com.baihangeducation.home.HomeFragment.cityID;

public class SelectCityActivity extends BaseActivity {

    private List<String> mCity;
    private List<CityInfo> mCityDatas = new ArrayList<>();
    private String adcode = "320106";
    private GridViewSim mAddressArrayAdapter;
    private TextView defaultCityName;
    private String cityName;

    public int initLayoutResID() {
        return R.layout.activity_select_city;
    }
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }
    @Override
    protected void initData() {
        mCommonTitle.setCenterText("选择城市");
        SelectCityModel selectCityModel = new SelectCityModel(this);
        selectCityModel.getCityData();

    }

    @Override
    protected void initView() {
        GridView mMyGridView = findViewById(R.id.MyGridView);
        defaultCityName = findViewById(R.id.default_city_name);
        mMyGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mCity = new ArrayList();
        mAddressArrayAdapter = new GridViewSim(context, mCity);
        mMyGridView.setAdapter(mAddressArrayAdapter);

        defaultCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfig.CTYLEID = "1388";
                AppConfig.CTYLENAME = cityName;
                AppConfig.CTYLENAMESELECT = true;
                SelectCityActivity.this.finish();
            }
        });

        mMyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cityID = String.valueOf(mCityDatas.get(i).city_id);
                cityName = String.valueOf(mCityDatas.get(i).name);
                AppConfig.CTYLEID = cityID;
                AppConfig.CTYLENAME = cityName;
                AppConfig.CTYLENAMESELECT = true;
                SelectCityActivity.this.finish();
//              adressName.setText(mCity.get(i));
            }
        });
    }

    public void getCityListSuccess(List<CityInfo> mCityInfo) {
        int delete = 0;
        if (mCityInfo != null) {
            for (int i = 0; i < mCityInfo.size(); i++) {

                if ((mCityInfo.get(i).is_default) == 1) {   //是否为默认城市    ( 1是2否 )
                    cityName = mCityInfo.get(i).name;
                    cityID = String.valueOf(mCityInfo.get(i).city_id);
                    defaultCityName.setText(cityName);
                    cityID = mCityInfo.get(i).city_id + "";
                }
            }


            for (int i = 0; i < mCityInfo.size(); i++) {
                if (!mCityInfo.get(i).name.equals(cityName)) {
                    mCity.add(mCityInfo.get(i).name);
                    mCityDatas.add(mCityInfo.get(i));
                }
            }
            mAddressArrayAdapter.setData(mCity);
        }
    }
}
