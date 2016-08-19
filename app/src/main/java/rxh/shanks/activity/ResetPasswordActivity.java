package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.ResetPasswordPresenter;
import rxh.shanks.view.ResetPasswordView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class ResetPasswordActivity extends BaseActivity implements ResetPasswordView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.new_password)
    EditText new_password;
    @Bind(R.id.reset_new_password)
    EditText reset_new_password;
    @Bind(R.id.reset_password)
    Button reset_password;
    ResetPasswordPresenter resetPasswordPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPasswordPresenter = new ResetPasswordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        title.setText("重置密码");
        back.setOnClickListener(this);
        reset_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.reset_password:
                resetPasswordPresenter.resetPassword(new_password.getText().toString(), reset_new_password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("提交中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void onSuccess(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onError(Throwable ex) {

    }

    @Override
    public void go() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }
}
