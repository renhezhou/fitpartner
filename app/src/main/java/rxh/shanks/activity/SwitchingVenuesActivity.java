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
import rxh.shanks.adapter.SwitchingVenuesAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.SwitchingVenuesCodeEntity;
import rxh.shanks.presenter.SwitchingVenuesPresenter;
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
    SwitchingVenuesAdapter switchingVenuesAdapter;
    SwitchingVenuesCodeEntity data = new SwitchingVenuesCodeEntity();
    SwitchingVenuesPresenter switchingVenuesPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchingVenuesPresenter = new SwitchingVenuesPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_switching_venues);
        ButterKnife.bind(this);
        title.setText("切换场馆");
        back.setOnClickListener(this);
        lv.setGroupIndicator(null);
        switchingVenuesPresenter.getClub();
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switchingVenuesPresenter.switchingvenues(data.getResult().get(groupPosition).get(childPosition).getClubID());
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
    public void getallvenues(SwitchingVenuesCodeEntity switchingVenuesCodeEntity) {
        data = null;
        data = switchingVenuesCodeEntity;
        switchingVenuesAdapter = new SwitchingVenuesAdapter(data, getApplicationContext());
        lv.setAdapter(switchingVenuesAdapter);
        for (int i = 0; i < switchingVenuesCodeEntity.getResult().size(); i++) {
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
        switchingVenuesPresenter.getClub();
    }

}
