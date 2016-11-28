package rxh.shanks.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import rxh.shanks.EBEntity.MANEntity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.ViewAppointmentActivity;
import rxh.shanks.adapter.AlreadyMakeAnAppointmentAdapter;
import rxh.shanks.adapter.CoachAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.AlreadyMakeAnAppointmentEntity;
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.MyPrivateEducationEventBusEntity;
import rxh.shanks.presenter.AlreadyMakeAnAppointmentPresenter;
import rxh.shanks.view.AlreadyMakeAnAppointmentView;

/**
 * Created by Administrator on 2016/8/2.
 * 已预约
 */
public class AlreadyMakeAnAppointmentFragment extends Fragment implements AlreadyMakeAnAppointmentAdapter.OnItemClickLitener, OnRefreshListener, OnLoadMoreListener, AlreadyMakeAnAppointmentView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView lv;
    List<AlreadyMakeAnAppointmentEntity> data = new ArrayList<>();
    AlreadyMakeAnAppointmentAdapter adapter;
    public String flag;//1表示是从我的私教跳转过来的 ，0表示从我的团课跳转过来的
    AlreadyMakeAnAppointmentPresenter presenter;

    public static AlreadyMakeAnAppointmentFragment newInstance(String flag) {
        Bundle args = new Bundle();
        args.putString("flag", flag);
        AlreadyMakeAnAppointmentFragment fragment = new AlreadyMakeAnAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_already_make_an_appointment, null);
        presenter = new AlreadyMakeAnAppointmentPresenter(this);
        flag = getArguments().getString("flag");
        // 注册EventBus
        EventBus.getDefault().register(this);
        initview();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void onEventMainThread(MANEntity entity) {
        if (flag.equals("1")) {
            presenter.getMyOrderPrivateLesson();
        } else {
            presenter.getMyOrderTeamLesson();
        }
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        if (flag.equals("1")) {
            presenter.getMyOrderPrivateLesson();
        } else {
            presenter.getMyOrderTeamLesson();
        }
        lv = (ListView) view.findViewById(R.id.swipe_target);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("lessonID", data.get(position).getLessonID());
                intent.setClass(getActivity(), ViewAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        if (flag.equals("1")) {
            presenter.getMyOrderPrivateLesson();
        } else {
            presenter.getMyOrderTeamLesson();
        }
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
    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMyOrderPrivateLesson(List<AlreadyMakeAnAppointmentEntity> alreadyMakeAnAppointmentEntityList) {
        data.clear();
        data.addAll(alreadyMakeAnAppointmentEntityList);
        adapter = new AlreadyMakeAnAppointmentAdapter(getActivity(), data);
        adapter.setOnItemClickLitener(this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int flag, int position) {
        //0,1电话，IM
        if (flag == 0) {
            Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                    .parse("tel:" + data.get(position).getNextLesson().getCoachPhone()));
            startActivity(intentNO);
        } else if (flag == 1) {
            //启动会话界面
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().startPrivateChat(getActivity(), data.get(position).getNextLesson().getCoachID(), data.get(position).getNextLesson().getCoachName());
            }
        }
    }
}
