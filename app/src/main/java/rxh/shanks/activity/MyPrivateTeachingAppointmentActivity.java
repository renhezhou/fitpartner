package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.MyPrivateTeachingAppointmentLVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.MyPrivateTeachingAppointmentEntity;

/**
 * Created by Administrator on 2016/11/7.
 * 我的私教预约
 */
public class MyPrivateTeachingAppointmentActivity extends BaseActivity {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lession_num)
    TextView lession_num;
    @Bind(R.id.lv)
    ListView lv;

    MyPrivateTeachingAppointmentLVAdapter adapter;
    List<MyPrivateTeachingAppointmentEntity> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
    }

    public void initview() {
        setContentView(R.layout.activity_my_private_teaching_appointment);
        ButterKnife.bind(this);
        title.setText("我的私教");
        back.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ConfirmTheAppointmentOfPrivateEducationActivity.class));
            }
        });
    }

    public void initdata() {
        lession_num.setText(Html.fromHtml("共"
                + "<font color=white>"
                + 5 + "</font>节课"));
        for (int i = 0; i < 10; i++) {
            MyPrivateTeachingAppointmentEntity entity = new MyPrivateTeachingAppointmentEntity();
            entity.setLession_name("减脂");
            entity.setAddress("美年观察");
            entity.setDate("2012-12-21");
            entity.setLeesion_details("减脂的 课程");
            entity.setState("预约");
            entity.setTime("11:12-11:22");
            entity.setProjess("10/100");
            data.add(entity);
        }
        adapter = new MyPrivateTeachingAppointmentLVAdapter(getApplicationContext(), data);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
