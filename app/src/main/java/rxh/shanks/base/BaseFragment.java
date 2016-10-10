package rxh.shanks.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/10.
 */
public class BaseFragment extends Fragment {

    LoadingFragment loadingFragment;
    LoadingNoPromptFragment loadingNoPromptFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 显示进度条,flag==ture表示点击dialog外部，dialog不会消失。flag==false表示点击dialog外部，dialog会消失
    public void loading(String title, String flag) {
        if (loadingFragment == null) {
            loadingFragment = new LoadingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("flag", flag);
            loadingFragment.setArguments(bundle);
        }
        if (!loadingFragment.isAdded()) {
            loadingFragment.show(getChildFragmentManager(), "loadingFragment");
        }
    }

    public void loading(String flag) {
        if (loadingNoPromptFragment == null) {
            loadingNoPromptFragment = new LoadingNoPromptFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", flag);
            loadingNoPromptFragment.setArguments(bundle);
        }
        if (!loadingNoPromptFragment.isAdded()) {
            loadingNoPromptFragment.show(getChildFragmentManager(), "loadingNoPromptFragment");
        }
    }

    // 隐藏进度条
    public void dismiss() {
        Fragment prev = getChildFragmentManager().findFragmentByTag("loadingFragment");
        if (prev != null) {
            loadingFragment.dismiss();
            return;
        }
        prev = getChildFragmentManager().findFragmentByTag("loadingNoPromptFragment");
        if (prev != null) {
            loadingNoPromptFragment.dismiss();
            return;
        }
    }
}
