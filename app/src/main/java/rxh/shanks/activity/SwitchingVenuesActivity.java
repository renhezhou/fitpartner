package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.SVAEntity;
import rxh.shanks.adapter.SwitchingVenuesAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.SwitchingVenuesAdapterEntity;
import rxh.shanks.entity.SwitchingVenuesCodeEntity;
import rxh.shanks.entity.SwitchingVenuesEntity;
import rxh.shanks.presenter.SwitchingVenuesPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SwitchingVenuesView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class SwitchingVenuesActivity extends BaseActivity implements SwitchingVenuesView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    ExpandableListView lv;
    SwitchingVenuesAdapter adapter;
    List<SwitchingVenuesAdapterEntity> data = new ArrayList<>();
    SwitchingVenuesPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SwitchingVenuesPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_switching_venues);
        ButterKnife.bind(this);
        title.setText("切换场馆");
        back.setOnClickListener(this);
        lv.setGroupIndicator(null);
        presenter.getClub();
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                presenter.switchingvenues(data.get(groupPosition).getEntityList().get(childPosition).getClubID());
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("加载中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getallvenues(List<SwitchingVenuesAdapterEntity> SwitchingVenuesAdapterEntitys) {
        data.clear();
        data.addAll(SwitchingVenuesAdapterEntitys);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getEntityList().get(0).getClubID().equals(MyApplication.currentClubID)) {
                SwitchingVenuesAdapterEntity entity = new SwitchingVenuesAdapterEntity();
                entity.setBrandName(data.get(0).getBrandName());
                entity.setEntityList(data.get(0).getEntityList());
                data.set(0, data.get(i));
                data.set(i, entity);
            }
        }
        if (adapter == null) {
            adapter = new SwitchingVenuesAdapter(data, getApplicationContext());
            lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < data.size(); i++) {
            lv.expandGroup(i);
        }
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public void switchingvenues() {
        EventBus.getDefault().post(new SVAEntity());
        presenter.getClub();
    }

}
