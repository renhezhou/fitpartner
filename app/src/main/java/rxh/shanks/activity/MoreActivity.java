package rxh.shanks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MoreActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.clear_application_cache)
    RelativeLayout clear_application_cache;
    @Bind(R.id.venue_agreement)
    RelativeLayout venue_agreement;
    @Bind(R.id.switching_venues)
    RelativeLayout switching_venues;
    @Bind(R.id.feedback)
    RelativeLayout feedback;
    @Bind(R.id.exit_login)
    Button exit_login;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user_info", 0);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        title.setText("更多");
        clear_application_cache.setOnClickListener(this);
        venue_agreement.setOnClickListener(this);
        switching_venues.setOnClickListener(this);
        feedback.setOnClickListener(this);
        exit_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_application_cache:
                break;
            case R.id.venue_agreement:
                startActivity(new Intent(getApplicationContext(), VenueAgreementActivity.class));
                break;
            case R.id.switching_venues:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SwitchingVenuesActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
                break;
            case R.id.exit_login:
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user", null);
                editor.putString("password", null);
                editor.commit();
                break;
            default:
                break;
        }
    }
}
