package rxh.shanks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ToggleButton;
import rxh.shanks.entity.DataSouceCoachEntity;
import rxh.shanks.presenter.LoginPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.utils.PrivateEducationCourseFragmentDataSouceUtils;
import rxh.shanks.view.LoginView;

/**
 * Created by Administrator on 2016/7/29.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.user)
    EditText user;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.password_ll)
    LinearLayout password_ll;
    @Bind(R.id.verification_code)
    EditText verification_code;
    @Bind(R.id.get_verification_code)
    Button get_verification_code;
    @Bind(R.id.verification_code_ll)
    LinearLayout verification_code_ll;
    @Bind(R.id.switch_login)
    ToggleButton switch_login;
    @Bind(R.id.forget_password)
    TextView forget_password;
    @Bind(R.id.login)
    Button login;
    LoginPresenter loginPresenter;
    private TimeCount time;
    //为false表示用户名登录，true表示手机号码登录
    boolean switch_login_flag = false;
    boolean eh_flag = false;

    private SharedPreferences sp;


    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    String phone = data.toString().substring(7, 18);
                    loginPresenter.login("1", null, phone, null);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        sp = getSharedPreferences("user_info", 0);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eh_flag) {
            SMSSDK.unregisterEventHandler(eh);
        }
    }

    public void initview() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (sp.getString("password", null) != null) {
            password.setText(sp.getString("password", null));
        }
        if (sp.getString("user", null) != null) {
            user.setText(sp.getString("user", null));
        }
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        forget_password.setOnClickListener(this);
        verification_code_ll.setVisibility(View.GONE);
        login.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        switch_login.setChecked(false);
        switch_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_login_flag = true;
                    user.setText("");
                    user.setHint("请输入电话号码");
                    forget_password.setVisibility(View.GONE);
                    password_ll.setVisibility(View.GONE);
                    verification_code_ll.setVisibility(View.VISIBLE);
                } else {
                    switch_login_flag = false;
                    user.setText("");
                    user.setHint("请输入用户名");
                    forget_password.setVisibility(View.VISIBLE);
                    verification_code_ll.setVisibility(View.GONE);
                    password_ll.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getverificationcode() {
        SMSSDK.initSDK(this, "15250bf89fb80", "3ef1b078f3b49b49cf22d6d099453646");
        SMSSDK.getVerificationCode("86", user.getText().toString());
        SMSSDK.registerEventHandler(eh); //注册短信回调
        eh_flag = true;
        time.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_password:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.get_verification_code:
                if (user.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "格式错误", Toast.LENGTH_LONG).show();
                } else {
                    if (CheckUtils.isMobileNO(user.getText().toString())) {
                        getverificationcode();
                    } else {
                        Toast.makeText(getApplicationContext(), "格式错误", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.login:
                if (switch_login_flag) {
                    if (verification_code.getText().length() > 0) {
                        SMSSDK.submitVerificationCode("86", user.getText().toString(), verification_code.getText().toString());
                        SMSSDK.registerEventHandler(eh); //注册短信回调
                        eh_flag = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_LONG).show();
                    }
                } else {
                    loginPresenter.login("2", null, user.getText().toString(), password.getText().toString());
                }
                break;
            default:
                break;
        }
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            get_verification_code.setText("重新验证");
            get_verification_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            get_verification_code.setClickable(false);
            get_verification_code.setText(millisUntilFinished / 1000 + "秒");
        }

    }


    @Override
    public void show() {
        loading("登录中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void onSuccess(String result) {

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", password.getText().toString());
        editor.putString("user", user.getText().toString());
        editor.commit();
        if (result.equals("1")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (result.equals("0")) {
            startActivity(new Intent(getApplicationContext(), SetUserInformationActivity.class));
            finish();
        }
    }

    @Override
    public void onError(Throwable ex) {

    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }
}
