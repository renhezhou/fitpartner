package rxh.shanks.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import rxh.shanks.activity.CouponActivity;
import rxh.shanks.activity.FitnessCalendarActivity;
import rxh.shanks.activity.InformationActivity;
import rxh.shanks.activity.MembershipCardActivity;
import rxh.shanks.activity.MoreActivity;
import rxh.shanks.activity.MyIntegralActivity;
import rxh.shanks.activity.MyPrivateEducationActivity;
import rxh.shanks.activity.PersonalInformationEditorActivity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.TestRecordActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.eventity.PIEEBEntity;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyFragment extends Fragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

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

    private SwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
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

    public void onEventMainThread(PIEEBEntity pieebEntity) {
        if (pieebEntity.getImg_path() != null) {
            Glide.with(getActivity())
                    .load(pieebEntity.getImg_path())
                    .centerCrop()
                    .into(head_portrait);
        }
        name.setText(pieebEntity.getUsername());
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        more.setOnClickListener(this);
        information.setOnClickListener(this);
        head_portrait.setOnClickListener(this);
        fitness_calendar.setOnClickListener(this);
        test_record.setOnClickListener(this);
        membership_card.setOnClickListener(this);
        coupon.setOnClickListener(this);
        my_private_education.setOnClickListener(this);
        my_league.setOnClickListener(this);
    }

    public void initdata() {
        Picasso.with(getActivity())
                .load(MyApplication.QNDownToken)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        name.setText(MyApplication.nickName);
        club_name.setText(MyApplication.currentClubName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MoreActivity.class);
                startActivity(intent);
                break;
            case R.id.information:
                //startActivity(new Intent(getActivity(), InformationActivity.class));
                //启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
                break;
            case R.id.head_portrait:
                startActivity(new Intent(getActivity(), PersonalInformationEditorActivity.class));
                // startActivity(new Intent(getActivity(), MyIntegralActivity.class));
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
                intent1.setClass(getActivity(), MyPrivateEducationActivity.class);
                intent1.putExtra("flag", "1");
                startActivity(intent1);
                break;
            case R.id.my_league:
                //我的团课和我的私教界面完全相同，故复用
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), MyPrivateEducationActivity.class);
                intent2.putExtra("flag", "0");
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        //initdata();
        swipeToLoadLayout.setRefreshing(false);
    }
}
