package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.ScanNextPresenter;
import rxh.shanks.view.ScanNextView;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ScanNextActivity extends BaseActivity implements ScanNextView {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.gym_name)
    TextView gym_name;
    @Bind(R.id.sign)
    Button sign;

    String qrString, logo, gymname, isin;

    ScanNextPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScanNextPresenter(this);
        initview();
        initdata();
    }

    public void initview() {
        setContentView(R.layout.activity_scan_next);
        ButterKnife.bind(this);
        title.setText("签到");
        back.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    public void initdata() {
        qrString = getIntent().getStringExtra("qrString");
        logo = getIntent().getStringExtra("logo");
        gymname = getIntent().getStringExtra("gymname");
        isin = getIntent().getStringExtra("isin");
        Picasso.with(getApplicationContext())
                .load(logo)
                .placeholder(R.drawable.loading_cort)
                .into(img);
        gym_name.setText(gymname);
        if (isin.equals("1")) {//1代表已在馆，0代表未在馆
            sign.setText("签出");
        } else {
            sign.setText("签入");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sign:
                presenter.scan(qrString);
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("签到中", "true");
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
