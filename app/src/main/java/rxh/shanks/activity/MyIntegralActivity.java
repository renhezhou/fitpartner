package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.MyIntegralGVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.MyIntegralEntity;
import rxh.shanks.fragment.ExchangeFragment;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MyIntegralActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, ExchangeFragment.ExchangeFragmentListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.swipe_target)
    GridView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    ExchangeFragment exchangeFragment;

    List<MyIntegralEntity> data = new ArrayList<>();
    MyIntegralGVAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
    }

    public void initview() {
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        back.setOnClickListener(this);
        title.setText("积分兑换");
        swipeTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showexchangeFragment(position);
            }
        });
    }

    public void initdata() {
        for (int i = 0; i < 20; i++) {
            MyIntegralEntity entity = new MyIntegralEntity();
            entity.setImg("http://www.baidu.com");
            entity.setName("这是测试");
            entity.setIntegral("3000积分");
            data.add(entity);
        }
        adapter = new MyIntegralGVAdapter(getApplicationContext(), data);
        swipeTarget.setAdapter(adapter);
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
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void sure(int position) {

    }

    public void showexchangeFragment(int position) {
        exchangeFragment = new ExchangeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tisi", data.get(position).getIntegral());
        bundle.putInt("position", position);
        exchangeFragment.setArguments(bundle);
        if (!exchangeFragment.isAdded()) {
            exchangeFragment.show(getSupportFragmentManager(), "exchangeFragment");
        }
    }
}
