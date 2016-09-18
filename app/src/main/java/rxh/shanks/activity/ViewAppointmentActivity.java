package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    ListViewForScrollView lv;
    List<ViewAppointmentEntity> data = new ArrayList<>();
    ViewAppointmentAdapter viewAppointmentAdapter;
    String coachID, lessonID, head_path;//教练ID,课程ID
    ViewAppointmentPresenter viewAppointmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAppointmentPresenter = new ViewAppointmentPresenter(this);
        coachID = getIntent().getStringExtra("coachID");
        lessonID = getIntent().getStringExtra("lessonID");
        head_path = getIntent().getStringExtra("head_path");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_view_appointment);
        ButterKnife.bind(this);
        title.setText("我的预约");
        back.setOnClickListener(this);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListViewForScrollView) findViewById(R.id.lv);
        viewAppointmentPresenter.getOrderLesson(lessonID);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    viewAppointmentPresenter.cancelBespokeLesson(data.get(position).getAppointmentID());
                } else if (data.get(position).getState().equals("11")) {
                    viewAppointmentPresenter.confirmOrderLesson(data.get(position).getAppointmentID());
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
        viewAppointmentPresenter.getOrderLesson(lessonID);
    }

    @Override
    public void show() {
        loading("加载中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getOrderLesson(List<ViewAppointmentEntity> viewAppointmentEntityList) {
        swipeToLoadLayout.setRefreshing(false);
        data.clear();
        data = viewAppointmentEntityList;
        viewAppointmentAdapter = new ViewAppointmentAdapter(ViewAppointmentActivity.this, data);
        lv.setAdapter(viewAppointmentAdapter);

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success() {
        viewAppointmentPresenter.getOrderLesson(lessonID);
    }
}
