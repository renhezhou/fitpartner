package rxh.shanks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.shanks.activity.ConfirmAnAppointmentActivity;
import rxh.shanks.activity.PrivateEducationCourseActivity;
import rxh.shanks.activity.R;
import rxh.shanks.adapter.NotMakeAnAppointmentAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.MyPrivateEducationEventBusEntity;
import rxh.shanks.entity.NotMakeAnAppointmentEntity;
import rxh.shanks.entity.NotMakeAnAppointmentEventBusEntity;
import rxh.shanks.presenter.NotMakeAnAppointmentPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.NotMakeAnAppointmentView;

/**
 * Created by Administrator on 2016/8/2.
 * 未预约
 */
public class NotMakeAnAppointmentFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, NotMakeAnAppointmentView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListViewForScrollView lv;
    List<NotMakeAnAppointmentEntity> data = new ArrayList<>();
    NotMakeAnAppointmentAdapter notMakeAnAppointmentAdapter;
    public String flag;//1表示是从我的私教跳转过来的 ，0表示从我的团课跳转过来的
    String coachID, coachName, head_path, evaluate, club_name, when_long, time;
    NotMakeAnAppointmentPresenter notMakeAnAppointmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_make_an_appointment, null);
        notMakeAnAppointmentPresenter = new NotMakeAnAppointmentPresenter(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    //获取从activity传过来的数据
    public void onEventMainThread(MyPrivateEducationEventBusEntity myPrivateEducationEventBusEntity) {
        flag = myPrivateEducationEventBusEntity.getFlag();
        coachID = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getCoachID();
        coachName = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getName();
        head_path = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getHeadImageURL();
        evaluate = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getEvaluate();
        club_name = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getClubName();
        when_long = myPrivateEducationEventBusEntity.getMyPrivateEducationHeadEntity().getTeachTime();
        initview();
        if (flag.equals("1")) {
            notMakeAnAppointmentPresenter.getMyUnorderPrivateLesson(coachID);
        } else {
            notMakeAnAppointmentPresenter.getMyUnorderTeamLesson(coachID);
        }
    }

    //获取下一级界面发送来的消息，更新数据
    public void onEventMainThread(NotMakeAnAppointmentEventBusEntity notMakeAnAppointmentEventBusEntity) {
        if (flag.equals("1")) {
            notMakeAnAppointmentPresenter.getMyUnorderPrivateLesson(coachID);
        } else {
            notMakeAnAppointmentPresenter.getMyUnorderTeamLesson(coachID);
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
                //这个地方我的私教和我的团课模块跳转下面的界面有所不同。我的团课跳ConfirmAnAppointmentActivity，我的
                //私教跳PrivateEducationCourseActivity。
                Intent intent = new Intent();
                if (flag.equals("1")) {
                    //传coachID, coachName, head_path过去;
                    MyApplication.lessonID = data.get(position).getLessonID();
                    intent.setClass(getActivity(), PrivateEducationCourseActivity.class);
                    intent.putExtra("coachID", coachID);
                    intent.putExtra("coachName", coachName);
                    intent.putExtra("head_path", head_path);
                    intent.putExtra("evaluate", evaluate);
                    intent.putExtra("club_name", club_name);
                    intent.putExtra("when_long", when_long);
                    intent.putExtra("time", data.get(position).getTime());
                    startActivity(intent);
                } else {
                    MyApplication.lessonID = data.get(position).getLessonID();
                    intent.setClass(getActivity(), ConfirmAnAppointmentActivity.class);
                    intent.putExtra("title", data.get(position).getLessonName());
                    intent.putExtra("coachID", coachID);
                    intent.putExtra("coachName", coachName);
                    intent.putExtra("head_path", head_path);
                    intent.putExtra("evaluate", evaluate);
                    intent.putExtra("club_name", club_name);
                    intent.putExtra("when_long", when_long);
                    intent.putExtra("lessonID", data.get(position).getLessonID());
                    startActivity(intent);
                }

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
            notMakeAnAppointmentPresenter.getMyUnorderPrivateLesson(coachID);
        } else {
            notMakeAnAppointmentPresenter.getMyUnorderTeamLesson(coachID);
        }
    }

    @Override
    public void getMyUnorderPrivateLesson(List<NotMakeAnAppointmentEntity> notMakeAnAppointmentEntityList) {
        swipeToLoadLayout.setRefreshing(false);
        data.clear();
        data = notMakeAnAppointmentEntityList;
        notMakeAnAppointmentAdapter = new NotMakeAnAppointmentAdapter(getActivity(), data);
        lv.setAdapter(notMakeAnAppointmentAdapter);
    }
}
