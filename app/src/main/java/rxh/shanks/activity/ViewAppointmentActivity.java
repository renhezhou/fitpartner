package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.EAEntity;
import rxh.shanks.EBEntity.ReadMsgEntity;
import rxh.shanks.adapter.ViewAppointmentAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.ViewAppointmentEntity;
import rxh.shanks.presenter.ViewAppointmentPresenter;
import rxh.shanks.view.ViewAppointmentView;

/**
 * Created by Administrator on 2016/8/2.
 * 已下的预约
 */
public class ViewAppointmentActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, ViewAppointmentView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView swipe_target;
    List<ViewAppointmentEntity> data = new ArrayList<>();
    ViewAppointmentAdapter adapter;
    String coachID, lessonID, head_path;//教练ID,课程ID
    ViewAppointmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ViewAppointmentPresenter(this);
        EventBus.getDefault().register(this);
        coachID = getIntent().getStringExtra("coachID");
        lessonID = getIntent().getStringExtra("lessonID");
        head_path = getIntent().getStringExtra("head_path");
        initview();
        presenter.getOrderLesson(lessonID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EAEntity eaEntity) {
        presenter.getOrderLesson(lessonID);
    }

    public void initview() {
        setContentView(R.layout.activity_view_appointment);
        ButterKnife.bind(this);
        title.setText("我的预约");
        back.setOnClickListener(this);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipe_target = (ListView) findViewById(R.id.swipe_target);
        swipe_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //00:完成未评价  01:完成已评价  10:预约中 11:代约中
                if (data.get(position).getState().equals("00")) {
                    if (head_path != null) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), EvaluateActivity.class);
                        intent.putExtra("coachID", coachID);
                        intent.putExtra("appointmentID", data.get(position).getAppointmentID());
                        intent.putExtra("head_path", head_path);
                        intent.putExtra("name", data.get(position).getLessonName());
                        startActivity(intent);
                    }
                } else if (data.get(position).getState().equals("10")) {
                    presenter.cancelBespokeLesson(data.get(position).getAppointmentID());
                } else if (data.get(position).getState().equals("11")) {
                    presenter.confirmOrderLesson(data.get(position).getAppointmentID());
                }
            }
        });
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

    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        presenter.getOrderLesson(lessonID);
    }

    @Override
    public void show() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hide() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    @Override
    public void getOrderLesson(List<ViewAppointmentEntity> viewAppointmentEntityList) {
        EventBus.getDefault().post(new ReadMsgEntity());
        data.clear();
        data.addAll(viewAppointmentEntityList);
        adapter = new ViewAppointmentAdapter(ViewAppointmentActivity.this, data);
        swipe_target.setAdapter(adapter);

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success() {
        presenter.getOrderLesson(lessonID);
    }
}
