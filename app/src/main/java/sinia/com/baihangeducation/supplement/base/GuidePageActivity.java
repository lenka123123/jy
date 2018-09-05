package sinia.com.baihangeducation.supplement.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.framwork.banner.SimpleGuideResBanner;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.ViewFindUtils;
import com.flyco.banner.anim.select.ZoomInEnter;

import java.util.ArrayList;

import com.mcxtzhang.swipemenulib.utils.Constants;

import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;


/**
 * 引导页
 * Created by gaoy on 2016/10/24.
 */

public class GuidePageActivity extends Activity {

    private Context context = this;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        decorView = getWindow().getDecorView();
        sgb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void sgb() {
        SimpleGuideResBanner sgb = ViewFindUtils.find(decorView, R.id.sgb);
        sgb.setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(null)
                .barPadding(0, 10, 0, 10)
                .setSource(geUsertGuides())
                .startScroll();
        sgb.setOnJumpClickL(new SimpleGuideResBanner.OnJumpClickL() {
            @Override
            public void onJumpClick() {
                SPUtils.getInstance().put(GuidePageActivity.this, Constants.IS_FIRST_START, false);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private static ArrayList<Integer> geUsertGuides() {
        ArrayList<Integer> list = new ArrayList<>();
//        list.add_friend(R.drawable.new_guide1);
        list.add(R.drawable.new_guide2);
        list.add(R.drawable.new_guide3);
        list.add(R.drawable.new_guide4);
        return list;
    }
}
