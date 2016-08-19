package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.FeedbackPresenter;
import rxh.shanks.view.FeedbackView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FeedbackActivity extends BaseActivity implements FeedbackView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.contact_information)
    EditText contact_information;
    @Bind(R.id.comments_or_suggestions)
    EditText comments_or_suggestions;
    @Bind(R.id.submit_feedback)
    Button submit_feedback;
    FeedbackPresenter feedbackPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackPresenter = new FeedbackPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        title.setText("意见反馈");
        submit_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit_feedback:
                feedbackPresenter.submitOpinion(contact_information.getText().toString(), comments_or_suggestions.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        loading("上传中...", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void response(String result) {
        finish();
    }

    @Override
    public void check(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
}
