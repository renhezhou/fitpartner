package rxh.shanks.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.hb.banner.ConvenientBanner;
import rxh.hb.banner.holder.CBViewHolderCreator;
import rxh.hb.banner.holder.Holder;
import rxh.hb.banner.listener.OnItemClickListener;
import rxh.shanks.activity.DonationCardActivity;
import rxh.shanks.activity.R;
import rxh.shanks.entity.MembershipCardCodeEventBusEntity;
import rxh.shanks.entity.MembershipCardEntity;
import rxh.shanks.entity.MembershipCardEventBusEntity;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MembershipCardFragment extends Fragment {

    private View view;
    ConvenientBanner convenientBanner;
    private List<MembershipCardEntity> datas = new ArrayList<>();
    LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenient_banner);
        convenientBanner.setCanLoop(false);
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    public void onEventMainThread(MembershipCardEventBusEntity membershipCardEventBusEntity) {
        datas = membershipCardEventBusEntity.getMembershipCardEntityList();
        initview();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void initview() {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas)
                //设置翻页指示器
                .setPageIndicator(new int[]{R.drawable.lunbozhishiqi, R.drawable.lunbozhishiqione})
                //设置翻页指示器位置
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    public class NetworkImageHolderView implements Holder<MembershipCardEntity> {

        private View item_view;
        //donation_card转赠次卡
        private LinearLayout donation_card;
        private ImageView flag;
        private TextView club_name, set_default_card, due_time, totalCount, remaindCount, remainingTimeOff, card_renewal;

        @Override
        public View createView(Context context) {
            mInflater = LayoutInflater.from(context);
            item_view = mInflater.inflate(R.layout.activity_membership_card_item, null);
            flag = (ImageView) item_view.findViewById(R.id.flag);
            donation_card = (LinearLayout) item_view.findViewById(R.id.donation_card);
            club_name = (TextView) item_view.findViewById(R.id.club_name);
            set_default_card = (TextView) item_view.findViewById(R.id.set_default_card);
            due_time = (TextView) item_view.findViewById(R.id.due_time);
            totalCount = (TextView) item_view.findViewById(R.id.totalCount);
            remaindCount = (TextView) item_view.findViewById(R.id.remaindCount);
            remainingTimeOff = (TextView) item_view.findViewById(R.id.remainingTimeOff);
            card_renewal = (TextView) item_view.findViewById(R.id.card_renewal);
            return item_view;
        }

        @Override
        public void UpdateUI(Context context, final int position, final MembershipCardEntity data) {
            if (datas.get(position).getType().equals("1")) {
                //表示是次卡
                remaindCount.setText("总次卡数:" + datas.get(position).getTotalCount() + "次");
                remainingTimeOff.setText("剩余:" + datas.get(position).getRemaindCount() + "次");
                card_renewal.setVisibility(View.GONE);
            } else {
                donation_card.setVisibility(View.GONE);
            }
            //首选会员卡判断
            if (MyApplication.defaultmembercard.equals(datas.get(position).getCardID())) {
                //此卡为首选会员卡
                set_default_card.setVisibility(View.GONE);
            } else {
                flag.setVisibility(View.GONE);
            }
            club_name.setText(datas.get(position).getClubName());
            due_time.setText("会员到期日期:" + CheckUtils.timetodate(datas.get(position).getValidiPeriod()));
            String endday = CheckUtils.timetodate(datas.get(position).getValidiPeriod());
            try {
                totalCount.setText("剩余天数:" + CreatTime.test(endday) + "天");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            card_renewal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳到续卡界面
//                    startActivity(new Intent(getActivity(), MembershipCardRenewalActivity.class));
                    Toast.makeText(getActivity(), "功能未开通，敬请期待", Toast.LENGTH_LONG).show();
                }
            });
            set_default_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置首选会员卡调用EventBus.getDefault().post(new MembershipCardCodeEventBusEntity(cardID));
                    EventBus.getDefault().post(new MembershipCardCodeEventBusEntity(datas.get(position).getCardID()));
                }
            });
            donation_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DonationCardActivity.class);
                    intent.putExtra("cardID", datas.get(position).getCardID());
                    intent.putExtra("clubID", datas.get(position).getClubID());
                    intent.putExtra("cardType", datas.get(position).getType());
                    intent.putExtra("totalCount", datas.get(position).getTotalCount());
                    startActivity(intent);
                }
            });
        }

    }

}
