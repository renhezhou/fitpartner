package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class InformationActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.dialogue)
    TextView dialogue;
    @Bind(R.id.system)
    TextView system;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        title.setText("消息");
        back.setOnClickListener(this);
        dialogue.setOnClickListener(this);
        system.setOnClickListener(this);
        selected(0);
        //selectFragment(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.dialogue:
                selected(0);
//                selectFragment(0);
                break;
            case R.id.system:
                selected(1);
//                selectFragment(1);
                break;
            default:
                break;
        }
    }

    public void selected(int i) {
        if (i == 0) {
            dialogue.setTextColor(getResources().getColor(R.color.white));
            dialogue.setBackgroundResource(R.drawable.activity_information_dialogue_selected);
            system.setTextColor(getResources().getColor(R.color.red));
            system.setBackgroundResource(R.drawable.activity_information_system_not_selected);
        } else if (i == 1) {
            system.setTextColor(getResources().getColor(R.color.white));
            system.setBackgroundResource(R.drawable.activity_information_system_selected);
            dialogue.setTextColor(getResources().getColor(R.color.red));
            dialogue.setBackgroundResource(R.drawable.activity_information_dialogue_not_selected);
        }
    }
}
