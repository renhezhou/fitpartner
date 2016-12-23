package rxh.shanks.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rxh.shanks.EBEntity.SVAEntity;
import rxh.shanks.activity.FitnessCalendarActivity;
import rxh.shanks.activity.MembershipCardActivity;
import rxh.shanks.activity.MoreActivity;
import rxh.shanks.activity.MyReservationActivity;
import rxh.shanks.activity.PersonalInformationEditorActivity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.TestRecordActivity;
import rxh.shanks.customview.BlurImageView;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.eventity.PIEEBEntity;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    View view;
    @Bind(R.id.more)
    ImageView more;
    @Bind(R.id.information)
    ImageView information;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.fitness_calendar)
    LinearLayout fitness_calendar;
    @Bind(R.id.test_record)
    LinearLayout test_record;
    @Bind(R.id.membership_card)
    LinearLayout membership_card;
    @Bind(R.id.coupon)
    LinearLayout coupon;
    @Bind(R.id.my_private_education)
    LinearLayout my_private_education;
    @Bind(R.id.my_league)
    LinearLayout my_league;
    @Bind(R.id.rl_bg)
    ImageView rl_bg;
    @Bind(R.id.integral)
    TextView integral;
    @Bind(R.id.integral_exchange)
    TextView integral_exchange;
    @Bind(R.id.notice_num)
    TextView notice_num;

    private SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("user_info", 0);
        EventBus.getDefault().register(this);
        initview();
        initdata();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    //修改了用户信息通知这个界面更新
    public void onEventMainThread(PIEEBEntity pieebEntity) {
        if (pieebEntity.getImg_path() != null) {
            Glide.with(getActivity())
                    .load(pieebEntity.getImg_path())
                    .centerCrop()
                    .into(head_portrait);
        }
        name.setText(pieebEntity.getUsername());
    }

    //切换了场馆通知这个界面更新
    public void onEventMainThread(SVAEntity svaEntity) {
        club_name.setText(MyApplication.currentClubName);
    }

    public void initview() {
        more.setOnClickListener(this);
        information.setOnClickListener(this);
        head_portrait.setOnClickListener(this);
        fitness_calendar.setOnClickListener(this);
        test_record.setOnClickListener(this);
        membership_card.setOnClickListener(this);
        coupon.setOnClickListener(this);
        my_private_education.setOnClickListener(this);
        my_league.setOnClickListener(this);
        integral_exchange.setOnClickListener(this);
    }

    public void initdata() {
        Glide.with(this).load(MyApplication.QNDownToken)
                .bitmapTransform(new BlurTransformation(getActivity()))
                .placeholder(R.drawable.load_mohu)
                .into(rl_bg);
        Picasso.with(getActivity())
                .load(MyApplication.QNDownToken)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        name.setText(MyApplication.nickName);
        club_name.setText(MyApplication.currentClubName);
        //上课提醒
        int RemindLessonToUser_num = sp.getInt("RemindLessonToUser", 0);
        //教练代约团课
        int ReplaceGroupClassToUser_num = sp.getInt("ReplaceGroupClassToUser", 0);
        //教练代约私教课
        int ReplaceOrderLessonToUser_num = sp.getInt("ReplaceOrderLessonToUser", 0);
        //会员卡即将到期
        int CardCloseExpiredToUser_num = sp.getInt("CardCloseExpiredToUser", 0);
        //新增优惠券
        int SendCouponToUser_num = sp.getInt("SendCouponToUser", 0);
        //课程扣除提醒
        int DeductionLessonToUser_num = sp.getInt("DeductionLessonToUser", 0);
        //场馆通知
        int NormalMessageToUser_num = sp.getInt("NormalMessageToUser", 0);
        //场馆活动
        int EventMessageToUser_num = sp.getInt("EventMessageToUser", 0);
        //系统通知
        int DevelopSystemMessage_num = sp.getInt("DevelopSystemMessage", 0);
        int all_notice = RemindLessonToUser_num + ReplaceGroupClassToUser_num + ReplaceOrderLessonToUser_num + CardCloseExpiredToUser_num +
                SendCouponToUser_num + DeductionLessonToUser_num + NormalMessageToUser_num + EventMessageToUser_num + DevelopSystemMessage_num;
        if (all_notice > 0) {
            notice_num.setText(String.valueOf(all_notice));
        } else {
            notice_num.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.more:
                intent = new Intent();
                intent.setClass(getActivity(), MoreActivity.class);
                startActivity(intent);
                break;
            case R.id.information:
                //startActivity(new Intent(getActivity(), InformationActivity.class));
                //启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
                notice_num.setVisibility(View.GONE);
                break;
            case R.id.head_portrait:
                startActivity(new Intent(getActivity(), PersonalInformationEditorActivity.class));
                break;
            case R.id.fitness_calendar:
                startActivity(new Intent(getActivity(), FitnessCalendarActivity.class));
                break;
            case R.id.test_record:
                startActivity(new Intent(getActivity(), TestRecordActivity.class));
                break;
            case R.id.membership_card:
                startActivity(new Intent(getActivity(), MembershipCardActivity.class));
                break;
            case R.id.coupon:
                //startActivity(new Intent(getActivity(), CouponActivity.class));
                Toast.makeText(getActivity(), "功能暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_private_education:
                //我的团课和我的私教界面完全相同，故复用
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), MyReservationActivity.class);
                intent1.putExtra("flag", "1");
                startActivity(intent1);
                break;
            case R.id.my_league:
                //我的团课和我的私教界面完全相同，故复用
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), MyReservationActivity.class);
                intent2.putExtra("flag", "0");
                startActivity(intent2);
                break;
            case R.id.integral_exchange:
                //startActivity(new Intent(getActivity(), MyIntegralActivity.class));
                Toast.makeText(getActivity(), "功能暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
