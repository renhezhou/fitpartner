package rxh.shanks.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.presenter.ConversationListPresenter;
import rxh.shanks.view.ConversationListView;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ConversationListActivity extends BaseActivity implements ConversationListView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.dialogue)
    TextView dialogue;
    @Bind(R.id.system)
    TextView system;
    @Bind(R.id.conversationlist_ll)
    LinearLayout conversationlist_ll;
    @Bind(R.id.system_ll)
    LinearLayout system_ll;

    ConversationListPresenter conversationListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationListPresenter = new ConversationListPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.conversationlist);
        ButterKnife.bind(this);
        title.setText("消息");
        back.setOnClickListener(this);
        dialogue.setOnClickListener(this);
        system.setOnClickListener(this);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                return conversationListPresenter.getUserInfo(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
        selected(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.dialogue:
                selected(0);

                break;
            case R.id.system:
                selected(1);

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
            conversationlist_ll.setVisibility(View.VISIBLE);
            system_ll.setVisibility(View.GONE);
        } else if (i == 1) {
            system.setTextColor(getResources().getColor(R.color.white));
            system.setBackgroundResource(R.drawable.activity_information_system_selected);
            dialogue.setTextColor(getResources().getColor(R.color.red));
            dialogue.setBackgroundResource(R.drawable.activity_information_dialogue_not_selected);
            conversationlist_ll.setVisibility(View.GONE);
            system_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getuserinfo(UserInfo userInfo) {
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }
}
