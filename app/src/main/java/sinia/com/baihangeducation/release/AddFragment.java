//package sinia.com.baihangeducation.release;
//
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import sinia.com.baihangeducation.R;
//import sinia.com.baihangeducation.release.info.ApplyResultInfo;
//import sinia.com.baihangeducation.release.presenter.AddFragmentPresenter;
//import sinia.com.baihangeducation.release.view.IAddFragmentView;
//
//import com.mcxtzhang.swipemenulib.base.BaseFragment;
//
//import sinia.com.baihangeducation.MyApplication;
//import sinia.com.baihangeducation.supplement.base.Goto;
//import sinia.com.baihangeducation.supplement.tool.BaseUtil;
//
//public class AddFragment extends BaseFragment implements IAddFragmentView {
//
//    private TextView mGetPerson;
//    private TextView mFindAllTime;
//    private TextView mFindPartTime;
//    private TextView mSchoolFun;
//    private TextView mSchoolHelp;
//    private TextView mResumeFindJob;
//    private TextView mReleaseTraing;
//    private MyApplication application;
//    private AddFragmentPresenter presenter;
//
//    @Override
//    public int initLayoutResID() {
//        return R.layout.addfragment;
//    }
//
//    @Override
//    protected void initData() {
//        application = (MyApplication) context.getApplication();
//
//        presenter = new AddFragmentPresenter(context, this);
//
//    }
//
//    @Override
//    protected void initView() {
//        mGetPerson = $(R.id.addfragment_getpeople);
//        mFindAllTime = $(R.id.addfragment_findalltime);
//        mFindPartTime = $(R.id.addfragment_findparttime);
//        mSchoolFun = $(R.id.addfragment_schoolfun);
//        mSchoolHelp = $(R.id.addfragment_schoolhelp);
//        mResumeFindJob = $(R.id.addfragment_resumefindjob);
//        mReleaseTraing = $(R.id.addfragment_releasetraining);
//
//        mGetPerson.setOnClickListener(this);
//        mFindAllTime.setOnClickListener(this);
//        mFindPartTime.setOnClickListener(this);
//        mSchoolFun.setOnClickListener(this);
//        mSchoolHelp.setOnClickListener(this);
//        mResumeFindJob.setOnClickListener(this);
//        mReleaseTraing.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (!BaseUtil.isLogin(context, application)) {
//            return;
//        }
//        switch (v.getId()) {
//            case R.id.addfragment_getpeople:
//                presenter.getApplyResult(1);
////                Goto.toCompanyJoinInActivity(context);
//                break;
//            case R.id.addfragment_findalltime: //我要找全职
//                Goto.toSiftUrNeedActivity(context, "1");
//                break;
//            case R.id.addfragment_findparttime:
//                Goto.toSiftUrNeedActivity(context, "2");
//                break;
//            case R.id.addfragment_schoolfun:
////                Goto.toReleaseInterestingActiviyy(context);
//                Goto.toReleaseFunActivity(context);
//                break;
//            case R.id.addfragment_schoolhelp:
//                Goto.toReleaseHelpEachOtherActivity(context);
//                break;
//            case R.id.addfragment_resumefindjob:
//                Goto.toMyResumeActivity(context, "ADD");
//                break;
//            case R.id.addfragment_releasetraining:
//                presenter.getApplyResult(2);
////                Goto.toReleaseTrainingAcgtivity(context);
//                break;
//        }
//    }
//
//    @Override
//    public void getGetBaseInfoSuccess(ApplyResultInfo info, int flag) {
//        switch (flag) {
//            case 1:
//                if (info != null && info.user_type != 1) {
//                    // user_type;               //类型 1普通用户 2普通企业 3培训机构
//                    // company_apply_status;       //审核状态 1已通过 2未通过
//                    switch (info.company_apply_status) {
//                        case 1:
//                            Goto.toReleaseJobInfoActivity(context);
//                            break;
//                        case 2:
//                            Goto.toCompanyJoinInActivity(context);
//                            break;
//                    }
//                } else {
//                    Goto.toCompanyJoinInActivity(context);
//                }
//                break;
//            case 2:
//                if (info != null && info.user_type == 3) {
//                    switch (info.company_apply_status) {
//                        case 1:
//                            Goto.toReleaseTrainingAcgtivity(context);
//                            break;
//                        case 2:
//                            Toast.makeText(context, "很抱歉，只有培训机构可以发布培训", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                } else {
//                    Toast.makeText(context, "很抱歉，只有培训机构可以发布培训", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//
//    }
//
//}
