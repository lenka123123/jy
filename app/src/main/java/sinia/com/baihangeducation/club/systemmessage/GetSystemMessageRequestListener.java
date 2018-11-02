package sinia.com.baihangeducation.club.systemmessage;


public interface GetSystemMessageRequestListener {
    void setRequestSuccess(ClubMessageList msg, int max);

    void setRequestFail();

}
