package rxh.shanks.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import rxh.shanks.adapter.ConfirmTheAppointmentOfPrivateEducationLVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.ConfirmTheAppointmentOfPrivateEducationEntity;
import rxh.shanks.presenter.ConfirmTheAppointmentOfPrivateEducationPresenter;
import rxh.shanks.view.ConfirmTheAppointmentOfPrivateEducationView;

/**
 * Created by Administrator on 2016/11/7.
 * 私教预约选择教练
 */
public class ConfirmTheAppointmentOfPrivateEducationActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, ConfirmTheAppointmentOfPrivateEducationView, ConfirmTheAppointmentOfPrivateEducationLVAdapter.OnItemClickLitener {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lession_name)
    TextView lession_name;
    @Bind(R.id.speed_of_progress)
    TextView speed_of_progress;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    ListView lv;
    @Bind(R.id.confirm_an_appointment)
    Button confirm_an_appointment;

    int selected_position = 0;
    private String lessonId, lessonName, lesson_address, number_of_remaining_nodes;

    ConfirmTheAppointmentOfPrivateEducationPresenter presenter;
    ConfirmTheAppointmentOfPrivateEducationLVAdapter adapter;
    List<ConfirmTheAppointmentOfPrivateEducationEntity> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ConfirmTheAppointmentOfPrivateEducationPresenter(this);
        lessonId = getIntent().getStringExtra("lessonId");
        lessonName = getIntent().getStringExtra("lessonName");
        lesson_address = getIntent().getStringExtra("lesson_address");
        number_of_remaining_nodes = getIntent().getStringExtra("number_of_remaining_nodes");
        presenter.getlessoncoach(lessonId);
    }

    public void initview() {
        setContentView(R.layout.activity_confirm_the_appointment_of_private_education);
        ButterKnife.bind(this);
        title.setText("我的私教");
        back.setOnClickListener(this);
        confirm_an_appointment.setOnClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lession_name.setText(lessonName);
        address.setText(lesson_address);
        speed_of_progress.setText("剩余" + number_of_remaining_nodes + "节");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_an_appointment:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PrivateEducationCourseActivity.class);
                intent.putExtra("lessonID", lessonId);
                intent.putExtra("coachID", data.get(selected_position).getCoachID());
                intent.putExtra("coachName", data.get(selected_position).getName());
                intent.putExtra("head_path", data.get(selected_position).getHeadImageURL());
                intent.putExtra("evaluate", data.get(selected_position).getEvaluate());
                intent.putExtra("club_name", data.get(selected_position).getClubName());
                intent.putExtra("when_long", data.get(selected_position).getTeachTime());
                intent.putExtra("time", data.get(selected_position).getLessonTime());//该节课的时长
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        presenter.getlessoncoach(lessonId);
    }

    @Override
    public void show() {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hide() {
        if (swipeToLoadLayout != null) {
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getlessoncoach(List<ConfirmTheAppointmentOfPrivateEducationEntity> entities) {
        initview();
        data.clear();
        data.addAll(entities);
        adapter = new ConfirmTheAppointmentOfPrivateEducationLVAdapter(getApplicationContext(), data);
        adapter.setOnItemClickLitener(this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int flag, int position) {
        //flag==0,1,2分别代表头像，电话，IM
        if (flag == 0) {
            selected_position = position;
        } else if (flag == 1) {
            Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                    .parse("tel:" + data.get(position).getPhone()));
            startActivity(intentNO);
        } else if (flag == 2) {
            //启动会话界面
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().startPrivateChat(ConfirmTheAppointmentOfPrivateEducationActivity.this, data.get(position).getCoachID(), data.get(position).getName());
            }
        }
    }

}
