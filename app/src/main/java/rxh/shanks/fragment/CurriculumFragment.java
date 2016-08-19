package rxh.shanks.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import rxh.shanks.activity.CheckToActivity;
import rxh.shanks.activity.CourseDetailsActivity;
import rxh.shanks.activity.R;
import rxh.shanks.activity.ScanActivity;
import rxh.shanks.activity.VenueIntroductionActivity;
import rxh.shanks.activity.ViewFreeCoursesActivity;
import rxh.shanks.adapter.CurriculumAdapter;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.entity.BrandGymPaidEntity;
import rxh.shanks.entity.BrandInfoEntity;
import rxh.shanks.entity.ConsultantEntity;
import rxh.shanks.entity.CurriculumEntity;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.presenter.CurriculumPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.GetBrandGymPaidView;
import rxh.shanks.view.GetBrandInfoView;
import rxh.shanks.view.GetConsultantView;

/**
 * Created by Administrator on 2016/7/29.
 * 课程
 */
public class CurriculumFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener, OnDismissListener, GetBrandGymPaidView, GetConsultantView, GetBrandInfoView {

    View view, popView;
    private PopupWindow popupWindow;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ScrollView scrollView;
    private ListViewForScrollView lv;
    private TextView peopleandtime, more, club_name, club_profile, name, view_free_courses, booking_group_course;
    private ImageView add, chat;
    private CircleImageView head_portrait;
    private LinearLayout scan, check_to;
    private List<BrandGymPaidEntity> data = new ArrayList<>();
    CurriculumAdapter curriculumAdapter;
    CurriculumPresenter curriculumPresenter;

    List<BrandInfoEntity> brandInfoEntityList = new ArrayList<>();
    ConsultantEntity consultantEntity = new ConsultantEntity();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curriculumPresenter = new CurriculumPresenter(this, this, this);
        view = inflater.inflate(R.layout.fragment_curriculum, null);
        initview();
        initdata();
        return view;
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        scrollView = (ScrollView) view.findViewById(R.id.swipe_target);
        scrollView.smoothScrollTo(0, 0);
        lv = (ListViewForScrollView) view.findViewById(R.id.lv);
        lv.setFocusable(false);
        add = (ImageView) view.findViewById(R.id.add);
        chat = (ImageView) view.findViewById(R.id.chat);
        club_name = (TextView) view.findViewById(R.id.club_name);
        club_profile = (TextView) view.findViewById(R.id.club_profile);
        more = (TextView) view.findViewById(R.id.more);
        peopleandtime = (TextView) view.findViewById(R.id.peopleandtime);
        head_portrait = (CircleImageView) view.findViewById(R.id.head_portrait);
        name = (TextView) view.findViewById(R.id.name);
        view_free_courses = (TextView) view.findViewById(R.id.view_free_courses);
        booking_group_course = (TextView) view.findViewById(R.id.booking_group_course);
        add.setOnClickListener(this);
        chat.setOnClickListener(this);
        more.setOnClickListener(this);
        view_free_courses.setOnClickListener(this);
        booking_group_course.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CheckUtils.isinstallbyread("com.baidu.BaiduMap")) {
                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent intent = new Intent();
                    intent = packageManager.getLaunchIntentForPackage("com.baidu.BaiduMap");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "没有安装百度地图客户端", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void initdata() {
        curriculumPresenter.getBrandGymPaid();
        curriculumPresenter.getConsultant();
        curriculumPresenter.getBrandInfo();
    }


    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                initPopupWindow();
                backgroundAlpha(0.5f);
                // 为popWindow添加动画效果
                // 点击弹出泡泡窗口
                popupWindow.showAsDropDown(add);
                break;
            case R.id.scan:
                startActivity(new Intent(getActivity(), ScanActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.check_to:
                startActivity(new Intent(getActivity(), CheckToActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.more:
                Intent moreintent = new Intent();
                moreintent.setClass(getActivity(), VenueIntroductionActivity.class);
                moreintent.putExtra("ClubIntroduce", brandInfoEntityList.get(0).getClubIntroduce());
                startActivity(moreintent);
                break;
            case R.id.view_free_courses:
                Intent coursesintent = new Intent();
                coursesintent.setClass(getActivity(), ViewFreeCoursesActivity.class);
                startActivity(coursesintent);
                break;
            case R.id.booking_group_course:
                Intent bookingintent = new Intent();
                bookingintent.putExtra("club_name", brandInfoEntityList.get(0).getClubName());
                bookingintent.setClass(getActivity(), CourseDetailsActivity.class);
                startActivity(bookingintent);
                break;
            case R.id.chat:
                //启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(getActivity(), consultantEntity.getIdsales(), consultantEntity.getName());
                break;
            default:
                break;
        }
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        popView = getActivity().getLayoutInflater().inflate(R.layout.activity_main_pop_window,
                null);
        scan = (LinearLayout) popView.findViewById(R.id.scan);
        check_to = (LinearLayout) popView.findViewById(R.id.check_to);
        scan.setOnClickListener(this);
        check_to.setOnClickListener(this);
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
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
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
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

    //调起百度地图
    private void openBaiDuMap() {
        try {
            Intent intent = Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content=美年广场&src=yourCompanyName|fitpartner#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (CheckUtils.isinstallbyread("com.baidu.BaiduMap")) {
                startActivity(intent); //启动调用
            } else {
                Toast.makeText(getActivity(), "没有安装百度地图客户端", Toast.LENGTH_LONG).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //调起高德地图
    private void openGaoDeMap() {
        try {
            Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=厦门通&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
            if (CheckUtils.isinstallbyread("com.autonavi.minimap")) {
                startActivity(intent); //启动调用
            } else {
                Toast.makeText(getActivity(), "没有安装高德地图客户端", Toast.LENGTH_LONG).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBrandGymPaid(List<BrandGymPaidEntity> brandGymPaidEntityList) {
        data = brandGymPaidEntityList;
        curriculumAdapter = new CurriculumAdapter(getActivity(), data);
        lv.setAdapter(curriculumAdapter);
    }

    //会计顾问
    @Override
    public void getConsultant(ConsultantEntity consultantEntity) {
        this.consultantEntity = consultantEntity;
        Glide
                .with(getActivity())
                .load(consultantEntity.getPic())
                .centerCrop()
//                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(head_portrait);
        name.setText(consultantEntity.getName());
    }

    @Override
    public void getBrandInfo(List<BrandInfoEntity> brandInfoEntity) {
        brandInfoEntityList = brandInfoEntity;
        club_name.setText(brandInfoEntityList.get(0).getClubName());
        if (brandInfoEntityList.get(0).getClubIntroduce() != null) {
            club_profile.setText(brandInfoEntityList.get(0).getClubIntroduce());
        }
        peopleandtime.setText(brandInfoEntityList.get(0).getOnlineNum() + "人  " + CheckUtils.timetodate(brandInfoEntityList.get(0).getNowTime()));
        EventBus.getDefault().post(new CurriculumEventBusEntity(brandInfoEntityList.get(0).getImageArrayTrans()));
    }
}