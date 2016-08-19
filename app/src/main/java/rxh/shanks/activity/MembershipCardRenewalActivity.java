package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.SmoothCheckBox;

/**
 * Created by Administrator on 2016/8/2.
 * 会员卡续费
 */
public class MembershipCardRenewalActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.big_annual_fee_checkbox)
    SmoothCheckBox big_annual_fee_checkbox;
    @Bind(R.id.big_annual_fee_tv)
    TextView big_annual_fee_tv;
    @Bind(R.id.big_annual_fee_details)
    TextView big_annual_fee_details;
    @Bind(R.id.small_annual_fee_check_box)
    SmoothCheckBox small_annual_fee_checkbox;
    @Bind(R.id.small_annual_fee_tv)
    TextView small_annual_fee_tv;
    @Bind(R.id.small_annual_fee_details)
    TextView small_annual_fee_details;
    @Bind(R.id.pay_immediately)
    TextView pay_immediately;
    @Bind(R.id.security_protocol_checkbox)
    SmoothCheckBox security_protocol_checkbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_membership_card_renewal);
        ButterKnife.bind(this);
        title.setText("支付订单");
        back.setOnClickListener(this);
        pay_immediately.setOnClickListener(this);
        big_annual_fee_checkbox.setChecked(true);
        security_protocol_checkbox.setChecked(true);
        big_annual_fee_details.setOnClickListener(this);
        small_annual_fee_details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pay_immediately:
                startActivity(new Intent(getApplicationContext(), PaymentOrderActivity.class));
                break;
            case R.id.big_annual_fee_details:
                startActivity(new Intent(getApplicationContext(),MembershipCardDetailsActivity.class));
                break;
            case R.id.small_annual_fee_details:
                startActivity(new Intent(getApplicationContext(),MembershipCardDetailsActivity.class));
                break;
            default:
                break;
        }
    }
}
