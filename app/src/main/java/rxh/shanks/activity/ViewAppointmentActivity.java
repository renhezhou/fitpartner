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
import rxh.shanks.fragment.ViewAppointmentDialogFragment;
import rxh.shanks.fragment.ViewAppointmentDialogFragment.ViewAppointmentDialogFragmentListener;
import rxh.shanks.presenter.ViewAppointmentPresenter;
import rxh.shanks.view.ViewAppointmentView;

/**
 * Created by Administrator on 2016/8/2.
 * 已下的预约
 */
public class ViewAppointmentActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, ViewAppointmentView, ViewAppointmentDialogFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView swipe_target;
    List<ViewAppointmentEntity> data = new ArrayList<>();
    ViewAppointmentAdapter adapter;
    String lessonID;
    ViewAppointmentPresenter presenter;
    ViewAppointmentDialogFragment dialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ViewAppointmentPresenter(this);
        lessonID = getIntent().getStringExtra("lessonID");
        initview();
        presenter.getOrderLesson(lessonID);
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
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), EvaluateActivity.class);
                    intent.putExtra("coachID", data.get(position).getCoachID());
                    intent.putExtra("coachName", data.get(position).getCoachName());
                    intent.putExtra("appointmentID", data.get(position).getAppointmentID());
                    intent.putExtra("head_path", data.get(position).getCoachURL());
                    intent.putExtra("name", data.get(position).getLessonName());
                    startActivity(intent);

                } else if (data.get(position).getState().equals("10")) {
//                    presenter.cancelBespokeLesson(data.get(position).getAppointmentID());
                    showdialog(10, position, "尊敬的用户你好。你点击的项目正处于预约中。如想取消此次预约，请点击确定按钮");
                } else if (data.get(position).getState().equals("11")) {
                    //presenter.confirmOrderLesson(data.get(position).getAppointmentID());
                    showdialog(11, position, "尊敬的用户你好。你点击的项目正处于代约中。请点击确定按钮确认此次代约");
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
    public void show(int flag) {
        if (flag == 0) {
            swipeToLoadLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(true);
                }
            });
        } else if (flag == 10) {
            loading("取消中", "true");
        } else if (flag == 11) {
            loading("确认中", "true");
        }
    }

    @Override
    public void hide(int flag) {
        if (flag == 0) {
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            }
        } else if (flag == 10) {
            dismiss();
        } else if (flag == 11) {
            dismiss();
        }
    }

    @Override
    public void getOrderLesson(List<ViewAppointmentEntity> viewAppointmentEntityList) {
        if (data.size() > 0) {
            data.clear();
        }
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

    //10:预约中 11:代约中
    @Override
    public void sure(int flag, int position) {
        if (flag == 10) {
            presenter.cancelBespokeLesson(data.get(position).getAppointmentID());
        } else if (flag == 11) {
            presenter.confirmOrderLesson(data.get(position).getAppointmentID());
        }
    }

    public void showdialog(int flag, int position, String ts) {
        dialogFragment = new ViewAppointmentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        bundle.putInt("position", position);
        bundle.putString("ts", ts);
        dialogFragment.setArguments(bundle);
        //这句代码用于解决频繁点击时发生的崩溃现象，未测试
        getSupportFragmentManager().executePendingTransactions();
        if (!dialogFragment.isAdded()) {
            dialogFragment.show(getSupportFragmentManager(), "dialogFragment");
        }
    }
}
