package rxh.shanks.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.adapter.CoachDetailsPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.DataEventBusEntity;
import rxh.shanks.fragment.DataFragment;
import rxh.shanks.fragment.GroupClassFragment;
import rxh.shanks.fragment.PrivateEducationFragment;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/8/3.
 * 教练详情
 */
public class CoachDetailsActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.coach_name)
    TextView coach_name;
    @Bind(R.id.coaching_years)
    TextView coaching_years;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.coach_ratingbar)
    RatingBar coach_ratingbar;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    String CoachID, name, descrip, teachtime, club_nametv, HeadImageURL, evaluate;
    private List<String> CoverImageArray = new ArrayList<>();

    private List<Fragment> fragments = new ArrayList<>();
    CoachDetailsPageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_coach_details);
        ButterKnife.bind(this);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
        title.setText("教练详情");
        back.setOnClickListener(this);
        adapter = new CoachDetailsPageAdapter(getSupportFragmentManager(), fragments, descrip);
        viewpager.setAdapter(adapter);
        Picasso.with(getApplicationContext())
                .load(HeadImageURL)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        coach_name.setText(name);
        coaching_years.setText("执教" + teachtime + "年");
        club_name.setText(club_nametv);
        if (evaluate != null) {
            coach_ratingbar.setStar(Float.parseFloat(evaluate));
        }
        EventBus.getDefault().post(new CurriculumEventBusEntity(CoverImageArray));
    }

    public void initdata() {
        CoachID = getIntent().getStringExtra("CoachID");
        MyApplication.CoachID = CoachID;
        name = getIntent().getStringExtra("name");
        teachtime = getIntent().getStringExtra("teachtime");
        club_nametv = getIntent().getStringExtra("club_name");
        HeadImageURL = getIntent().getStringExtra("HeadImageURL");
        evaluate = getIntent().getStringExtra("evaluate");
        CoverImageArray = getIntent().getStringArrayListExtra("CoverImageArray");
        descrip = getIntent().getStringExtra("descrip");
        fragments.add(new DataFragment());
        fragments.add(new PrivateEducationFragment());
        fragments.add(new GroupClassFragment());
        //EventBus.getDefault().post(new DataEventBusEntity(descrip));
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
