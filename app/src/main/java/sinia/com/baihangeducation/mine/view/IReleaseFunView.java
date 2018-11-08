package sinia.com.baihangeducation.mine.view;

import com.alibaba.fastjson.JSONArray;
import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

public interface IReleaseFunView extends BaseView{
    List<String> getReleaseFunImgs();

    String getReleaseFunContent();

    String getReleaseFunLng();

    String getReleaseFunLat();

    String getReleaseFunAdcode();

    JSONArray getReleaseFunAllImgs();

    void releaseSingleImageSuccess(SingleImageBean imgbean,int i);

    void releaseFunSuccess();
    void releaseFunFail();

}
