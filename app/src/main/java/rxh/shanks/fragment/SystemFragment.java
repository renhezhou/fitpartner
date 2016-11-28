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

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.ReadMsgEntity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.SystemNextActivity;
import rxh.shanks.activity.SystemSecondActivity;
import rxh.shanks.adapter.IntformationAdapter;
import rxh.shanks.entity.SystemEventBusEntity;
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
    SystemDialogFragment systemDialogFragment;
    SystemPresenter presenter;
    int delPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_system, null);
        presenter = new SystemPresenter(this);
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

    public void onEventMainThread(SystemEventBusEntity systemEventBusEntity) {
        presenter.delMsg(CheckUtils.getbacktype(data.get(delPosition).getType()));
    }

    public void onEventMainThread(ReadMsgEntity readMsgEntity) {

    }

    public void initview() {
        lv = (ListView) view.findViewById(R.id.lv);
        lv.setEmptyView(view.findViewById(R.id.empty));
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delPosition = position;
                if (systemDialogFragment == null) {
                    systemDialogFragment = new SystemDialogFragment();
                }
                systemDialogFragment.show(getActivity().getSupportFragmentManager(), "systemDialogFragment");
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).getType().equals("教练代约团课") || data.get(position).getType().equals("教练代约私教课")) {
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
        presenter.getMsg();
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
        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG);
        data.remove(delPosition);
        intformationAdapter.notifyDataSetChanged();
    }
}
