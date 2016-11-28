package rxh.shanks.fragment;

import android.content.Intent;
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
import rxh.shanks.EBEntity.MANEntity;
import rxh.shanks.activity.ConfirmAnAppointmentActivity;
import rxh.shanks.activity.ConfirmTheAppointmentOfPrivateEducationActivity;
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
    ListView lv;
    List<NotMakeAnAppointmentEntity> data = new ArrayList<>();
    NotMakeAnAppointmentAdapter adapter;
    public String flag;//1表示是从我的私教跳转过来的 ，0表示从我的团课跳转过来的
    NotMakeAnAppointmentPresenter presenter;

    public static NotMakeAnAppointmentFragment newInstance(String flag) {
        Bundle args = new Bundle();
        args.putString("flag", flag);
        NotMakeAnAppointmentFragment fragment = new NotMakeAnAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_make_an_appointment, null);
        presenter = new NotMakeAnAppointmentPresenter(this);
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
            presenter.getMyUnorderPrivateLesson();
        } else {
            presenter.getMyUnorderTeamLesson();
        }
    }


    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListView) view.findViewById(R.id.swipe_target);
        if (flag.equals("1")) {
            presenter.getMyUnorderPrivateLesson();
        } else {
            presenter.getMyUnorderTeamLesson();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这个地方我的私教和我的团课模块跳转下面的界面有所不同。我的团课跳ConfirmAnAppointmentActivity，我的
                //私教跳PrivateEducationCourseActivity。
                Intent intent = new Intent();
                if (flag.equals("1")) {
                    MyApplication.lessonID = data.get(position).getLessonID();
                    intent.setClass(getActivity(), ConfirmTheAppointmentOfPrivateEducationActivity.class);
                    intent.putExtra("lessonId", data.get(position).getLessonID());
                    intent.putExtra("lessonName", data.get(position).getLessonName());
                    intent.putExtra("lesson_address", data.get(position).getAddress());
                    int sy = Integer.parseInt(data.get(position).getTotalCount()) - Integer.parseInt(data.get(position).getOrderCount());
                    intent.putExtra("number_of_remaining_nodes", String.valueOf(sy));
                    startActivity(intent);
                } else {
                    MyApplication.lessonID = data.get(position).getLessonID();
                    intent.setClass(getActivity(), ConfirmAnAppointmentActivity.class);
                    intent.putExtra("title", data.get(position).getLessonName());
                    //intent.putExtra("coachID", coachID);
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
            presenter.getMyUnorderPrivateLesson();
        } else {
            presenter.getMyUnorderTeamLesson();
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
    public void getMyUnorderPrivateLesson(List<NotMakeAnAppointmentEntity> notMakeAnAppointmentEntityList) {
        data.clear();
        data.addAll(notMakeAnAppointmentEntityList);
        adapter = new NotMakeAnAppointmentAdapter(getActivity(), data);
        lv.setAdapter(adapter);
    }
}
