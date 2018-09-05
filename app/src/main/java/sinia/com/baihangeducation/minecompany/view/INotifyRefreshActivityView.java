package sinia.com.baihangeducation.minecompany.view;

public interface INotifyRefreshActivityView {
    /**
     * 通知跳转编辑页面
     */
    void go2EditActivity(int jobId);

    /**
     * 下架操作
     */
    void downJob(int jobId);

    /**
     * 上架操作
     */
    void upJob(int jobId);

    /**
     * 删除操作
     */
    void detelJob(int jobId);
}
