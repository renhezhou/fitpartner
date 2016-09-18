package rxh.shanks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.IntformationAdapter;
import rxh.shanks.adapter.SystemNextAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.SystemLVEntity;
import rxh.shanks.fragment.SystemDialogFragment;
import rxh.shanks.presenter.SystemNextPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.view.SystemNextView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SystemNextActivity extends BaseActivity implements SystemNextView, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    ListViewForScrollView lv;

    private SwipeToLoadLayout swipeToLoadLayout;

    String type;
    private List<SystemLVEntity> data = new ArrayList<>();
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
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        back.setOnClickListener(this);
        title.setText(type);
    }

    public void initdata() {
        systemNextPresenter.getMsg(CheckUtils.getbacktype(type));
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
    public void getMsg(List<SystemLVEntity> systemLVEntityList) {
        swipeToLoadLayout.setRefreshing(false);
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
    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        initdata();
    }
}
