package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.entity.YZMEntity;
import rxh.shanks.presenter.LoginPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.view.LoginView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class ForgetPasswordActivity extends BaseActivity implements Handler.Callback {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.phone_num)
    EditText phone_num;
    @Bind(R.id.verification_code)
    EditText verification_code;
    @Bind(R.id.get_verification_code)
    Button get_verification_code;
    @Bind(R.id.confirm)
    Button confirm;

    boolean eh_flag = false;
    private TimeCount time;
    final Handler handler = new Handler(this);
    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            handler.sendMessage(msg);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "15250bf89fb80", "3ef1b078f3b49b49cf22d6d099453646");
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
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        title.setText("忘记密码");
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.get_verification_code:
                if (phone_num.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    if (CheckUtils.isMobileNO(phone_num.getText().toString())) {
                        getverificationcode();
                    } else {
                        Toast.makeText(getApplicationContext(), "手机号码格式不正确", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.confirm:
                if (phone_num.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (verification_code.getText().length() > 0) {
                    SMSSDK.submitVerificationCode("86", phone_num.getText().toString(), verification_code.getText().toString());
                    SMSSDK.registerEventHandler(eh); //注册短信回调
                    eh_flag = true;
                    loading("验证中...");
                } else {
                    Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        YZMEntity entity = new YZMEntity();
        dismiss();
        switch (event) {
            case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //验证成功
                    String phone = data.toString().substring(7, 18);
                    startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                    finish();
                } else {
                    //验证失败
                    entity = JsonUtils.get_mob_data(((Throwable) data).getMessage().toString());
                    if (entity.getDetail() != null) {
                        Toast.makeText(getApplicationContext(), entity.getDetail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "验证失败,请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //获取验证码成功
                    if (time != null) {
                        time.start();
                    } else {
                        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
                        time.start();
                    }
                } else {
                    //获取验证码失败
                    entity = JsonUtils.get_mob_data(((Throwable) data).getMessage().toString());
                    if (entity.getDetail() != null) {
                        Toast.makeText(getApplicationContext(), entity.getDetail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "获取验证码失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return false;
    }

    public void getverificationcode() {
        SMSSDK.getVerificationCode("86", phone_num.getText().toString());
        SMSSDK.registerEventHandler(eh); //注册短信回调
        eh_flag = true;
        loading("获取验证码中...");
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

}
