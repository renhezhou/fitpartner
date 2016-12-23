package rxh.shanks.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
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
import rxh.shanks.utils.ChangeUtils;
import rxh.shanks.utils.MyApplication;
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
    CheckToPresenter presenter;

    Bitmap mBitmap;
    private TimeCount time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CheckToPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_check_to);
        ButterKnife.bind(this);
        title.setText("签到");
        time = new TimeCount(30000, 1000);// 构造CountDownTimer对象
        back.setOnClickListener(this);
        click_refresh.setOnClickListener(this);
        presenter.getFitCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.click_refresh:
                time.start();
                presenter.getFitCard();
                break;
            default:
                break;
        }
    }

    @Override
    public void show(int flag) {
        if (flag == 0) {
            loading("获取信息中...", "true");
        } else if (flag == 1) {
            loading("开卡中...", "true");
        } else if (flag == 2) {
            loading("二维码生成中...", "true");
        }
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
    public void handleCardSuccess(String cardID, String cardType) {
        presenter.generatedQR(cardID, cardType);
    }

    @Override
    public void handleCard(final String cardID, final String cardType) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("你还未开卡，是否提前开卡？")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int j) {
                        dismiss();
                        presenter.handleCard(cardID, cardType);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        finish();
                    }
                }).show();
    }

    @Override
    public void onFinished(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getFitCard(final List<FitCardEntity> fitCardEntityList) {
        for (int i = 0; i < fitCardEntityList.size(); i++) {
            if (fitCardEntityList.get(i).getCardID().equals(MyApplication.defaultmembercard)) {
                if (fitCardEntityList.get(i).getCardState().equals("1")) {
                    presenter.generatedQR(fitCardEntityList.get(i).getCardID(), fitCardEntityList.get(i).getType());
                } else if (fitCardEntityList.get(i).getCardState().equals("7")) {
                    final int finalI = i;
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("你还未开卡，是否提前开卡？")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int j) {
                                    dismiss();
                                    presenter.handleCard(fitCardEntityList.get(finalI).getCardID(), fitCardEntityList.get(finalI).getType());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dismiss();
                                    finish();
                                }
                            }).show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("你好，你的卡处于" + ChangeUtils.get_card_type(fitCardEntityList.get(i).getCardState()) + "状态，暂时不能签到")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int j) {
                                    dismiss();
                                    finish();
                                }
                            }).show();
                }
            }
        }
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            click_refresh.setText("点击刷新");
            click_refresh.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            click_refresh.setClickable(false);
            click_refresh.setText(millisUntilFinished / 1000 + "秒有效");
        }

    }

}
