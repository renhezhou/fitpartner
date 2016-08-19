package rxh.shanks.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import rxh.shanks.activity.R;
import rxh.shanks.fragment.PopupFragment;


/**
 * Created by Administrator on 2016/4/10.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, PopupFragment.PopupFragmentListener {

    LoadingFragment loadingFragment;
    LoadingNoPromptFragment loadingNoPromptFragment;
    PopupFragment popupFragment;
    TextView no_net_working, no_response;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 加载view,flag==0表示没网，flag==404表示请求失败
    public void initactivity(int flag) {
        if (flag == 0) {
            setContentView(R.layout.activity_base_no_net_working);
            no_net_working = (TextView) findViewById(R.id.no_net_working);
            no_net_working.setOnClickListener(this);
        } else if (flag == 404) {
            setContentView(R.layout.activity_base_no_response);
            no_response = (TextView) findViewById(R.id.no_response);
            no_response.setOnClickListener(this);
        }

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
        loadingFragment.show(getSupportFragmentManager(), "loadingFragment");
    }

    public void loading(String flag) {
        if (loadingNoPromptFragment == null) {
            loadingNoPromptFragment = new LoadingNoPromptFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", flag);
            loadingNoPromptFragment.setArguments(bundle);
        }
        loadingNoPromptFragment.show(getSupportFragmentManager(), "loadingNoPromptFragment");
    }

    public void showpopupfragment() {
        if (popupFragment == null) {
            popupFragment = new PopupFragment();
        }
        popupFragment.show(getSupportFragmentManager(), "popupFragment");
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


    // 判断网络是否可用,返回true表示可用，返回false表示不可用
    public Boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    //PopupFragment弹窗点击了确定或者取消后产生的回调
    @Override
    public void onClick(View v) {

    }

    @Override
    public void confirm() {

    }

    @Override
    public void cancel() {

    }


    //android弹窗


}
