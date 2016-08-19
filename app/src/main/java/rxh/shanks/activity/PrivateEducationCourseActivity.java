package rxh.shanks.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.PrivateEducationCoursePageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeEntity;
import rxh.shanks.fragment.CalendarFragment;
import rxh.shanks.fragment.PrivateEducationCourseFragment;
import rxh.shanks.presenter.PrivateEducationCoursePresenter;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.PrivateEducationCourseView;

/**
 * Created by Administrator on 2016/8/3.
 * 预约私教课程
 */
public class PrivateEducationCourseActivity extends BaseActivity implements CalendarFragment.CalendarFragmentListener, PrivateEducationCourseFragment.PrivateEducationCourseFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.coach_name)
    TextView coach_name;
    @Bind(R.id.coaching_years)
    TextView coaching_years;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.coach_ratingbar)
    RatingBar coach_ratingbar;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.calendar)
    ImageView calendar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    String coachID, coachName, head_path, evaluate, club_name_tv, when_long;
    CalendarFragment calendarFragment;
    PrivateEducationCoursePageAdapter privateEducationCoursePageAdapter;
    private List<String> datelist = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coachID = getIntent().getStringExtra("coachID");
        MyApplication.CoachID = coachID;
        coachName = getIntent().getStringExtra("coachName");
        head_path = getIntent().getStringExtra("head_path");
        evaluate = getIntent().getStringExtra("evaluate");
        club_name_tv = getIntent().getStringExtra("club_name");
        when_long = getIntent().getStringExtra("when_long");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_private_education_course);
        ButterKnife.bind(this);
        title.setText("预约私教课程");
        datelist = CreatTime.creat(new Date());
        fragmentdatelist = CreatTime.creatfragment(new Date());
        privateEducationCoursePageAdapter = new PrivateEducationCoursePageAdapter(getSupportFragmentManager(), this, datelist, fragmentdatelist);
        viewpager.setAdapter(privateEducationCoursePageAdapter);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
        back.setOnClickListener(this);
        calendar.setOnClickListener(this);
        initdata();
    }

    public void initdata() {
        Glide
                .with(getApplicationContext())
                .load(head_path)
                .centerCrop()
                .crossFade()
                .into(head_portrait);
        coach_name.setText(coachName);
        coach_ratingbar.setStar(Float.parseFloat(evaluate));
        club_name.setText(club_name_tv);
        coaching_years.setText("执教" + when_long + "年");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.calendar:
                showcalendar();
                break;
            default:
                break;
        }
    }

    public void showcalendar() {
        if (calendarFragment == null) {
            calendarFragment = new CalendarFragment();
        }
        calendarFragment.show(getSupportFragmentManager(), null);
    }

    //选中了日历日期触发此事件
    @Override
    public void checkdate(String date) {
        datelist.clear();
        fragmentdatelist.clear();
        datelist = CreatTime.creat(CreatTime.conversion(date));
        fragmentdatelist = CreatTime.creatfragment(CreatTime.conversion(date));
        privateEducationCoursePageAdapter = new PrivateEducationCoursePageAdapter(getSupportFragmentManager(), this, datelist, fragmentdatelist);
        viewpager.setAdapter(privateEducationCoursePageAdapter);
    }

    //预约私教课成功的回调
    @Override
    public void ConfirmAnAppointment() {

    }
}
