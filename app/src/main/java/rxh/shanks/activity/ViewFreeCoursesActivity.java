package rxh.shanks.activity;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.VFCEntity;
import rxh.shanks.adapter.ViewFreeCoursesPageAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.ViewFreeCoursesEntity;
import rxh.shanks.fragment.CalendarFragment;
import rxh.shanks.presenter.ViewFreeCoursesActivityPresenter;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ViewFreeCoursesActivityView;

/**
 * Created by Administrator on 2016/8/16.
 * 免费课程
 */
public class ViewFreeCoursesActivity extends BaseActivity implements OnDismissListener, CalendarFragment.CalendarFragmentListener, ViewFreeCoursesActivityView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.calendar)
    ImageView calendar;
    @Bind(R.id.club_name)
    TextView club_name;
    @Bind(R.id.more)
    RelativeLayout more;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    View popView;
    private PopupWindow popupWindow;
    private ListView pop_lv;
    String club_name_flag, club_id;

    CalendarFragment calendarFragment;
    ViewFreeCoursesPageAdapter viewFreeCoursesPageAdapter;
    ViewFreeCoursesAdapter viewFreeCoursesAdapter;
    ViewFreeCoursesActivityPresenter presenter;
    private List<ViewFreeCoursesEntity> data = new ArrayList<>();
    private List<String> datelist = new ArrayList<>();
    private List<String> seclet = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ViewFreeCoursesActivityPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_view_free_courses);
        ButterKnife.bind(this);
        initPopupWindow();
        title.setText("免费课程");
        club_name.setText(MyApplication.currentClubName);
        club_name_flag = MyApplication.currentClubName;
        club_id = MyApplication.currentClubID;
        back.setOnClickListener(this);
        calendar.setOnClickListener(this);
        more.setOnClickListener(this);
        datelist = CreatTime.creat(new Date());
        seclet = CreatTime.seclet(new Date());
        viewFreeCoursesPageAdapter = new ViewFreeCoursesPageAdapter(getSupportFragmentManager(), this, datelist, seclet, club_id);
        viewpager.setAdapter(viewFreeCoursesPageAdapter);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
        pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                club_name_flag = data.get(position).getClubName();
                club_name.setText(data.get(position).getClubName());
                club_id = data.get(position).getClubID();
                EventBus.getDefault().post(new VFCEntity(club_id));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.calendar:
                showcalendar();
                break;
            case R.id.more:
                presenter.getBortherClubShop();
                break;
            default:
                break;
        }
    }


    public void showcalendar() {
        if (calendarFragment == null) {
            calendarFragment = new CalendarFragment();
        }
        calendarFragment.show(getSupportFragmentManager(), null);
    }

    //选中了日历日期触发此事件
    @Override
    public void checkdate(String date) {
        datelist.clear();
        seclet.clear();
        datelist = CreatTime.creat(CreatTime.conversion(date));
        seclet = CreatTime.seclet(CreatTime.conversion(date));
        viewFreeCoursesPageAdapter = new ViewFreeCoursesPageAdapter(getSupportFragmentManager(), this, datelist, seclet, club_id);
        viewpager.setAdapter(viewFreeCoursesPageAdapter);
    }

    @Override
    public void getBortherClubShop(List<ViewFreeCoursesEntity> viewFreeCoursesEntityList) {
        // 为popWindow添加动画效果
        // 点击弹出泡泡窗口
        backgroundAlpha(0.5f);
        popupWindow.showAsDropDown(more);
        data.clear();
        data = viewFreeCoursesEntityList;
        viewFreeCoursesAdapter = new ViewFreeCoursesAdapter();
        pop_lv.setAdapter(viewFreeCoursesAdapter);
    }


    public class ViewFreeCoursesAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public ViewFreeCoursesAdapter() {
            mInflater = LayoutInflater.from(getApplicationContext());
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            // 如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                // 根据自定义的Item布局加载布局
                convertView = mInflater.inflate(
                        R.layout.activity_view_free_courses_pop_window_lv_item, null);
                holder.club_name = (TextView) convertView.findViewById(R.id.club_name);
                holder.select = (ImageView) convertView.findViewById(R.id.select);
                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.club_name.setText(data.get(position).getClubName());
            if (club_name_flag.equals(data.get(position).getClubName())) {
                holder.select.setVisibility(View.VISIBLE);
            } else {
                holder.select.setVisibility(View.GONE);
            }

            return convertView;
        }

        // ViewHolder静态类
        class ViewHolder {
            public TextView club_name;
            public ImageView select;
        }
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        popView = getLayoutInflater().inflate(R.layout.activity_view_free_courses_pop_window,
                null);
        pop_lv = (ListView) popView.findViewById(R.id.pop_lv);
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);

    }

}
