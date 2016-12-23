package rxh.shanks.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.MANEntity;
import rxh.shanks.adapter.PrivateEducationCoursePageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.NotMakeAnAppointmentEventBusEntity;
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

    String coachID, lessonID, coachName, head_path, evaluate, club_name_tv, when_long, time;
    CalendarFragment calendarFragment;
    PrivateEducationCoursePageAdapter pageAdapter;
    private List<String> datelist = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coachID = getIntent().getStringExtra("coachID");
        MyApplication.CoachID = coachID;
        lessonID = getIntent().getStringExtra("lessonID");
        coachName = getIntent().getStringExtra("coachName");
        head_path = getIntent().getStringExtra("head_path");
        evaluate = getIntent().getStringExtra("evaluate");
        club_name_tv = getIntent().getStringExtra("club_name");
        when_long = getIntent().getStringExtra("when_long");
        time = getIntent().getStringExtra("time");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_private_education_course);
        ButterKnife.bind(this);
        title.setText("预约私教课程");
        datelist = CreatTime.creat(new Date());
        fragmentdatelist = CreatTime.creatfragment(new Date());
        pageAdapter = new PrivateEducationCoursePageAdapter(getSupportFragmentManager(), datelist, fragmentdatelist, lessonID, time);
        viewpager.setAdapter(pageAdapter);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
        back.setOnClickListener(this);
        calendar.setOnClickListener(this);
        initdata();
    }

    public void initdata() {
        Picasso.with(getApplicationContext())
                .load(head_path)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        coach_name.setText(coachName);
        if (evaluate == null) {
            coach_ratingbar.setStar(0f);
        } else {
            coach_ratingbar.setStar(Float.parseFloat(evaluate));
        }
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
        pageAdapter = new PrivateEducationCoursePageAdapter(getSupportFragmentManager(), datelist, fragmentdatelist, lessonID, time);
        viewpager.setAdapter(pageAdapter);
    }

    //预约私教课成功的回调
    @Override
    public void ConfirmAnAppointment() {
        new AlertDialog.Builder(this)
                .setTitle("预约成功")
                .setMessage("你已成功预约该私教课程。请准时前往上课")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        finish();
                    }
                }).show();
        //用eb发送消息到上一级界面通知数据更新
        EventBus.getDefault().post(new MANEntity());
    }
}
