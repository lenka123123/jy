package sinia.com.baihangeducation.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mcxtzhang.swipemenulib.base.BaseFragment;

public class StatedFragment extends BaseFragment {

    Bundle savedState;

    public StatedFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (!restoreStateFromArguments()) {

            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveStateToArguments();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        saveStateToArguments();
    }


    private void saveStateToArguments() {
        if (getView() != null) savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle("internalSavedViewState8954201239547", savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("internalSavedViewState8954201239547");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }
    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    private Bundle saveState() {
        Bundle state = new Bundle();

        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }


    @Override
    public void onClick(View view) {

    }
}