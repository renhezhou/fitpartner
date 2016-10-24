package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import rxh.shanks.EBEntity.VFCEntity;
import rxh.shanks.activity.PrivateEducationOrLeagueDetailsActivity;
import rxh.shanks.activity.R;
import rxh.shanks.adapter.CourseDetailsAdapter;
import rxh.shanks.adapter.FragmentViewFreeCoursesAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.CourseDetailsEntity;
import rxh.shanks.presenter.CourseDetailsPresenter;
import rxh.shanks.presenter.ViewFreeCoursesPresenter;
import rxh.shanks.view.ViewFreeCoursesView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ViewFreeCoursesFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, ViewFreeCoursesView {

    View view;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView lv;

    String date, clubID;
    private List<CourseDetailsEntity> data = new ArrayList<>();
    FragmentViewFreeCoursesAdapter fragmentViewFreeCoursesAdapter;
    ViewFreeCoursesPresenter viewFreeCoursesPresenter;

    public static ViewFreeCoursesFragment newInstance(String date, String clubID) {
        Bundle args = new Bundle();
        args.putString("date", date);
        args.putString("clubID", clubID);
        ViewFreeCoursesFragment pageFragment = new ViewFreeCoursesFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_free_courses, null);
        viewFreeCoursesPresenter = new ViewFreeCoursesPresenter(this);
        EventBus.getDefault().register(this);
        date = getArguments().getString("date");
        clubID = getArguments().getString("clubID");
        initview();
        initdata();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(VFCEntity vfcEntity) {
        clubID = vfcEntity.getClubID();
        viewFreeCoursesPresenter.getFreeLesson(clubID, date);
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListView) view.findViewById(R.id.swipe_target);

    }

    public void initdata() {
        viewFreeCoursesPresenter.getFreeLesson(clubID, date);
    }

    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        initdata();
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
        fragmentViewFreeCoursesAdapter = new FragmentViewFreeCoursesAdapter(getActivity(), data);
        lv.setAdapter(fragmentViewFreeCoursesAdapter);
    }
}
