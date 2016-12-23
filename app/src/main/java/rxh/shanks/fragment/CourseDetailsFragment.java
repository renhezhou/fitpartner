package rxh.shanks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.PrivateEducationOrLeagueDetailsActivity;
import rxh.shanks.activity.R;
import rxh.shanks.adapter.CourseDetailsAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.CourseDetailsEntity;
import rxh.shanks.presenter.CourseDetailsPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CourseDetailsView;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CourseDetailsFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, CourseDetailsView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView lv;
    private List<CourseDetailsEntity> data = new ArrayList<>();
    CourseDetailsAdapter adapter;
    CourseDetailsPresenter presenter;


    String date;

    public static CourseDetailsFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date", date);
        CourseDetailsFragment pageFragment = new CourseDetailsFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_details, null);
        presenter = new CourseDetailsPresenter(this);
        date = getArguments().getString("date");
        initview();
        initdada();
        return view;
    }


    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListView) view.findViewById(R.id.swipe_target);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApplication.CoachID = data.get(position).getCoachID();
                Intent intent = new Intent();
                intent.setClass(getActivity(), PrivateEducationOrLeagueDetailsActivity.class);
                intent.putExtra("logo", data.get(position).getLogo());
                intent.putExtra("address", data.get(position).getAddress());
                intent.putExtra("coachID", data.get(position).getCoachID());
                intent.putExtra("coachName", data.get(position).getCoachName());
                intent.putExtra("lessonIntro", data.get(position).getLessonIntro());
                intent.putExtra("lessonName", data.get(position).getLessonName());
                intent.putExtra("lessonID", data.get(position).getLessonID());
                intent.putExtra("evaluate", data.get(position).getEvaluate());
                intent.putExtra("price", data.get(position).getPrice());
                intent.putExtra("time", data.get(position).getTime());
                startActivity(intent);
            }
        });
    }

    public void initdada() {
        presenter.getFreeLesson(date);
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
    public void getFreeLesson(List<CourseDetailsEntity> courseDetailsEntityList) {
        swipeToLoadLayout.setRefreshing(false);
        data = courseDetailsEntityList;
        adapter = new CourseDetailsAdapter(getActivity(), data);
        lv.setAdapter(adapter);
    }


    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        initdada();
    }

}
