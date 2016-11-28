package rxh.shanks.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import rxh.shanks.activity.R;
import rxh.shanks.fragment.PopupFragment;
import rxh.shanks.fragment.SignOutDialogFragment;


/**
 * Created by Administrator on 2016/4/10.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, PopupFragment.PopupFragmentListener, SignOutDialogFragment.SignOutDialogFragmentListener {

    LoadingFragment loadingFragment;
    LoadingNoPromptFragment loadingNoPromptFragment;
    SignOutDialogFragment signOutDialogFragment;
    TextView r_load;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        r_load = (TextView) findViewById(R.id.r_load);
        r_load.setOnClickListener(this);
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
        getSupportFragmentManager().executePendingTransactions();
        if (!loadingFragment.isAdded()) {
            loadingFragment.show(getSupportFragmentManager(), "loadingFragment");
        }
    }

    public void loading(String title) {
        loadingNoPromptFragment = new LoadingNoPromptFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", title);
        loadingNoPromptFragment.setArguments(bundle);
        getSupportFragmentManager().executePendingTransactions();
        if (!loadingNoPromptFragment.isAdded()) {
            loadingNoPromptFragment.show(getSupportFragmentManager(), "loadingNoPromptFragment");
        }
    }

    // 隐藏进度条
    public void dismiss() {
        Fragment prev = getSupportFragmentManager().findFragmentByTag("loadingFragment");
        if (prev != null) {
            loadingFragment.dismiss();
            return;
        }
        prev = getSupportFragmentManager().findFragmentByTag("loadingNoPromptFragment");
        if (prev != null) {
            loadingNoPromptFragment.dismiss();
            return;
        }
    }

    @Override
    public void login() {

    }

    @Override
    public void onClick(View v) {

    }


    // 加载view,flag==0表示没网，flag==404表示请求失败
//    public void initactivity(int flag) {
//        if (flag == 0) {
//            setContentView(R.layout.activity_base_no_net_working);
//            no_net_working = (TextView) findViewById(R.id.no_net_working);
//            no_net_working.setOnClickListener(this);
//        } else if (flag == 404) {
//            setContentView(R.layout.activity_base_no_response);
//            no_response = (TextView) findViewById(R.id.no_response);
//            no_response.setOnClickListener(this);
//        }
//
//    }

    //解决问题的方法之一，重写dialogfragment的show方法
//    public void show(FragmentManager manager, String tag) {
//        mDismissed = false;
//        mShownByMe = true;
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(this, tag);
//        ft.commitAllowingStateLoss();
//    }


//    public void showpopupfragment() {
//        if (popupFragment == null) {
//            popupFragment = new PopupFragment();
//        }
//        popupFragment.show(getSupportFragmentManager(), "popupFragment");
//    }


    // 判断网络是否可用,返回true表示可用，返回false表示不可用
//    public Boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//        if (ni != null && ni.isConnectedOrConnecting()) {
//            return true;
//        } else {
//            return false;
//        }
//    }


    //PopupFragment弹窗点击了确定或者取消后产生的回调
    @Override
    public void confirm() {

    }

    @Override
    public void cancel() {

    }


    //android弹窗


}
