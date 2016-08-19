package rxh.shanks.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.adapter.ConfirmAnAppointmentPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.ConfirmAnAppointmentEventBusEntity;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.fragment.CalendarFragment;
import rxh.shanks.fragment.ConfirmAnAppointmentFragment;
import rxh.shanks.presenter.ConfirmAnAppointmentActivityPresenter;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ConfirmAnAppointmentActivityView;

/**
 * Created by Administrator on 2016/8/3.
 * 开始预约项目的Activity
 */
public class ConfirmAnAppointmentActivity extends BaseActivity implements CalendarFragment.CalendarFragmentListener, ConfirmAnAppointmentFragment.ConfirmAnAppointmentFragmentListener {

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
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.calendar)
    ImageView calendar;

    String titletv, lessonID, coachID, coachName, head_path, evaluate, club_name_tv, when_long;

    CalendarFragment calendarFragment;
    ConfirmAnAppointmentPageAdapter confirmAnAppointmentPageAdapter;
    private List<String> datelist = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titletv = getIntent().getStringExtra("title");
        lessonID = getIntent().getStringExtra("lessonID");
        MyApplication.lessonID = lessonID;
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
        setContentView(R.layout.activity_confirm_an_appointment);
        ButterKnife.bind(this);
        datelist = CreatTime.creat(new Date());
        fragmentdatelist = CreatTime.creatfragment(new Date());
        confirmAnAppointmentPageAdapter = new ConfirmAnAppointmentPageAdapter(getSupportFragmentManager(), this, datelist, fragmentdatelist);
        viewpager.setAdapter(confirmAnAppointmentPageAdapter);
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
        title.setText(titletv);
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
        confirmAnAppointmentPageAdapter = new ConfirmAnAppointmentPageAdapter(getSupportFragmentManager(), this, datelist, fragmentdatelist);
        viewpager.setAdapter(confirmAnAppointmentPageAdapter);
    }

    //从fragment传回来的预约的IDs
    @Override
    public void ConfirmAnAppointment() {
        Toast.makeText(getApplicationContext(), "预约成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
