package rxh.shanks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
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
public class AlreadyMakeAnAppointmentFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, AlreadyMakeAnAppointmentView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListViewForScrollView lv;
    List<AlreadyMakeAnAppointmentEntity> data = new ArrayList<>();
    AlreadyMakeAnAppointmentAdapter alreadyMakeAnAppointmentAdapter;
    String coachID, head_path;
    public String flag;//1表示是从我的私教跳转过来的 ，0表示从我的团课跳转过来的
    AlreadyMakeAnAppointmentPresenter alreadyMakeAnAppointmentPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_already_make_an_appointment, null);
        alreadyMakeAnAppointmentPresenter = new AlreadyMakeAnAppointmentPresenter(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void onEventMainThread(MyPrivateEducationEventBusEntity myPrivateEducationEventBusEntity) {
        flag = myPrivateEducationEventBusEntity.getFlag();
        coachID = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getCoachID();
        head_path = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getHeadImageURL();
        initview();
        if (flag.equals("1")) {
            alreadyMakeAnAppointmentPresenter.getMyOrderPrivateLesson(coachID);
        } else {
            alreadyMakeAnAppointmentPresenter.getMyOrderTeamLesson(coachID);
        }
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListViewForScrollView) view.findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("coachID", coachID);
                intent.putExtra("lessonID", data.get(position).getLessonID());
                intent.putExtra("head_path", head_path);
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
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void getMyOrderPrivateLesson(List<AlreadyMakeAnAppointmentEntity> alreadyMakeAnAppointmentEntityList) {
        data.clear();
        data = alreadyMakeAnAppointmentEntityList;
        alreadyMakeAnAppointmentAdapter = new AlreadyMakeAnAppointmentAdapter(getActivity(), data);
        lv.setAdapter(alreadyMakeAnAppointmentAdapter);
    }
}
