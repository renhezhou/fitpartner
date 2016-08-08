package rxh.shanks.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.CourseDetailsPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.fragment.CalendarFragment;
import rxh.shanks.fragment.CourseDetailsFragment;
import rxh.shanks.utils.CreatTime;

/**
 * Created by Administrator on 2016/8/3.
 * 课程详情。包括团体课和免费课程
 */
public class CourseDetailsActivity extends BaseActivity implements CalendarFragment.CalendarFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.calendar)
    ImageView calendar;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    String clubname, flag;//1代表免费课程，2代表团体课

    CalendarFragment calendarFragment;
    CourseDetailsPageAdapter courseDetailsPageAdapter;
    private List<String> datelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getIntent().getStringExtra("flag");
        clubname = getIntent().getStringExtra("club_name");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        if (flag.equals("1")) {
            title.setText("免费课程");
        } else {
            title.setText("团体课");
        }
        club_name.setText(clubname);
        back.setOnClickListener(this);
        calendar.setOnClickListener(this);
        datelist = CreatTime.creat(new Date());
        courseDetailsPageAdapter = new CourseDetailsPageAdapter(getSupportFragmentManager(), this, datelist, flag);
        viewpager.setAdapter(courseDetailsPageAdapter);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
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
        datelist = CreatTime.creat(CreatTime.conversion(date));
        courseDetailsPageAdapter = new CourseDetailsPageAdapter(getSupportFragmentManager(), this, datelist, flag);
        viewpager.setAdapter(courseDetailsPageAdapter);
    }

}
