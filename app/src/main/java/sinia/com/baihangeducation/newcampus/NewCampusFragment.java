package sinia.com.baihangeducation.newcampus;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.view.AddFriendListActivity;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusFragment;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018.02.24.

 */

public class NewCampusFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private FragmentManager childFragmentManager;
//    private FragmentTransaction ft;

    private View view;

    private RadioButton mHelp;              //互助
    private RadioButton mGive;              //转让
    private RadioButton mFun;               //趣事
    private NewCampusHelpFragment mNewCampusHelpFragment;       //互助
    private NewCampusGiveFragment mNewCampusGiveFragment;       //转让
    private ImageView mAddFriend;
    private FunCampusFragment funCampusFragment;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (funCampusFragment!=null){
            funCampusFragment.setVisibleToUser(isVisibleToUser);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.newcampusfragment, null);
        mAddFriend = view.findViewById(R.id.add_friend);
        mAddFriend.setOnClickListener(this);
        childFragmentManager = getChildFragmentManager();
        initView();
        initData();
        return view;
    }

    private void initData() {
        FragmentTransaction ft = childFragmentManager.beginTransaction();
        funCampusFragment = new FunCampusFragment();
        // TODO: 2018/7/4 0004       mNewCampusFunFragment = new NewCampusFunFragment();
        ft.add(R.id.newcampusfragment_framelayout, funCampusFragment);
        ft.commit();
    }

    private void initView() {
        mHelp = view.findViewById(R.id.newcampusfragment_help);
        mGive = view.findViewById(R.id.newcampusfragment_give);
        mFun = view.findViewById(R.id.newcampusfragment_fun);

        mHelp.setOnClickListener(this);
        mGive.setOnClickListener(this);
        mFun.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = childFragmentManager.beginTransaction(); //开启一个事务
        switch (view.getId()) {
            case R.id.add_friend:
                Goto.toAddFriend(context);

                break;
            case R.id.newcampusfragment_help:
                //互助
                if (null == mNewCampusHelpFragment) {
                    mNewCampusHelpFragment = new NewCampusHelpFragment();
                    ft.add(R.id.newcampusfragment_framelayout, mNewCampusHelpFragment);
                }
                if (null != mNewCampusGiveFragment) {
                    ft.hide(mNewCampusGiveFragment);
                }
//                if (null != mNewCampusFunFragment) {
//                    ft.hide(mNewCampusFunFragment);
//                }
                ft.show(mNewCampusHelpFragment);
                break;
            case R.id.newcampusfragment_give:
                //转让
                if (null == mNewCampusGiveFragment) {
                    mNewCampusGiveFragment = new NewCampusGiveFragment();
                    ft.add(R.id.newcampusfragment_framelayout, mNewCampusGiveFragment);
                }
                if (null != mNewCampusHelpFragment) {
                    ft.hide(mNewCampusHelpFragment);
                }
//                if (null != mNewCampusFunFragment) {
//                    ft.hide(mNewCampusFunFragment);
//                }
                ft.show(mNewCampusGiveFragment);
                break;
            case R.id.newcampusfragment_fun:
                //趣事
//                if (null == mNewCampusFunFragment) {
//                    mNewCampusFunFragment = new NewCampusFunFragment();
//                    ft.add_friend(R.id.newcampusfragment_framelayout, mNewCampusFunFragment);
//                }
                if (null != mNewCampusHelpFragment) {
                    ft.hide(mNewCampusHelpFragment);
                }
                if (null != mNewCampusGiveFragment) {
                    ft.hide(mNewCampusGiveFragment);
                }
//                ft.show(mNewCampusFunFragment);
                break;
        }
        ft.commit();   //提交事务
    }
}
