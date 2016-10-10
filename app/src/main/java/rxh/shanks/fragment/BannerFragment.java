package rxh.shanks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rxh.hb.banner.ConvenientBanner;
import rxh.hb.banner.holder.CBViewHolderCreator;
import rxh.hb.banner.holder.Holder;
import rxh.hb.banner.listener.OnItemClickListener;
import rxh.shanks.activity.R;
import rxh.shanks.entity.CurriculumEventBusEntity;

/**
 * Created by Administrator on 2016/4/25.
 * 首页顶部导航栏
 */
public class BannerFragment extends Fragment {

    private View view;
    ConvenientBanner convenientBanner;
    private List<String> urls = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenient_banner);
        initview();
        // 注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);// 反注册EventBus
    }

    public void onEventMainThread(CurriculumEventBusEntity curriculumEventBusEntity) {
        convenientBanner.notifyDataSetChanged();
        urls = curriculumEventBusEntity.getUrls();
        initview();
    }

    public void initview() {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, urls)
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

    //开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    //停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    public class NetworkImageHolderView implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            //在这儿对imageview进行加载
//            Glide
//                    .with(context)
//                    .load(urls.get(position))
//                    .centerCrop()
//                    // .placeholder(R.drawable.ic_launcher)
//                    .crossFade()
//                    .into(imageView);
            Picasso.with(context)
                    .load(urls.get(position))
//                    .placeholder(R.drawable.loading_cort)
//                    .error(R.drawable.loading_cort)
                    .fit()
                    .into(imageView);
        }
    }

}
