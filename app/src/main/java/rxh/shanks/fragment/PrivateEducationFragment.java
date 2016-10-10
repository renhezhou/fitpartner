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

import rxh.shanks.activity.CoachDetailsActivity;
import rxh.shanks.activity.PrivateEducationOrLeagueDetailsActivity;
import rxh.shanks.activity.R;
import rxh.shanks.adapter.CoachAdapter;
import rxh.shanks.adapter.CoachDetailsAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.CoachDetailsEntity;
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.presenter.CoachDetailsPresenter;
import rxh.shanks.view.CoachDetailsView;

/**
 * Created by Administrator on 2016/8/3.
 * 私教Fragment
 */
public class PrivateEducationFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, CoachDetailsView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView lv;
    private List<CoachDetailsEntity> data = new ArrayList<>();
    CoachDetailsAdapter adapter;
    CoachDetailsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_private_education, null);
        presenter = new CoachDetailsPresenter(this);
        initview();
        initdata();
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
                Intent intent = new Intent();
                intent.setClass(getActivity(), PrivateEducationOrLeagueDetailsActivity.class);
                intent.putExtra("logo", data.get(position).getLogo());
                intent.putExtra("address", data.get(position).getAddress());
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

    public void initdata() {
        presenter.getPrivateLesson();
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
    public void getPrivateLessongetTeamLesson(List<CoachDetailsEntity> coachDetailsEntityList) {
        data.clear();
        data = coachDetailsEntityList;
        adapter = new CoachDetailsAdapter(getActivity(), data);
        lv.setAdapter(adapter);
    }


    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        initdata();
    }

}
