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
import rxh.shanks.adapter.ConfirmAnAppointmentPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.ConfirmAnAppointmentEventBusEntity;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.NotMakeAnAppointmentEventBusEntity;
import rxh.shanks.fragment.CalendarFragment;
import rxh.shanks.fragment.ConfirmAnAppointmentFragment;
import rxh.shanks.presenter.ConfirmAnAppointmentActivityPresenter;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ConfirmAnAppointmentActivityView;

/**
 * Created by Administrator on 2016/8/3.
 * 我的团课
 */
public class ConfirmAnAppointmentActivity extends BaseActivity implements CalendarFragment.CalendarFragmentListener, ConfirmAnAppointmentFragment.ConfirmAnAppointmentFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.calendar)
    ImageView calendar;

    String titletv, lessonID;

    CalendarFragment calendarFragment;
    ConfirmAnAppointmentPageAdapter pageAdapter;
    private List<String> datelist = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titletv = getIntent().getStringExtra("title");
        lessonID = getIntent().getStringExtra("lessonID");
        MyApplication.lessonID = lessonID;
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_confirm_an_appointment);
        ButterKnife.bind(this);
        datelist = CreatTime.creat(new Date());
        fragmentdatelist = CreatTime.creatfragment(new Date());
        pageAdapter = new ConfirmAnAppointmentPageAdapter(getSupportFragmentManager(), datelist, fragmentdatelist, lessonID);
        viewpager.setAdapter(pageAdapter);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
        back.setOnClickListener(this);
        calendar.setOnClickListener(this);
        initdata();
    }

    public void initdata() {
        title.setText(titletv);
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
        pageAdapter = new ConfirmAnAppointmentPageAdapter(getSupportFragmentManager(), datelist, fragmentdatelist, lessonID);
        viewpager.setAdapter(pageAdapter);
    }

    //从fragment传回来的预约的IDs
    @Override
    public void ConfirmAnAppointment() {
        new AlertDialog.Builder(this)
                .setTitle("预约成功")
                .setMessage("你已成功预约该团课课程。请准时前往上课")
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
