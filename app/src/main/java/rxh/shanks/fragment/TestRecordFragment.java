package rxh.shanks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.hb.banner.ConvenientBanner;
import rxh.hb.banner.holder.CBViewHolderCreator;
import rxh.hb.banner.holder.Holder;
import rxh.hb.banner.listener.OnItemClickListener;
import rxh.shanks.activity.R;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.TestRecordEntity;
import rxh.shanks.entity.TestRecordEventBusEntity;
import rxh.shanks.utils.CheckUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class TestRecordFragment extends Fragment {

    private View view;
    ConvenientBanner convenientBanner;
    private List<TestRecordEntity> datas = new ArrayList<>();
    LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenient_banner);
        convenientBanner.setCanLoop(false);
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    public void onEventMainThread(TestRecordEventBusEntity testRecordEventBusEntity) {
        datas = testRecordEventBusEntity.getTestRecordEntityList();
        initview();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void initview() {

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas)
                //设置翻页指示器
                .setPageIndicator(new int[]{R.drawable.lunbozhishiqi, R.drawable.lunbozhishiqione})
                //设置翻页指示器位置
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    public class NetworkImageHolderView implements Holder<TestRecordEntity> {

        private View item_view;
        private TextView club_name, time, bmi, weight, body_fat_percentage, fat_mass, skeletal_muscle_content, basal_metabolic_rate;

        @Override
        public View createView(Context context) {
            mInflater = LayoutInflater.from(context);
            item_view = mInflater.inflate(R.layout.activity_test_record_item, null);
            club_name = (TextView) item_view.findViewById(R.id.club_name);
            time = (TextView) item_view.findViewById(R.id.time);
            bmi = (TextView) item_view.findViewById(R.id.bmi);
            weight = (TextView) item_view.findViewById(R.id.weight);
            body_fat_percentage = (TextView) item_view.findViewById(R.id.body_fat_percentage);
            fat_mass = (TextView) item_view.findViewById(R.id.fat_mass);
            skeletal_muscle_content = (TextView) item_view.findViewById(R.id.skeletal_muscle_content);
            basal_metabolic_rate = (TextView) item_view.findViewById(R.id.basal_metabolic_rate);
            return item_view;
        }

        @Override
        public void UpdateUI(Context context, int position, TestRecordEntity data) {
            club_name.setText(datas.get(position).getClubName());
            time.setText(CheckUtils.timetodate(datas.get(position).getRecordTime()));
            bmi.setText(datas.get(position).getBmi());
            weight.setText(datas.get(position).getWeight() + "kg");
            body_fat_percentage.setText(datas.get(position).getBodyFat() + "%");
            fat_mass.setText(datas.get(position).getFatmass() + "kg");
            skeletal_muscle_content.setText(datas.get(position).getSkeletal() + "kg");
            basal_metabolic_rate.setText(datas.get(position).getBmr() + "Kcal");
        }
    }

}
