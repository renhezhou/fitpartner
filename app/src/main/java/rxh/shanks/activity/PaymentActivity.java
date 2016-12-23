package rxh.shanks.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.EBEntity.PEntity;
import rxh.shanks.adapter.PaymentStoredValueCardListLVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.PswInputView;
import rxh.shanks.entity.PaymentEntity;
import rxh.shanks.entity.PaymentStoredValueCardListEntity;
import rxh.shanks.presenter.PaymentPresenter;
import rxh.shanks.view.PaymentView;

/**
 * Created by Administrator on 2016/12/6.
 * 付款
 */
public class PaymentActivity extends BaseActivity implements PaymentView, PopupWindow.OnDismissListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.QR_code)
    ImageView QR_code;
    @Bind(R.id.click_refresh)
    Button click_refresh;

    View view, card_list_pv, input_pay_pw;
    private PopupWindow card_list_pw;
    private TextView cancel, card_name, payment_amount;
    private ListView lv;
    private Button confirm_payment;
    private PswInputView ppw_input_view;
    PaymentStoredValueCardListLVAdapter adapter;
    private List<PaymentStoredValueCardListEntity> data = new ArrayList<>();

    String pay_id = null, pay_card_id = null, pay_card_name = null, pay_price = null;
    Bitmap mBitmap;
    private TimeCount time;
    PaymentPresenter presenter;
    PaymentEntity paymentEntity = new PaymentEntity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PaymentPresenter(this);
        initview();
        presenter.generatedPaidQR();
    }

    public void onEventMainThread(PEntity entity) {
        if (entity.getFlag() == 0) {
            pay_id = entity.getId();
            pay_price = entity.getPrice();
            presenter.getpaidcard(pay_id);
        } else if (entity.getFlag() == 1) {
            confirm_payment.setText("重新支付");
        }
    }

    public void initview() {
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        view = findViewById(R.id.parthen);
        title.setText("付款");
        time = new TimeCount(30000, 1000);// 构造CountDownTimer对象
        back.setOnClickListener(this);
        click_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.click_refresh:
                time.start();
                break;
            case R.id.cancel:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确认退出本次支付?")
                        .setCancelable(false)
                        .setPositiveButton("继续支付", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dismiss();
                            }
                        })
                        .setNegativeButton("退出支付", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                card_list_pw.dismiss();
                                dismiss();
                                finish();
                            }
                        }).show();
                break;
            case R.id.confirm_payment:
                show_input_pay_password();
                break;
            default:
                break;
        }
    }

    @Override
    public void show(int flag) {
        if (flag == 0) {
            loading("获取支付码中...");
        } else if (flag == 1) {
            loading("获取可支付存值卡...");
        } else if (flag == 2) {
            loading("支付中...");
        } else if (flag == 3) {
            loading("获取支付信息...");
        }
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
    public void generatedPaidQR(PaymentEntity entity) {
        paymentEntity = entity;
        if (paymentEntity.getPwdInited().equals("0")) {
            //未设置
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("尊敬的用户，你还未设置支付密码，无法使用储值卡支付。请先设置支付密码。")
                    .setCancelable(false)
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            startActivityForResult(new Intent(getApplicationContext(), SetPayPasswordActivity.class), 0);
                        }
                    })
                    .setNegativeButton("退出支付", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        } else if (paymentEntity.getPwdInited().equals("1")) {
            //已设置
            mBitmap = CodeUtils.createImage(paymentEntity.getQr(), 400, 400, null);
            QR_code.setImageBitmap(mBitmap);
        }

    }

    @Override
    public void getpaidcard(List<PaymentStoredValueCardListEntity> entities) {
        show_selected_card();
        backgroundAlpha(0.5f);
        card_list_pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        data.clear();
        data.addAll(entities);
        adapter = new PaymentStoredValueCardListLVAdapter(getApplicationContext(), data);
        lv.setAdapter(adapter);
    }

    @Override
    public void get_pay_info(String price) {
        lv.setVisibility(View.GONE);
        card_name.setText(pay_card_name);
        payment_amount.setText(price);
    }

    @Override
    public void pay_confirm() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("支付成功!")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mBitmap = CodeUtils.createImage(paymentEntity.getQr(), 400, 400, null);
            QR_code.setImageBitmap(mBitmap);
        } else if (requestCode == 0 && resultCode == RESULT_CANCELED) {
            finish();
        } else {
            finish();
        }
    }

    public void show_selected_card() {
        card_list_pv = LayoutInflater.from(PaymentActivity.this).inflate(R.layout.activity_payment_stored_value_card_list, null);
        card_list_pw = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        cancel = (TextView) card_list_pv.findViewById(R.id.cancel);
        lv = (ListView) card_list_pv.findViewById(R.id.lv);
        card_name = (TextView) card_list_pv.findViewById(R.id.card_name);
        payment_amount = (TextView) card_list_pv.findViewById(R.id.payment_amount);
        confirm_payment = (Button) card_list_pv.findViewById(R.id.confirm_payment);
        card_list_pw = new PopupWindow(card_list_pv, ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300));
        card_list_pw.setFocusable(true);
        card_list_pw.setOutsideTouchable(false);
        card_list_pw.setOnDismissListener(this);
        cancel.setOnClickListener(this);
        confirm_payment.setOnClickListener(this);
        adapter = new PaymentStoredValueCardListLVAdapter(getApplicationContext(), data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pay_card_id = data.get(position).getCardID();
                pay_card_name = data.get(position).getCardName();
                presenter.get_pay_info(pay_id, data.get(position).getCardID());
            }
        });
    }

    //输入支付密码的弹窗
    public void show_input_pay_password() {
        input_pay_pw = getLayoutInflater().inflate(R.layout.activity_payment_input_pay_password, null);
        new AlertDialog.Builder(this)
                .setTitle("支付密码输入")
                .setView(input_pay_pw)
                .setCancelable(false)
                .setPositiveButton("取消支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("忘记了密码？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), ForgetPayPasswordActivity.class));
                    }
                }).show();
        ppw_input_view = (PswInputView) input_pay_pw.findViewById(R.id.ppw_input_view);
        ppw_input_view.setInputCallBack(new PswInputView.InputCallBack() {
            @Override
            public void onInputFinish(String result) {
                dismiss();
                ppw_input_view.clearResult();
                presenter.pay_confirm(pay_id, pay_card_id, result);
            }
        });
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

    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }
}
