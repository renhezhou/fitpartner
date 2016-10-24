package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.TestRecordEntity;
import rxh.shanks.entity.TestRecordEventBusEntity;
import rxh.shanks.presenter.TestRecordPresenter;
import rxh.shanks.view.TestRecordView;

/**
 * Created by Administrator on 2016/8/2.
 * 体测记录
 */
public class TestRecordActivity extends BaseActivity implements TestRecordView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    TestRecordPresenter presenter;
    @Bind(R.id.empty)
    TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TestRecordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_test_record);
        ButterKnife.bind(this);
        title.setText("体测记录");
        back.setOnClickListener(this);
        presenter.getBodyTestRecord();
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
    public void getBodyTestRecord(List<TestRecordEntity> testRecordEntityList) {
        if (testRecordEntityList.size() > 0) {
            empty.setVisibility(View.GONE);
            EventBus.getDefault().post(new TestRecordEventBusEntity(testRecordEntityList));
        } else {
            empty.setVisibility(View.VISIBLE);
        }
    }
}
