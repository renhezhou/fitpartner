package rxh.shanks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import rxh.shanks.base.BaseActivity;

public class GuidePageActivity extends BaseActivity {

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user_info", 0);
        setContentView(R.layout.activity_guide_page);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (sp.getString("token", null) != null) {
                    Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                    startActivity(intent);
                    GuidePageActivity.this.finish();
                    finish();
                } else {
                    Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    GuidePageActivity.this.finish();
                    finish();
                }
            }

        }, 2000);
    }

}
