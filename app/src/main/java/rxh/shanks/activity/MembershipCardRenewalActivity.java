package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.MembershipCardRenewalLVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.SmoothCheckBox;
import rxh.shanks.entity.MembershipCardRenewalEntity;

/**
 * Created by Administrator on 2016/8/2.
 * 会员卡续费
 */
public class MembershipCardRenewalActivity extends BaseActivity implements MembershipCardRenewalLVAdapter.item_selected {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.pay_immediately)
    TextView pay_immediately;
    @Bind(R.id.sure)
    SmoothCheckBox sure;
    @Bind(R.id.lv)
    ListView lv;

    List<MembershipCardRenewalEntity> data = new ArrayList<>();
    MembershipCardRenewalLVAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
    }

    public void initview() {
        setContentView(R.layout.activity_membership_card_renewal);
        ButterKnife.bind(this);
        title.setText("支付订单");
        back.setOnClickListener(this);
        pay_immediately.setOnClickListener(this);
        sure.setChecked(true);
    }

    public void initdata() {
        for (int i = 0; i < 3; i++) {
            MembershipCardRenewalEntity entity = new MembershipCardRenewalEntity();
            entity.setMoney("133美年");
            data.add(entity);
        }
        adapter = new MembershipCardRenewalLVAdapter(getApplicationContext(), data);
        adapter.set_item_selected(this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pay_immediately:
                if (sure.isChecked()) {
                    startActivity(new Intent(getApplicationContext(), PaymentOrderActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "请同意支付协议", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void selected(int flag, int position) {
        if (flag == 0) {
            //0代表checkbox

        } else {
            //1代表详情
            startActivity(new Intent(getApplicationContext(), MembershipCardDetailsActivity.class));
        }
    }
}
