package rxh.shanks.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import rxh.shanks.base.BaseActivity;

public class GuidePageActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
                startActivity(intent);
                GuidePageActivity.this.finish();
                finish();
            }

        }, 2000);
    }

}
