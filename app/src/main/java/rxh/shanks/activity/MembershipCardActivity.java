package rxh.shanks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.hb.banner.ConvenientBanner;
import rxh.hb.banner.holder.CBViewHolderCreator;
import rxh.hb.banner.holder.Holder;
import rxh.hb.banner.listener.OnItemClickListener;
import rxh.shanks.EBEntity.DCEntity;
import rxh.shanks.adapter.MembershipCardLVAdapter;
import rxh.shanks.adapter.MembershipCardPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.MembershipCardCodeEventBusEntity;
import rxh.shanks.entity.MembershipCardEntity;
import rxh.shanks.entity.MembershipCardEventBusEntity;
import rxh.shanks.presenter.MembershipCardPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.ZoomOutPageTransformer;
import rxh.shanks.view.MembershipCardView;

/**
 * Created by Administrator on 2016/8/2.
 * 会员卡
 */
public class MembershipCardActivity extends BaseActivity implements MembershipCardView, ViewPager.OnPageChangeListener, MembershipCardPageAdapter.OnItemClickLitener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.viewpage)
    ViewPager viewpage;
    @Bind(R.id.lv)
    ListView lv;

    View headView;
    TextView details, support_venues, card_renewal;

    int selected_position = 0;
    LayoutInflater mInflater;
    MembershipCardLVAdapter adapter;
    MembershipCardPresenter presenter;
    List<MembershipCardEntity> datas = new ArrayList<>();

    MembershipCardPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        presenter = new MembershipCardPresenter(this);
        presenter.getMembershipCard();
    }

    public void onEventMainThread(DCEntity entity) {
        presenter.getMembershipCard();
    }

    public void initview() {
        setContentView(R.layout.activity_membership_card);
        headView = mInflater.inflate(R.layout.activity_membership_card_lv_head, null);
        ButterKnife.bind(this);
        details = (TextView) headView.findViewById(R.id.details);
        support_venues = (TextView) headView.findViewById(R.id.support_venues);
        card_renewal = (TextView) headView.findViewById(R.id.card_renewal);
        lv.addHeaderView(headView);
        back.setOnClickListener(this);
        card_renewal.setOnClickListener(this);
        title.setText("会员卡");
        pageAdapter = new MembershipCardPageAdapter(getApplicationContext(), datas);
        viewpage.setAdapter(pageAdapter);
        pageAdapter.setOnItemClickLitener(this);
        viewpage.setOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(datas.size());
        viewpage.setPageTransformer(true, new ZoomOutPageTransformer());
        initdata();
    }

    public void initdata() {
        //0 时间卡，1 次卡，2 储值卡
        if (datas.get(selected_position).getCardType().equals("0")) {
            card_renewal.setVisibility(View.VISIBLE);
            details.setText(datas.get(selected_position).getCardDescripton());
            String support_club = "";
            for (int i = 0; i < datas.get(selected_position).getSupportCludNames().size(); i++) {
                support_club = support_club + "  " + datas.get(selected_position).getSupportCludNames().get(i);
            }
            support_venues.setText("支持场馆\n" + support_club);
        } else if (datas.get(selected_position).getCardType().equals("1")) {
            card_renewal.setVisibility(View.GONE);
            details.setText(datas.get(selected_position).getCardDescripton());
            String support_club = "";
            for (int i = 0; i < datas.get(selected_position).getSupportCludNames().size(); i++) {
                support_club = support_club + "  " + datas.get(selected_position).getSupportCludNames().get(i);
            }
            support_venues.setText("支持场馆\n" + support_club);
        } else if (datas.get(selected_position).getCardType().equals("2")) {
            card_renewal.setVisibility(View.GONE);
            details.setText(datas.get(selected_position).getCardDescripton());
            String support_club = "";
            for (int i = 0; i < datas.get(selected_position).getSupportCludNames().size(); i++) {
                support_club = support_club + "  " + datas.get(selected_position).getSupportCludNames().get(i);
            }
            support_venues.setText("支持场馆\n" + support_club);
        }
        adapter = new MembershipCardLVAdapter(getApplicationContext(), datas.get(selected_position).getUsedHistory(), datas.get(selected_position).getCardType());
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.card_renewal:
                //跳到续卡界面
                //startActivity(new Intent(getActivity(), MembershipCardRenewalActivity.class));
                Toast.makeText(getApplicationContext(), "功能未开通，敬请期待", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("修改中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getFitCard(List<MembershipCardEntity> membershipCardEntityList) {
        datas.clear();
        datas.addAll(membershipCardEntityList);
        initview();
    }

    @Override
    public void setDefaultCard() {
        presenter.getMembershipCard();
        Toast.makeText(getApplicationContext(), "首选会员卡修改成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void error() {
        r_load.setText("服务器或网络异常");
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.setDefaultCard(datas.get(position).getCardID());
    }

    //viewpage滑动切换监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (rl != null) {
            rl.invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        selected_position = position;
        initdata();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
