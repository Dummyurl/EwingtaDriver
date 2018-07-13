package ewingta.domesticlogistic.driver.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.activities.BaseActivity;
import ewingta.domesticlogistic.driver.utils.ToastUtil;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseActivity.class.getSimpleName();

    public void showInfoToast(int msg) {
        ToastUtil.showInfo(getActivity(), msg);
    }

    public void showErrorToast(int msg) {
        ToastUtil.showError(getActivity(), msg);
    }

    public void showErrorToast(String msg) {
        ToastUtil.showError(getActivity(), msg);
    }

    public void showSuccessToast(int msg) {
        ToastUtil.showSuccess(getActivity(), msg);
    }

    public boolean isNetworkAvailable() {
        boolean isAvailable = false;

        try {
            Activity activity = getActivity();
            if (activity != null && !activity.isFinishing()) {
                MainActivity homeActivity = (MainActivity) activity;
                isAvailable = homeActivity.isNetworkAvailable();
            }
        } catch (Exception e) {
            Log.e(TAG, "isNetworkAvailable() - " + e.toString(), e);
        }

        return isAvailable;
    }

    public void logError(Exception exception) {
    }

    public void logError(String exception) {
    }
}
