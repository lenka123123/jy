package sinia.com.baihangeducation.club.im.view;

import android.view.View;
import android.view.ViewGroup;

public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
