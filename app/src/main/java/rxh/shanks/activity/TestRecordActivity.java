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
import rxh.shanks.entity.CurriculumEventBusEntity;
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
    TestRecordPresenter testRecordPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testRecordPresenter = new TestRecordPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_test_record);
        ButterKnife.bind(this);
        title.setText("体测记录");
        back.setOnClickListener(this);
        testRecordPresenter.getBodyTestRecord();
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
        loading("加载中...", "false");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void getBodyTestRecord(List<TestRecordEntity> testRecordEntityList) {
        EventBus.getDefault().post(new TestRecordEventBusEntity(testRecordEntityList));
    }
}
