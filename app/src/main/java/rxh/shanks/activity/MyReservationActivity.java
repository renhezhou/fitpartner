package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.MyReservationVPAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.MyPrivateTeachingAppointmentEntity;
import rxh.shanks.fragment.AlreadyMakeAnAppointmentFragment;
import rxh.shanks.fragment.NotMakeAnAppointmentFragment;

/**
 * Created by Administrator on 2016/11/7.
 * 我的预约
 */
public class MyReservationActivity extends BaseActivity {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.already_make_an_appointment)
    TextView already_make_an_appointment;
    @Bind(R.id.not_make_an_appointment)
    TextView not_make_an_appointment;
    @Bind(R.id.viewpage)
    ViewPager viewpage;

    String flag;//1代表私课，0代表团课
    MyReservationVPAdapter adapter;
    List<Fragment> fragments = new ArrayList<Fragment>();
    List<MyPrivateTeachingAppointmentEntity> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getIntent().getStringExtra("flag");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_my_reservation);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        already_make_an_appointment.setOnClickListener(this);
        not_make_an_appointment.setOnClickListener(this);
        fragments.add(new AlreadyMakeAnAppointmentFragment());
        fragments.add(new NotMakeAnAppointmentFragment());
        if (flag.equals("1")) {
            title.setText("我的私教");
        } else {
            title.setText("我的团课");
        }
        selected(0);
        adapter = new MyReservationVPAdapter(getSupportFragmentManager(), flag, fragments);
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
                //startActivity(new Intent(getApplicationContext(), MyPrivateTeachingAppointmentActivity.class));
                break;
            case R.id.r_load:
                break;
            default:
                break;
        }
    }

    public void selected(int i) {
        if (i == 0) {
            already_make_an_appointment.setTextColor(getResources().getColor(R.color.red));
            already_make_an_appointment.setBackgroundResource(R.drawable.activity_my_reservation_already_make_an_appointment_down);
            not_make_an_appointment.setTextColor(getResources().getColor(R.color.white));
            not_make_an_appointment.setBackgroundResource(R.drawable.activity_my_reservation_not_make_an_appointment_up);
        } else if (i == 1) {
            not_make_an_appointment.setTextColor(getResources().getColor(R.color.red));
            not_make_an_appointment.setBackgroundResource(R.drawable.activity_my_reservation_not_make_an_appointment_down);
            already_make_an_appointment.setTextColor(getResources().getColor(R.color.white));
            already_make_an_appointment.setBackgroundResource(R.drawable.activity_my_reservation_already_make_an_appointment_up);
        }
    }
}
