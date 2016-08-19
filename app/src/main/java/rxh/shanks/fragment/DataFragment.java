package rxh.shanks.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.activity.R;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.DataEventBusEntity;

/**
 * Created by Administrator on 2016/8/3.
 * 资料fragment
 */
public class DataFragment extends Fragment {

    View view;
    @Bind(R.id.data)
    TextView data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data, null);
        ButterKnife.bind(this, view);
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    public void onEventMainThread(DataEventBusEntity dataEventBusEntity) {
        data.setText(dataEventBusEntity.getContent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }
}
