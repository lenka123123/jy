package sinia.com.baihangeducation.club.im.chat;

import android.view.ViewGroup;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
