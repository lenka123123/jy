//package com.mcxtzhang.swipemenulib.dialog;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//
//import com.example.framwork.utils.Toast;
//import com.mcxtzhang.swipemenulib.R;
//
//import razerdp.basepopup.BasePopupWindow;
//
//public class SlideFromBottomPopup extends BasePopupWindow implements View.OnClickListener {
//
//
//    private View popupView;
//
//    public SlideFromBottomPopup(Activity context) {
//        super(context,300,600);
//        bindEvent();
//    }
//
//
//    @Override
//    protected Animation initShowAnimation() {
//        return getTranslateVerticalAnimation(1f, 0, 500);
//    }
//
//    @Override
//    protected Animation initExitAnimation() {
//        return getTranslateVerticalAnimation(0, 1f, 500);
//    }
//
//    @Override
//    public View getClickToDismissView() {
//        return popupView.findViewById(R.id.cancel);
//    }
//
//    @Override
//    public View onCreatePopupView() {
//        popupView = LayoutInflater.from(getContext()).inflate(R.layout.pop_window_is_no, null);
//        return popupView;
//    }
//
//    @Override
//    public View initAnimaView() {
//        return popupView.findViewById(R.id.linearLayout);
//    }
//
//    private void bindEvent() {
//        if (popupView != null) {
//            popupView.findViewById(R.id.is).setOnClickListener(this);
//            popupView.findViewById(R.id.no).setOnClickListener(this);
//            popupView.findViewById(R.id.cancel).setOnClickListener(this);
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.is) {
//            Toast.getInstance().showSuccessToast(getContext(), "click tx_1");
//
//
//        } else if (i == R.id.no) {
//            Toast.getInstance().showSuccessToast(getContext(), "click tx_2");
//
//        } else if (i == R.id.cancel) {
//            Toast.getInstance().showSuccessToast(getContext(), "click tx_3");
//
//        } else {
//        }
//
//    }
//}
