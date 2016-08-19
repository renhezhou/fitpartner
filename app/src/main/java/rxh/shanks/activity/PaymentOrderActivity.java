package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class PaymentOrderActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.club_name_category)
    TextView club_name_category;
    @Bind(R.id.payment_amount)
    TextView payment_amount;
    @Bind(R.id.payment_logo)
    ImageView payment_logo;
    @Bind(R.id.payment_method)
    TextView payment_method;
    @Bind(R.id.confirm_payment)
    TextView confirm_payment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_payment_order);
        ButterKnife.bind(this);
        title.setText("支付订单");
        back.setOnClickListener(this);
        confirm_payment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_payment:
                break;
            default:
                break;
        }
    }
}
