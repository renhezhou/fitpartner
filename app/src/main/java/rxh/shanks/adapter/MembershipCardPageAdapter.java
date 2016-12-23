package rxh.shanks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.DonationCardActivity;
import rxh.shanks.activity.R;
import rxh.shanks.entity.MembershipCardEntity;
import rxh.shanks.utils.CheckUtils;

/**
 * Created by Administrator on 2016/12/6.
 */
public class MembershipCardPageAdapter extends PagerAdapter {


    private View view;
    //donation_card转赠次卡
    private ImageView card_state, donation_card;
    private TextView card_name, total_number_of_times, card_type, residual_frequency, surplus, due_time, use_state;
    Context context;
    LayoutInflater mInflater;
    List<MembershipCardEntity> data = new ArrayList<>();

    OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MembershipCardPageAdapter(Context context, List<MembershipCardEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        view = mInflater.inflate(R.layout.activity_membership_card_item, null);
        card_state = (ImageView) view.findViewById(R.id.card_state);
        donation_card = (ImageView) view.findViewById(R.id.donation_card);
        card_name = (TextView) view.findViewById(R.id.card_name);
        total_number_of_times = (TextView) view.findViewById(R.id.total_number_of_times);
        card_type = (TextView) view.findViewById(R.id.card_type);
        residual_frequency = (TextView) view.findViewById(R.id.residual_frequency);
        surplus = (TextView) view.findViewById(R.id.surplus);
        due_time = (TextView) view.findViewById(R.id.due_time);
        use_state = (TextView) view.findViewById(R.id.use_state);
        //判断是否是默认卡
        if (data.get(position).getIsDefault().equals("1")) {
            Picasso.with(context).load(R.drawable.sheweishouxuanka_not_sec).into(card_state);
        } else {
            Picasso.with(context).load(R.drawable.sheweishouxuanka_is_sec).into(card_state);
        }
        //判断是否是次卡
        if (data.get(position).getCardType().equals("1")) {
            donation_card.setVisibility(View.VISIBLE);
            Picasso.with(context).load(R.drawable.zhuanzeng).into(donation_card);
        } else {
            donation_card.setVisibility(View.GONE);
        }
        card_name.setText(data.get(position).getCardName());
        if (data.get(position).getCardType().equals("0")) {
            card_state.setVisibility(View.VISIBLE);
            card_type.setText("时间卡");
            surplus.setText("剩余:" + data.get(position).getSurplusTime() + "天");
            due_time.setText("到期时间\n" + data.get(position).getCardEndTime());
        } else if (data.get(position).getCardType().equals("1")) {
            card_state.setVisibility(View.VISIBLE);
            card_type.setText("次卡");
            surplus.setText("剩余:" + data.get(position).getSurplusTime() + "天");
            due_time.setText("到期时间\n" + data.get(position).getCardEndTime());
            total_number_of_times.setText("总次数：" + data.get(position).getCount() + "次");
            residual_frequency.setText("剩余：" + data.get(position).getSurplusCount() + "次");
        } else if (data.get(position).getCardType().equals("2")) {
            card_state.setVisibility(View.GONE);
            card_type.setText("储值卡");
            surplus.setText("剩余:" + data.get(position).getSurplusPrice() + "元");
        }
        use_state.setText("使用状态\n" + CheckUtils.get_card_state(data.get(position).getCardState()));
        //设为默认卡
        card_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.get(position).getIsDefault().equals("1")) {
                    mOnItemClickLitener.onItemClick(v, position);
                } else {
                    Toast.makeText(context, "此卡已是默认卡", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //转赠次卡
        donation_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DonationCardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cardID", data.get(position).getCardID());
                intent.putExtra("clubID", data.get(position).getClubID());
                intent.putExtra("surplus", data.get(position).getSurplusCount());
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

}
