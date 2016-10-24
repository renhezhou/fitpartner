package rxh.shanks.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import rxh.shanks.adapter.MYERVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.entity.MyPrivateEducationEventBusEntity;
import rxh.shanks.entity.MyPrivateEducationHeadEntity;
import rxh.shanks.fragment.AlreadyMakeAnAppointmentFragment;
import rxh.shanks.fragment.NotMakeAnAppointmentFragment;
import rxh.shanks.presenter.MyPrivateEducationPresenter;
import rxh.shanks.view.MyPrivateEducationView;

/**
 * Created by Administrator on 2016/8/2.
 * 我的私教
 */
public class MyPrivateEducationActivity extends BaseActivity implements MyPrivateEducationView, MYERVAdapter.OnItemClickLitener {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.coach_name)
    TextView coach_name;
    @Bind(R.id.coaching_years)
    TextView coaching_years;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.dadianhua)
    ImageView dadianhua;
    @Bind(R.id.faduanxin)
    ImageView faduanxin;
    @Bind(R.id.already_make_an_appointment)
    TextView already_make_an_appointment;
    @Bind(R.id.not_make_an_appointment)
    TextView not_make_an_appointment;
    @Bind(R.id.already_make_an_appointment_view)
    View already_make_an_appointment_view;
    @Bind(R.id.not_make_an_appointment_view)
    View not_make_an_appointment_view;
    @Bind(R.id.viewpage)
    ViewPager viewpage;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    List<Fragment> fragments = new ArrayList<Fragment>();
    //顶部滑动头像
    List<MyPrivateEducationHeadEntity> data = new ArrayList<MyPrivateEducationHeadEntity>();
    public int selectNum = 0;//全局变量，保存被选中的item
    public String flag;//1表示是从我的私教跳转过来的 ，0表示从我的团课跳转过来的
    MYERVAdapter rvAdapter;
    MyPrivateEducationPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyPrivateEducationPresenter(this);
        flag = getIntent().getStringExtra("flag");
        initview();
        if (flag.equals("1")) {
            presenter.getMyPrivateCoach();
        } else {
            presenter.getMyTeamCoach();
        }
    }

    public void initview() {
        setContentView(R.layout.activity_my_private_education);
        ButterKnife.bind(this);
        if (flag.equals("0")) {
            title.setText("我的团课");
        } else {
            title.setText("我的私教");
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(manager);
        back.setOnClickListener(this);
        dadianhua.setOnClickListener(this);
        faduanxin.setOnClickListener(this);
        already_make_an_appointment.setOnClickListener(this);
        not_make_an_appointment.setOnClickListener(this);
        selected(0);
        fragments.add(new AlreadyMakeAnAppointmentFragment());
        fragments.add(new NotMakeAnAppointmentFragment());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        // 设定适配器
        viewpage.setAdapter(adapter);
        viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                selected(arg0);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public void initdata() {
        selectNum = (int) (data.size() / 2);
        rvAdapter = new MYERVAdapter(getApplicationContext(), selectNum, data);
        rvAdapter.setOnItemClickLitener(this);
        recycler_view.setAdapter(rvAdapter);
        coach_name.setText(data.get(selectNum).getName());
        coaching_years.setText("执教" + data.get(selectNum).getTeachTime() + "年");
        club_name.setText(data.get(selectNum).getClubName());
        EventBus.getDefault().post(new MyPrivateEducationEventBusEntity(flag, data.get(selectNum)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.already_make_an_appointment:
                selected(0);
                viewpage.setCurrentItem(0);
                break;
            case R.id.not_make_an_appointment:
                selected(1);
                viewpage.setCurrentItem(1);
                break;
            case R.id.dadianhua:
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + data.get(selectNum).getNumOfServiced()));
                startActivity(intentNO);
                break;
            case R.id.faduanxin:
                //启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(MyPrivateEducationActivity.this, data.get(selectNum).getCoachID(), data.get(selectNum).getName());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("加载中", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getMyPrivateCoach(List<MyPrivateEducationHeadEntity> myPrivateEducationHeadEntityList) {
        data = myPrivateEducationHeadEntityList;
        if (data.size() > 0) {
            initdata();
        } else {
            setContentView(R.layout.activity_my_no_private_education);
            TextView details = (TextView) findViewById(R.id.details);
            title = (TextView) findViewById(R.id.title);
            back = (LinearLayout) findViewById(R.id.back);
            if (flag.equals("0")) {
                title.setText("我的团课");
                details.setText("当前场馆中你暂时还没有团课教练");
            } else {
                title.setText("我的私教");
                details.setText("当前场馆中你暂时还没有私人教练");
            }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        selectNum = position;//
        coach_name.setText(data.get(selectNum).getName());
        coaching_years.setText("执教" + data.get(selectNum).getTeachTime() + "年");
        club_name.setText(data.get(selectNum).getClubName());
        rvAdapter = new MYERVAdapter(getApplicationContext(), selectNum, data);
        rvAdapter.setOnItemClickLitener(this);
        recycler_view.setAdapter(rvAdapter);
        EventBus.getDefault().post(new MyPrivateEducationEventBusEntity(flag, data.get(selectNum)));
    }

    public void selected(int i) {
        if (i == 0) {
            already_make_an_appointment_view.setBackgroundResource(R.color.red);
            already_make_an_appointment_view.setAlpha(0.6f);
            not_make_an_appointment_view.setAlpha(0.6f);
            not_make_an_appointment_view.setBackgroundResource(R.color.text_not_selected);
            already_make_an_appointment.setTextColor(getResources().getColor(R.color.red));
            not_make_an_appointment.setTextColor(getResources().getColor(R.color.black));
        } else if (i == 1) {
            not_make_an_appointment_view.setBackgroundResource(R.color.red);
            already_make_an_appointment_view.setBackgroundResource(R.color.text_not_selected);
            already_make_an_appointment.setTextColor(getResources().getColor(R.color.black));
            already_make_an_appointment_view.setAlpha(0.6f);
            not_make_an_appointment_view.setAlpha(0.6f);
            not_make_an_appointment.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public class FragAdapter extends FragmentPagerAdapter {
        public FragmentManager fm;
        public List<Fragment> list = new ArrayList<Fragment>();


        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.list = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = null;
            fragment = list.get(arg0);
            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            Fragment fragment = list.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }

    }

}
