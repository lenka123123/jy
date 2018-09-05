package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.NewTaskInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public interface NewTaskView extends BaseView {

    String getPage();

    String getPerpage();

    void getNewTaskSuccess(List<NewTaskInfo> data, int maxpage);

}
