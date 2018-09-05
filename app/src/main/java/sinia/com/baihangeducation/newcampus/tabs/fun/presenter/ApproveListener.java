package sinia.com.baihangeducation.newcampus.tabs.fun.presenter;

public interface ApproveListener {
// '{"is_praise":"1","praise_num":"0"} 点赞
    void actionSuccess(int flag,int praise_num );
    void actionFail();
}
