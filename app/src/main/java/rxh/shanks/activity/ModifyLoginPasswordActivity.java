package rxh.shanks.activity;

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
import rxh.shanks.presenter.ModifyLoginPasswordPresenter;
import rxh.shanks.view.ModifyLoginPasswordView;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ModifyLoginPasswordActivity extends BaseActivity implements ModifyLoginPasswordView {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.old_password)
    EditText old_password;
    @Bind(R.id.new_password)
    EditText new_password;
    @Bind(R.id.reset_new_password)
    EditText reset_new_password;
    @Bind(R.id.sure)
    Button sure;

    ModifyLoginPasswordPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ModifyLoginPasswordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_modify_login_password);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
        title.setText("修改登录密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                presenter.modifyloginpassword(old_password.getText().toString(), new_password.getText().toString(), reset_new_password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("修改中", "true");
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
        finish();
    }
}
