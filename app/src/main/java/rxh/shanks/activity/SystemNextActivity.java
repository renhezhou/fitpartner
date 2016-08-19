package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.IntformationAdapter;
import rxh.shanks.adapter.SystemNextAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.SystemLVEntity;
import rxh.shanks.fragment.SystemDialogFragment;
import rxh.shanks.presenter.SystemNextPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.view.SystemNextView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SystemNextActivity extends BaseActivity implements SystemNextView, SystemDialogFragment.SystemDialogFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    ListView lv;

    int delPosition;
    String type;
    private List<SystemLVEntity> data = new ArrayList<>();
    SystemDialogFragment systemDialogFragment;
    SystemNextAdapter systemNextAdapter;
    SystemNextPresenter systemNextPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        systemNextPresenter = new SystemNextPresenter(this);
        initview();
        initdata();
    }

    public void initview() {
        setContentView(R.layout.activity_system_next);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        title.setText(CheckUtils.gettype(type));
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delPosition = position;
                if (systemDialogFragment == null) {
                    systemDialogFragment = new SystemDialogFragment();
                }
                systemDialogFragment.show(getSupportFragmentManager(), "systemDialogFragment");
                return false;
            }
        });
    }

    public void initdata(){
        systemNextPresenter.getMsg(type);
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
    public void getMsg(List<SystemLVEntity> systemLVEntityList) {
        data = systemLVEntityList;
        systemNextAdapter = new SystemNextAdapter(getApplicationContext(), data);
        lv.setAdapter(systemNextAdapter);
    }

    @Override
    public void delMsg(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void delSuccess() {

    }

    //
    @Override
    public void del(String position) {
        systemNextPresenter.delMsg(String.valueOf(delPosition));
    }
}
