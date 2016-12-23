package rxh.shanks.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.fragment.CoachFragment;
import rxh.shanks.fragment.CurriculumFragment;
import rxh.shanks.fragment.MyFragment;
import rxh.shanks.utils.MyApplication;

public class MainActivity extends BaseActivity {

    @Bind(R.id.curriculum_img)
    ImageView curriculum_img;
    @Bind(R.id.curriculum_tv)
    TextView curriculum_tv;
    @Bind(R.id.curriculum)
    LinearLayout curriculum;
    @Bind(R.id.coach_img)
    ImageView coach_img;
    @Bind(R.id.coach_tv)
    TextView coach_tv;
    @Bind(R.id.coach)
    LinearLayout coach;
    @Bind(R.id.my_img)
    ImageView my_img;
    @Bind(R.id.my_tv)
    TextView my_tv;
    @Bind(R.id.my)
    LinearLayout my;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    //课程，教练，我的fragment
    private CurriculumFragment curriculumFragment;
    private CoachFragment coachFragment;
    private MyFragment myFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化极光推送并设置tag
        JPushInterface.init(getApplicationContext());
        JPushInterface.setAlias(getApplicationContext(), MyApplication.userID, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        connect(MyApplication.imToken);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        curriculum.setOnClickListener(this);
        coach.setOnClickListener(this);
        my.setOnClickListener(this);
        setbg(0);
        selectFragment(0);
//        showpopupfragment();
    }

    public void selectFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (i) {
            case 0:// 课程
                if (curriculumFragment == null) {
                    curriculumFragment = new CurriculumFragment();
                    fragmentTransaction.add(R.id.fragment, curriculumFragment);
                } else {
                    fragmentTransaction.show(curriculumFragment);
                }
                break;

            case 1:// 教练
                if (coachFragment == null) {
                    coachFragment = new CoachFragment();
                    fragmentTransaction.add(R.id.fragment, coachFragment);
                } else {
                    fragmentTransaction.show(coachFragment);
                }
                break;

            case 2:// 我的
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.fragment, myFragment);
                } else {
                    fragmentTransaction.show(myFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (curriculumFragment != null) {
            fragmentTransaction.hide(curriculumFragment);
        }
        if (coachFragment != null) {
            fragmentTransaction.hide(coachFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setbg(int i) {
        if (i == 0) {
            curriculum_tv.setTextColor(this.getResources().getColor(R.color.red));
            coach_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            my_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            curriculum_img.setBackground(getResources().getDrawable(R.drawable.kechengdown));
            coach_img.setBackground(getResources().getDrawable(R.drawable.jiaolianup));
            my_img.setBackground(getResources().getDrawable(R.drawable.wodeup));
        } else if (i == 1) {
            curriculum_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            coach_tv.setTextColor(this.getResources().getColor(R.color.red));
            my_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            curriculum_img.setBackground(getResources().getDrawable(R.drawable.kechengup));
            coach_img.setBackground(getResources().getDrawable(R.drawable.jiaoliandown));
            my_img.setBackground(getResources().getDrawable(R.drawable.wodeup));
        } else if (i == 2) {
            curriculum_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            coach_tv.setTextColor(this.getResources().getColor(R.color.text_not_selected));
            my_tv.setTextColor(this.getResources().getColor(R.color.red));
            curriculum_img.setBackground(getResources().getDrawable(R.drawable.kechengup));
            coach_img.setBackground(getResources().getDrawable(R.drawable.jiaolianup));
            my_img.setBackground(getResources().getDrawable(R.drawable.wodedown));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.curriculum:
                setbg(0);
                selectFragment(0);
                break;
            case R.id.coach:
                setbg(1);
                selectFragment(1);
                break;
            case R.id.my:
                setbg(2);
                selectFragment(2);
                break;
            default:
                break;
        }
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

}
