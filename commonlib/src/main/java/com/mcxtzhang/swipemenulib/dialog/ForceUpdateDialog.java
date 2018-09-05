package com.mcxtzhang.swipemenulib.dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.framwork.utils.ViewFindUtils;
import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.info.bean.VersionInfo;


/**
 * Created by wanjingyu on 2016/6/24.
 */
public class ForceUpdateDialog extends BaseDialog<ForceUpdateDialog> {
    private TextView btnUpdate;
    private TextView btnNoUpdate;
    private Activity mContext;
    private VersionInfo info;

    public ForceUpdateDialog(Activity context, VersionInfo info) {
        super(context);
        this.info = info;
        this.mContext = context;
    }

    @Override
    public View onCreateView() {
        widthScale(0.75f);
        showAnim(new Swing());
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_force_update_app, null);
        btnUpdate = ViewFindUtils.find(inflate, R.id.img_update);
        btnNoUpdate = ViewFindUtils.find(inflate, R.id.img_not_update);
        TextView txtContent = ViewFindUtils.find(inflate, R.id.version_update_content);
        StringBuilder sbIntro = new StringBuilder();
        for (int i = 0; i < info.intro.length; i++) {
            sbIntro.append(info.intro[i]).append("\n");
        }
        txtContent.setText(sbIntro.toString());
        TextView versionNum = ViewFindUtils.find(inflate, R.id.version_num);
        versionNum.setText(info != null && info.version != null ? "V" + info.version : "");
//        inflate.setBackgroundDrawable(
//                CornerUtils.cornerDrawable(Color.parseColor("#00ffffff"), dp2px(5)));
        if (info.is_force == 2) {
            btnNoUpdate.setVisibility(View.VISIBLE);
            this.setCanceledOnTouchOutside(true);
        } else {
            btnNoUpdate.setVisibility(View.GONE);
            this.setCanceledOnTouchOutside(false);
        }
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.down_url != null)
                    startDownload(info.down_url);
            }
        });
        btnNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void startDownload(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (path.contains("http")) {
            intent.setData(Uri.parse(path));
        } else {
            intent.setData(Uri.parse("http://" + path));
        }
        mContext.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (info.is_force == 2) {
            dismiss();
        } else {
            mContext.finish();
        }
    }
}
