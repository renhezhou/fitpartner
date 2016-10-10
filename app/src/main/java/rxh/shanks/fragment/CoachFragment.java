package rxh.shanks.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.SVAEntity;
import rxh.shanks.activity.CheckToActivity;
import rxh.shanks.activity.CoachDetailsActivity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.ScanActivity;
import rxh.shanks.adapter.CoachAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.presenter.CoachPsesenter;
import rxh.shanks.view.CoachView;

/**
 * Created by Administrator on 2016/7/29.
 * 教练
 */
public class CoachFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, PopupWindow.OnDismissListener, View.OnClickListener, CoachView {

    View view, popView;
    private PopupWindow popupWindow;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListView lv;
    private ImageView add;
    private LinearLayout scan, check_to;
    private List<CoachEntity> data = new ArrayList<>();
    CoachAdapter adapter;
    CoachPsesenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coach, null);
        presenter = new CoachPsesenter(this);
        EventBus.getDefault().register(this);
        initview();
        presenter.getCoach();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(SVAEntity svaEntity) {
        presenter.getCoach();
    }

    public void initview() {
        add = (ImageView) view.findViewById(R.id.add);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        add.setOnClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListView) view.findViewById(R.id.swipe_target);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CoachDetailsActivity.class);
                intent.putExtra("CoachID", data.get(position).getCoachID());
                intent.putStringArrayListExtra("CoverImageArray", (ArrayList<String>) data.get(position).getCoverImageArray());
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("teachtime", data.get(position).getTeachTime());
                intent.putExtra("club_name", data.get(position).getClubName());
                intent.putExtra("HeadImageURL", data.get(position).getHeadImageURL());
                intent.putExtra("evaluate", data.get(position).getEvaluate());
                intent.putExtra("descrip", data.get(position).getDescrip());
                startActivity(intent);
            }
        });
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
    public void getCoach(List<CoachEntity> coachEntityList) {
        data.clear();
        data = coachEntityList;
        adapter = new CoachAdapter(getActivity(), data);
        lv.setAdapter(adapter);
    }


    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        presenter.getCoach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                initPopupWindow();
                backgroundAlpha(0.5f);
                // 为popWindow添加动画效果
                // 点击弹出泡泡窗口
                popupWindow.showAsDropDown(add);
                break;
            case R.id.scan:
                startActivity(new Intent(getActivity(), ScanActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.check_to:
                startActivity(new Intent(getActivity(), CheckToActivity.class));
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        popView = getActivity().getLayoutInflater().inflate(R.layout.activity_main_pop_window,
                null);
        scan = (LinearLayout) popView.findViewById(R.id.scan);
        check_to = (LinearLayout) popView.findViewById(R.id.check_to);
        scan.setOnClickListener(this);
        check_to.setOnClickListener(this);
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);

    }

}
