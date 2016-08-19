package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.MembershipCardCodeEventBusEntity;
import rxh.shanks.entity.MembershipCardEntity;
import rxh.shanks.entity.MembershipCardEventBusEntity;
import rxh.shanks.entity.TestRecordEventBusEntity;
import rxh.shanks.presenter.MembershipCardPresenter;
import rxh.shanks.view.MembershipCardView;

/**
 * Created by Administrator on 2016/8/2.
 * 会员卡
 */
public class MembershipCardActivity extends BaseActivity implements MembershipCardView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    MembershipCardPresenter membershipCardPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        membershipCardPresenter = new MembershipCardPresenter(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void onEventMainThread(MembershipCardCodeEventBusEntity membershipCardCodeEventBusEntity) {
        membershipCardPresenter.setDefaultCard(membershipCardCodeEventBusEntity.getCardID());
    }

    public void initview() {
        setContentView(R.layout.activity_membership_card);
        ButterKnife.bind(this);
        title.setText("会员卡");
        back.setOnClickListener(this);
        membershipCardPresenter.getFitCard();
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
        loading("加载中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getFitCard(List<MembershipCardEntity> membershipCardEntityList) {
        EventBus.getDefault().post(new MembershipCardEventBusEntity(membershipCardEntityList));
    }

    @Override
    public void setDefaultCard() {
        membershipCardPresenter.getFitCard();
        Toast.makeText(getApplicationContext(), "首选会员卡修改成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
