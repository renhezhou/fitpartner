package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/3.
 * 场馆介绍
 */
public class VenueIntroductionActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.introduce)
    TextView introduce;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_venue_introduction);
        ButterKnife.bind(this);
        title.setText("场馆介绍");
        back.setOnClickListener(this);
        introduce.setText(getIntent().getStringExtra("ClubIntroduce"));
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
}
