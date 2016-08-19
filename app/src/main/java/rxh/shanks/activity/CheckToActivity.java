package rxh.shanks.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.FitCardEntity;
import rxh.shanks.presenter.CheckToPresenter;
import rxh.shanks.view.CheckToView;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CheckToActivity extends BaseActivity implements CheckToView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.QR_code)
    ImageView QR_code;
    @Bind(R.id.click_refresh)
    Button click_refresh;
    CheckToPresenter checkToPresenter;

    Bitmap mBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkToPresenter = new CheckToPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_check_to);
        ButterKnife.bind(this);
        title.setText("签到");
        back.setOnClickListener(this);
        click_refresh.setOnClickListener(this);
        checkToPresenter.getFitCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.click_refresh:
                checkToPresenter.getFitCard();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("获取信息中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void generatedQR(String result) {
        mBitmap = CodeUtils.createImage(result, 400, 400, null);
        QR_code.setImageBitmap(mBitmap);
    }

    @Override
    public void onFinished(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getFitCard(List<FitCardEntity> fitCardEntityList) {
        checkToPresenter.generatedQR(fitCardEntityList.get(0).getCardID(),fitCardEntityList.get(0).getType());
    }
}
