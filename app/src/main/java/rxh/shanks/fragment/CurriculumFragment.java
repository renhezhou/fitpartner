package rxh.shanks.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import rxh.shanks.EBEntity.SVAEntity;
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

    View view, popView, headView;
    private PopupWindow popupWindow;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView lv;
    private TextView peopleandtime, more, club_name, club_profile, name, view_free_courses, booking_group_course;
    private ImageView add, dadianhua, chat;
    private CircleImageView head_portrait;
    private LinearLayout scan, check_to;

    private List<BrandGymPaidEntity> data = new ArrayList<>();
    CurriculumAdapter adapter;
    CurriculumPresenter presenter;

    List<BrandInfoEntity> brandInfoEntityList = new ArrayList<>();
    ConsultantEntity consultantEntity = new ConsultantEntity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new CurriculumPresenter(this, this, this);
        headView = inflater.inflate(R.layout.fragment_curriculum_header, null);
        view = inflater.inflate(R.layout.fragment_curriculum, null);
        EventBus.getDefault().register(this);
        initview();
        initdata();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(SVAEntity svaEntity) {
        initdata();
    }

    public void initview() {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        lv = (ListView) view.findViewById(R.id.swipe_target);
        lv.setFocusable(false);
        lv.addHeaderView(headView);
        add = (ImageView) view.findViewById(R.id.add);
        dadianhua = (ImageView) headView.findViewById(R.id.dadianhua);
        chat = (ImageView) headView.findViewById(R.id.chat);
        club_name = (TextView) headView.findViewById(R.id.club_name);
        club_profile = (TextView) headView.findViewById(R.id.club_profile);
        more = (TextView) headView.findViewById(R.id.more);
        peopleandtime = (TextView) headView.findViewById(R.id.peopleandtime);
        head_portrait = (CircleImageView) headView.findViewById(R.id.head_portrait);
        name = (TextView) headView.findViewById(R.id.name);
        view_free_courses = (TextView) headView.findViewById(R.id.view_free_courses);
        booking_group_course = (TextView) headView.findViewById(R.id.booking_group_course);
        add.setOnClickListener(this);
        dadianhua.setOnClickListener(this);
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
        presenter.getBrandGymPaid();
        presenter.getConsultant();
        presenter.getBrandInfo();
    }

    @Override
    public void onLoadMore() {
        //swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        initdata();
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
            case R.id.dadianhua:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + consultantEntity.getPhone()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getBrandGymPaid(List<BrandGymPaidEntity> brandGymPaidEntityList) {
        data.clear();
        data = brandGymPaidEntityList;
        adapter = new CurriculumAdapter(getActivity(), data);
        lv.setAdapter(adapter);

    }

    //会计顾问
    @Override
    public void getConsultant(ConsultantEntity consultantEntity) {
        this.consultantEntity = consultantEntity;
        Picasso.with(getActivity())
                .load(consultantEntity.getPic())
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        name.setText(MyApplication.nickName);
        club_name.setText(MyApplication.currentClubName);
        name.setText(consultantEntity.getName());
    }

    @Override
    public void show() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hide() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    @Override
    public void getBrandInfo(List<BrandInfoEntity> brandInfoEntity) {
        brandInfoEntityList.clear();
        brandInfoEntityList = brandInfoEntity;
        club_name.setText(brandInfoEntityList.get(0).getClubName());
        if (brandInfoEntityList.get(0).getClubIntroduce() != null) {
            club_profile.setText(brandInfoEntityList.get(0).getClubIntroduce());
        }
        peopleandtime.setText(brandInfoEntityList.get(0).getOnlineNum() + "人  " + CheckUtils.timetodate(brandInfoEntityList.get(0).getNowTime()));
        EventBus.getDefault().post(new CurriculumEventBusEntity(brandInfoEntityList.get(0).getImageArrayTrans()));
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


}
