package sinia.com.baihangeducation;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

public class TestAct extends AppCompatActivity  {

    String job_lng = "119.028296";
    String job_lat = "31.651125";


    MapView mapView=null;//地图视图
    AMap aMap;//地图对象
    CameraUpdate cameraUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytitle);
        mapView= (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);//创建地图
        aMap=mapView.getMap();//获取地图对象

        getSearchAdd();
    }



    public void getSearchAdd() {
        PoiSearch.Query query = new PoiSearch.Query("广东电视台", "");
        PoiSearch poiSearch = new PoiSearch(TestAct.this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int errcode) {
                //判断搜索成功
                if (errcode == 1000) {
                    if (null != poiResult && poiResult.getPois().size() > 0) {


                        for (int i = 0; i < poiResult.getPois().size(); i++) {
                            if (i == 0) {
                                getadress(poiResult.getPois().get(i).getLatLonPoint().toString(), poiResult.getPois().get(i).getLatLonPoint().toString(), "广东电视台");
                                return;
                            }


                            Log.i("TAG_MAIN", "POI 的行政区划代码和名称=" + poiResult.getPois().get(i).getAdCode() + "," + poiResult.getPois().get(i).getAdName());
                            Log.i("TAG_MAIN", "POI的所在商圈=" + poiResult.getPois().get(i).getBusinessArea());
                            Log.i("TAG_MAIN", "POI的城市编码与名称=" + poiResult.getPois().get(i).getCityCode() + "," + poiResult.getPois().get(i).getCityName());
                            Log.i("TAG_MAIN", "POI 的经纬度=" + poiResult.getPois().get(i).getLatLonPoint());
                            Log.i("TAG_MAIN", "POI的地址=" + poiResult.getPois().get(i).getSnippet());
                            Log.i("TAG_MAIN", "POI的名称=" + poiResult.getPois().get(i).getTitle());
                        }
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }

        });
    }


    //解析指定坐标的地址
    public void getadress(String s, String toString, String 广东电视台){
        Log.e("Shunxu","调用getadress");
        GeocodeSearch geocodeSearch=new GeocodeSearch(this);//地址查询器

        //设置查询参数,
        //三个参数依次为坐标，范围多少米，坐标系
        RegeocodeQuery regeocodeQuery=new RegeocodeQuery(new LatLonPoint(39.22,116.39),200,GeocodeSearch.AMAP);

        //设置查询结果监听
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            //根据坐标获取地址信息调用
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                String s =regeocodeResult.getRegeocodeAddress().getFormatAddress();
                Log.e("Shunxu","获得请求结果");
                makepoint("我家");
            }
            //根据地址获取坐标信息是调用
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });

        geocodeSearch.getFromLocationAsyn(regeocodeQuery);//发起异步查询请求
    }












    //根据地址绘制需要显示的点
    public void makepoint(String sss){
        Log.e("Shunxu","开始绘图");
        //北纬39.22，东经116.39，为负则表示相反方向
        LatLng latLng=new LatLng(39.22,116.39);
        Log.e("地址",sss);

        //使用默认点标记
        Marker maker=aMap.addMarker(new MarkerOptions().position(latLng).title("地点:").snippet(sss));


        //自定义点标记
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(new LatLng(34,115)).title("标题").snippet("内容");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.ic_launcher)));//设置图标
        aMap.addMarker(markerOptions);

        //改变可视区域为指定位置
        //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
        cameraUpdate= CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,8,0,30));
        aMap.moveCamera(cameraUpdate);//地图移向指定区域


    }



    //将地图生命周期跟活动绑定，减少某些不必要的bug
    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}