package rxh.shanks.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/8/3.
 * 私教或者团课详情
 */
public class PrivateEducationOrLeagueDetailsActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.project)
    TextView project;
    @Bind(R.id.evaluate)
    RatingBar evaluate;
    @Bind(R.id.coach_name)
    TextView coach_name;
    @Bind(R.id.when_long)
    TextView when_long;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.introduce)
    TextView introduce;
    @Bind(R.id.consultation)
    TextView consultation;
    @Bind(R.id.purchase)
    TextView purchase;

    String logo, addresss, coachID, coachName, lessonIntro, lessonName, lessonID, evaluates, prices, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_private_education_or_league_details);
        ButterKnife.bind(this);
        title.setText("课程详情");
        back.setOnClickListener(this);
        consultation.setOnClickListener(this);
        purchase.setOnClickListener(this);
        if (logo != null && !logo.equals("")) {
            Picasso.with(getApplicationContext())
                    .load(logo)
                    .into(img);
        }
        project.setText(lessonName);
        if (evaluates != null) {
            evaluate.setStar(Float.parseFloat(evaluates));
        } else {
            evaluate.setStar(0f);
        }
        coach_name.setText(coachName);
        when_long.setText("时长：" + time + "分钟");
        price.setText(prices + "元/节");
        address.setText(addresss);
        introduce.setText(lessonIntro);
    }

    public void initdata() {
        logo = getIntent().getStringExtra("logo");
        addresss = getIntent().getStringExtra("address");
        coachID = getIntent().getStringExtra("coachID");
        coachName = getIntent().getStringExtra("coachName");
        lessonIntro = getIntent().getStringExtra("lessonIntro");
        lessonName = getIntent().getStringExtra("lessonName");
        lessonID = getIntent().getStringExtra("lessonID");
        evaluates = getIntent().getStringExtra("evaluate");
        prices = getIntent().getStringExtra("price");
        time = getIntent().getStringExtra("time");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.consultation:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("购课模块暂未开通，敬请期待。")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dismiss();
                            }
                        }).show();
                //启动会话界面
                //if (RongIM.getInstance() != null)
                //RongIM.getInstance().startPrivateChat(PrivateEducationOrLeagueDetailsActivity.this, MyApplication.CoachID, coachName);
                break;
            case R.id.purchase:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("购课模块暂未开通，敬请期待。")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dismiss();
                            }
                        }).show();
                break;
            default:
                break;
        }
    }
}
