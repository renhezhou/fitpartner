package rxh.shanks.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.PEntity;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.SetPayPasswordPresenter;
import rxh.shanks.view.SetPayPasswordView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class SetPayPasswordActivity extends BaseActivity implements SetPayPasswordView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.pay_password)
    EditText pay_password;
    @Bind(R.id.reset_pay_password)
    EditText reset_pay_password;
    @Bind(R.id.sure)
    Button sure;

    SetPayPasswordPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SetPayPasswordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_set_pay_password);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
        title.setText("设置支付密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("退出本次支付密码设置，你将无法使用本次支付功能。是否继续退出？")
                        .setCancelable(false)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                setResult(RESULT_CANCELED);
                                finish();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
            case R.id.sure:
                presenter.setpaypassword(pay_password.getText().toString(), reset_pay_password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("设置中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success() {
        EventBus.getDefault().post(new PEntity(1, "", ""));
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("支付密码设置成功。请妥善保管。如需对支付密码进行更改、找回。请到更多模块进行操作。")
                .setCancelable(false)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("退出本次支付密码设置，你将无法使用本次支付功能。是否继续退出？")
                    .setCancelable(false)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
