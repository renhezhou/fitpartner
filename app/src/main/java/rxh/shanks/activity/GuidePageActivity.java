package rxh.shanks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.fragment.GuidePageDialogFragment;
import rxh.shanks.presenter.GuidePagePresenter;
import rxh.shanks.view.GuidePageView;

public class GuidePageActivity extends BaseActivity implements GuidePageView, GuidePageDialogFragment.GuidePageDialogFragmentListener {

    private ImageView img;
    private SharedPreferences sp;
    GuidePagePresenter presenter;
    GuidePageDialogFragment fragment;
    String appurl = "https://www.pgyer.com/FitPartner";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GuidePagePresenter(this);
        sp = getSharedPreferences("user_info", 0);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_guide_page);
        img = (ImageView) findViewById(R.id.img);
        Picasso.with(getApplicationContext())
                .load(R.drawable.guidepage)
                .into(img);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (sp.getString("token", null) != null) {
                    presenter.login("2", null, sp.getString("user", null), sp.getString("password", null));
                } else {
                    Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    GuidePageActivity.this.finish();
                    finish();
                }
            }
        }, 2000); //延迟2秒跳转

    }

    @Override
    public void onSuccess(String result, LoginCodeEntity response) {
        if (!response.getResult().getVersion().equals(getVersionName())) {
            //建议用户去更新
            showdialog();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("token", response.getToken());
            editor.putString("QNUPToken", response.getResult().getQNUPToken());
            editor.putString("userID", response.getResult().getUserID());
            editor.putString("realName", response.getResult().getRealName());
            editor.putString("nickName", response.getResult().getNickName());
            editor.putString("headImageURL", response.getResult().getHeadImageURL());
            editor.putString("currentClubID", response.getResult().getCurrentClubID());
            editor.putString("currentClubName", response.getResult().getCurrentClubName());
            editor.putString("address", response.getResult().getAddress());
            editor.putString("age", response.getResult().getAge());
            editor.putString("phoneNumber", response.getResult().getPhoneNumber());
            editor.putString("sex", response.getResult().getSex());
            editor.putString("fitTarget", response.getResult().getFitTarget());
            editor.putString("defaultmembercard", response.getResult().getDefaultmembercard());
            editor.putString("userName", response.getResult().getUserName());
            editor.putString("QNDownToken", response.getResult().getQNDownToken());
            editor.putString("imToken", response.getResult().getImToken());
            editor.commit();
            if (response.getResult().getToken() != null) {
                Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                startActivity(intent);
                GuidePageActivity.this.finish();
            }
        }
    }

    @Override
    public void onError(Throwable ex) {

    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }

    @Override
    public void go() {
        //打开浏览器去更新
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(appurl);
        intent.setData(content_url);
        startActivity(intent);
    }

    //获取apk版本号
    public String getVersionName() {
        String VersionName = "1.0";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            VersionName = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return VersionName;
    }

    public void showdialog() {
        if (fragment == null) {
            fragment = new GuidePageDialogFragment();
        }
        fragment.show(getSupportFragmentManager(), "fragment");
    }

}
