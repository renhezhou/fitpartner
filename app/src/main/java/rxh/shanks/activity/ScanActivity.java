package rxh.shanks.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.ScanCodeGetGymEntity;
import rxh.shanks.presenter.ScanPresenter;
import rxh.shanks.view.ScanView;

/**
 * Created by Administrator on 2016/8/4.
 * 扫一扫界面
 */
public class ScanActivity extends BaseActivity implements CodeUtils.AnalyzeCallback, ScanView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;

    String qrString;
    ScanPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScanPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        title.setText("扫码");
        back.setOnClickListener(this);
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        // CodeUtils.setFragmentArgs(captureFragment, R.id.fl_my_container);
        captureFragment.setAnalyzeCallback(this);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {

        loading(" 获取中", "true");

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
    public void get_gymInfo(ScanCodeGetGymEntity entity) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ScanNextActivity.class);
        intent.putExtra("qrString", qrString);
        intent.putExtra("logo", entity.getLogo());
        intent.putExtra("gymname", entity.getGymName());
        intent.putExtra("isin", entity.getIsIn());
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        try {
            JSONObject obj = JSON.parseObject(result);
            String type = obj.getString("type");
            if (type.equals("gym")) {
                qrString = obj.getString("qrString");
                presenter.get_gymInfo(qrString);
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "不被识别的二维码", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onAnalyzeFailed() {
        Toast.makeText(getApplicationContext(), "无法识别", Toast.LENGTH_LONG).show();
    }
}
