package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.EAEntity;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.presenter.EvaluatePresenter;
import rxh.shanks.view.EvaluateView;

/**
 * Created by Administrator on 2016/8/2.
 * 评分Activity
 */
public class EvaluateActivity extends BaseActivity implements EvaluateView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.project)
    TextView project;
    @Bind(R.id.submit_evaluation)
    Button submit_evaluation;
    @Bind(R.id.curriculum_ratingbar)
    RatingBar curriculum_ratingbar;
    @Bind(R.id.coach_ratingbar)
    RatingBar coach_ratingbar;
    String coachID, appointmentID;//教练ID,预约ID\
    EvaluatePresenter presenter;
    String curriculum_num = null, coach_num = null, head_path, nametv;
    @Bind(R.id.pingjia)
    EditText pingjia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EvaluatePresenter(this);
        coachID = getIntent().getStringExtra("coachID");
        appointmentID = getIntent().getStringExtra("appointmentID");
        head_path = getIntent().getStringExtra("head_path");
        nametv = getIntent().getStringExtra("name");
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        title.setText("评价");
        Picasso.with(getApplicationContext())
                .load(head_path)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        project.setText(nametv);
        back.setOnClickListener(this);
        submit_evaluation.setOnClickListener(this);
        //课程评分
        curriculum_ratingbar.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        curriculum_num = String.valueOf(RatingCount);
                    }
                }
        );

        //教练评分
        coach_ratingbar.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        coach_num = String.valueOf(RatingCount);
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit_evaluation:
                presenter.makeEvaluate(coachID, appointmentID, curriculum_num, coach_num, pingjia.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("提交中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void toast(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success() {
        EventBus.getDefault().post(new EAEntity());
        finish();
    }
}
