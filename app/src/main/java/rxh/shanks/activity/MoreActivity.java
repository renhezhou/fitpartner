package rxh.shanks.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MoreActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.clear_application_cache)
    RelativeLayout clear_application_cache;
    @Bind(R.id.venue_agreement)
    RelativeLayout venue_agreement;
    @Bind(R.id.switching_venues)
    RelativeLayout switching_venues;
    @Bind(R.id.feedback)
    RelativeLayout feedback;
    @Bind(R.id.exit_login)
    Button exit_login;
    @Bind(R.id.modify_login_password)
    RelativeLayout modify_login_password;
    @Bind(R.id.modify_pay_password)
    RelativeLayout modify_pay_password;
    @Bind(R.id.forget_pay_password)
    RelativeLayout forget_pay_password;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user_info", 0);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        title.setText("更多");
        clear_application_cache.setOnClickListener(this);
        venue_agreement.setOnClickListener(this);
        switching_venues.setOnClickListener(this);
        modify_login_password.setOnClickListener(this);
        modify_pay_password.setOnClickListener(this);
        forget_pay_password.setOnClickListener(this);
        feedback.setOnClickListener(this);
        exit_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_application_cache:
                break;
            case R.id.venue_agreement:
                startActivity(new Intent(getApplicationContext(), VenueAgreementActivity.class));
                break;
            case R.id.switching_venues:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SwitchingVenuesActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_login_password:
                startActivity(new Intent(getApplicationContext(), ModifyLoginPasswordActivity.class));
                break;
            case R.id.modify_pay_password:
                startActivity(new Intent(getApplicationContext(), ModifyPayPasswordActivity.class));
                break;
            case R.id.forget_pay_password:
                startActivity(new Intent(getApplicationContext(), ForgetPayPasswordActivity.class));
                break;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
                break;
            case R.id.exit_login:
                new AlertDialog.Builder(this)
                        .setTitle("退出登录")
                        .setMessage("确定退出登录？")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("token", null);
                                editor.putString("user", null);
                                editor.putString("password", null);
                                editor.commit();
                                application.finishAll();
                                Intent login_intent = new Intent();
                                login_intent.setClass(getApplicationContext(), LoginActivity.class);
                                startActivity(login_intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        }).show();
                break;
            default:
                break;
        }
    }
}
