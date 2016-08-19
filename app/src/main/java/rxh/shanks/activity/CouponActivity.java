package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.CouponAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.CouponEntity;

/**
 * Created by Administrator on 2016/8/2.
 * 优惠券
 */
public class CouponActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    private SwipeToLoadLayout swipeToLoadLayout;
    ListViewForScrollView lv;
    private List<CouponEntity> data = new ArrayList<>();
    CouponAdapter couponAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }


    public void initview() {
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        title.setText("优惠券");
        back.setOnClickListener(this);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListViewForScrollView) findViewById(R.id.lv);
        for (int i = 0; i < 10; i++) {
            CouponEntity couponEntity = new CouponEntity();
            couponEntity.setClub_name("测试俱乐部");
            couponEntity.setDiscount("9折");
            couponEntity.setApplicable_shop("全部店铺");
            couponEntity.setSuitable_product("全部产品");
            couponEntity.setTerm_of_validity("2016-5-7");
            data.add(couponEntity);
        }
        couponAdapter = new CouponAdapter(CouponActivity.this, data);
        lv.setAdapter(couponAdapter);
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
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }


}
