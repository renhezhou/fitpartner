package rxh.shanks.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.ModifyPayPasswordPresenter;
import rxh.shanks.view.ModifyPayPasswordView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ModifyPayPasswordActivity extends BaseActivity implements ModifyPayPasswordView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.old_pay_password)
    EditText old_pay_password;
    @Bind(R.id.new_pay_password)
    EditText new_pay_password;
    @Bind(R.id.reset_new_pay_password)
    EditText reset_new_pay_password;
    @Bind(R.id.sure)
    Button sure;

    ModifyPayPasswordPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ModifyPayPasswordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_modify_pay_password);
        ButterKnife.bind(this);
        title.setText("修改支付密码");
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                presenter.modifypaypassword(old_pay_password.getText().toString(), new_pay_password.getText().toString(), reset_new_pay_password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("修改中...", "true");
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
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("支付密码修改成功。请妥善保管。")
                .setCancelable(false)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        finish();
                    }
                }).show();
    }
}
