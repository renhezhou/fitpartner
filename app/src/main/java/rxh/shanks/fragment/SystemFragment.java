package rxh.shanks.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.activity.SystemNextActivity;
import rxh.shanks.activity.SystemSecondActivity;
import rxh.shanks.adapter.IntformationAdapter;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.IntformationEntity;
import rxh.shanks.entity.SystemLVEntity;
import rxh.shanks.presenter.SystemPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.view.SystemView;

/**
 * Created by Administrator on 2016/8/2.
 * 系统fragment
 */
public class SystemFragment extends Fragment implements SystemView {

    View view;
    ListView lv;
    private List<SystemLVEntity> data = new ArrayList<>();
    IntformationAdapter intformationAdapter;
    SystemPresenter systemPresenter;
    int delPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_system, null);
        systemPresenter = new SystemPresenter(this);
        initview();
        return view;
    }

    public void initview() {
        lv = (ListView) view.findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).getType().equals("ReplaceGroupClassToUser") || data.get(position).getType().equals("ReplaceOrderLessonToUser")) {
                    Intent intent = new Intent();
                    intent.putExtra("type", data.get(position).getType());
                    intent.setClass(getActivity(), SystemNextActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("type", data.get(position).getType());
                    intent.setClass(getActivity(), SystemSecondActivity.class);
                    startActivity(intent);
                }
            }
        });
        systemPresenter.getMsg();
    }

    @Override
    public void getMsg(List<SystemLVEntity> systemLVEntityList) {
        data = systemLVEntityList;
        intformationAdapter = new IntformationAdapter(getActivity(), data);
        lv.setAdapter(intformationAdapter);
    }

    @Override
    public void delMsg(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void delSuccess() {
        data.remove(delPosition);
        intformationAdapter.notifyDataSetChanged();
    }
}