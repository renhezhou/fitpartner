//package rxh.shanks.fragment;
//
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
//import com.aspsine.swipetoloadlayout.OnRefreshListener;
//import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import rxh.shanks.activity.R;
//import rxh.shanks.adapter.CoachAdapter;
//import rxh.shanks.adapter.IntformationAdapter;
//import rxh.shanks.customview.ListViewForScrollView;
//import rxh.shanks.entity.CoachEntity;
//import rxh.shanks.entity.IntformationEntity;
//import rxh.shanks.entity.SystemLVEntity;
//import rxh.shanks.utils.CheckUtils;
//
///**
// * Created by Administrator on 2016/8/2.
// * 对话fragment
// */
//public class DialogueFragment extends Fragment {
//
//    View view;
//    SlideAndDragListView lv;
//    private List<SystemLVEntity> data = new ArrayList<>();
//    IntformationAdapter intformationAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_dialogue, null);
//        initview();
//        return view;
//    }
//
//    public void initview() {
//        lv = (SlideAndDragListView) view.findViewById(R.id.lv);
//        Menu menu = new Menu(true, false, 0);//第1个参数表示在拖拽的时候 item 的背景是否透明，第2个参数表示滑动item是否能滑的过头，像弹簧那样(true表示过头，就像Gif中显示的那样；false表示不过头，就像Android QQ中的那样)
//        menu.addItem(new MenuItem.Builder().setWidth(CheckUtils.dp2px(getActivity(), 120))//set Width
//                .setBackground(new ColorDrawable(Color.RED))// set background
//                .setDirection(MenuItem.DIRECTION_RIGHT)//设置方向 (默认方向为DIRECTION_LEFT)
//                .setText("One")//set text string
//                .setTextColor(Color.WHITE)//set text color
//                .setTextSize(20)//set text size
//                .build());
////set in sdlv
//        lv.setMenu(menu);
//        intformationAdapter = new IntformationAdapter(getActivity(), data);
//        lv.setAdapter(intformationAdapter);
//        lv.setOnSlideListener(new SlideAndDragListView.OnSlideListener() {
//            @Override
//            public void onSlideOpen(View view, View parentView, int position, int direction) {
//
//            }
//
//            @Override
//            public void onSlideClose(View view, View parentView, int position, int direction) {
//
//            }
//        });
//        lv.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
//            @Override
//            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
//                Toast.makeText(getActivity(), "点击了" + String.valueOf(itemPosition), Toast.LENGTH_LONG).show();
//                return Menu.ITEM_SCROLL_BACK;
//            }
//        });
//        intformationAdapter = new IntformationAdapter(getActivity(), data);
//        lv.setAdapter(intformationAdapter);
//
//
//    }
//
//}
