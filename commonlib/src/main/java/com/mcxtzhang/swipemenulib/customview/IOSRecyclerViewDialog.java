//package com.mcxtzhang.swipemenulib.customview;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.mcxtzhang.swipemenulib.R;
//
//public class IOSRecyclerViewDialog {
//
//
//    private Context context;
//    private Dialog dialog;
//    private LinearLayout lLayout_bg;
//    private TextView txt_title;
//    private TextView txt_msg;
//    private Button btn_neg;
//    private Button btn_pos;
//    //private ImageView img_line;
//    private Display display;
//    private boolean showTitle = false;
//    private boolean showMsg = false;
//    private boolean showPosBtn = false;
//    private boolean showNegBtn = false;
//    private View view;
//    private FrameLayout frameLayout;
//
//    public IOSRecyclerViewDialog(Context context) {
//        this.context = context;
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public IOSRecyclerViewDialog builder() {
//        view = LayoutInflater.from(context).inflate(R.layout.dialog_recycler_view, null);
//        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
//        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
//        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
//        txt_msg.setVisibility(View.GONE);
//        btn_neg = (Button) view.findViewById(R.id.btn_neg);
//        btn_neg.setVisibility(View.GONE);
//        btn_pos = (Button) view.findViewById(R.id.btn_pos);
//        btn_pos.setVisibility(View.GONE);
//
//        frameLayout = (FrameLayout) view.findViewById(R.id.customPanel);
//
//        // ????Dialog????????
//        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.setContentView(view);
//        dialog.setCancelable(false);
//        // ????dialog????????
//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setCustomView(View v, ViewGroup.LayoutParams params){
//        if (frameLayout.getChildCount() > 0)
//            frameLayout.removeAllViews();
//
//        txt_msg.setVisibility(View.GONE);
//        frameLayout.addView(v, params);
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setTitle(String title) {
//        showTitle = true;
//        if ("".equals(title)) {
//            txt_title.setText("????");
//        } else {
//            txt_title.setText(title);
//        }
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setMsg(String msg) {
//        frameLayout.setVisibility(View.GONE);
//        showMsg = true;
//        if ("".equals(msg)) {
//            txt_msg.setText("????");
//        } else {
//            txt_msg.setText(msg);
//        }
//        return this;
//    }
//
//
//    public IOSRecyclerViewDialog setCancelable(boolean cancel) {
//        dialog.setCancelable(cancel);
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setCanceledOnTouchOutside(boolean cancel) {
//        dialog.setCanceledOnTouchOutside(cancel);
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setPositiveButton(String text,
//                                                   final View.OnClickListener listener) {
//        showPosBtn = true;
//        if ("".equals(text)) {
//            btn_pos.setText("???");
//        } else {
//            btn_pos.setText(text);
//        }
//        btn_pos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null)
//                    listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    public IOSRecyclerViewDialog setNegativeButton(String text,
//                                                   final View.OnClickListener listener) {
//        showNegBtn = true;
//        if ("".equals(text)) {
//            btn_neg.setText("???");
//        } else {
//            btn_neg.setText(text);
//        }
//        btn_neg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null)
//                    listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    private void setLayout() {
//        if (!showTitle && !showMsg) {
//            txt_title.setText("???");
//            txt_title.setVisibility(View.VISIBLE);
//        }
//
//        if (showTitle) {
//            txt_title.setVisibility(View.VISIBLE);
//        }
//
//        if (!showPosBtn && !showNegBtn) {
//            btn_pos.setText("???");
//            btn_pos.setVisibility(View.VISIBLE);
//            //btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
//            btn_pos.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//        }
//
//        if (showPosBtn && showNegBtn) {
//            btn_pos.setVisibility(View.VISIBLE);
//            //btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
//            btn_neg.setVisibility(View.VISIBLE);
//            //btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
//            //img_line.setVisibility(View.VISIBLE);
//        }
//
//        if (showPosBtn && !showNegBtn) {
//            btn_pos.setVisibility(View.VISIBLE);
//            //btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
//        }
//
//        if (!showPosBtn && showNegBtn) {
//            btn_neg.setVisibility(View.VISIBLE);
//            //btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
//        }
//    }
//
//    public void show() {
//        setLayout();
//        dialog.show();
//    }
//
//
//
//
//
//
//
//
//}
