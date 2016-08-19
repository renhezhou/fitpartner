package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.entity.DataSouceCoachEntity;
import rxh.shanks.entity.PrivateEducationCourseEntity;

/**
 * Created by Administrator on 2016/8/3.
 */
//public class PrivateEducationCourseGVAdapter extends BaseAdapter {


//    Context context;
//    List<DataSouceCoachEntity> data = new ArrayList<>();
//    LayoutInflater mInflater;
//
//    public PrivateEducationCourseGVAdapter(Context context, List<DataSouceCoachEntity> data) {
//        this.context = context;
//        this.data = data;
//        mInflater = LayoutInflater.from(context);
//    }
//
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        // 如果缓存convertView为空，则需要创建View
//        if (convertView == null) {
//            holder = new ViewHolder();
//            // 根据自定义的Item布局加载布局
//            convertView = mInflater.inflate(
//                    R.layout.fragment_private_education_course_gv_item, null);
//            holder.time = (TextView) convertView.findViewById(R.id.time);
//            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.time.setText(data.get(position).getTime());
//        //0表示用户和教练都有空，1表示教练没空，2表示用户没空，3表示教练和用户都没空,4表示被选中
//        if (data.get(position).getFlag() == 0) {
//            holder.time.setBackgroundColor(context.getResources().getColor(R.color.white));
//            holder.time.setTextColor(context.getResources().getColor(R.color.red));
//        } else if (data.get(position).getFlag() == 1) {
//            holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
//            holder.time.setTextColor(context.getResources().getColor(R.color.white));
//        } else if (data.get(position).getFlag() == 2) {
//            holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
//            holder.time.setTextColor(context.getResources().getColor(R.color.white));
//        } else if (data.get(position).getFlag() == 4) {
//            holder.time.setBackgroundColor(context.getResources().getColor(R.color.red));
//            holder.time.setTextColor(context.getResources().getColor(R.color.white));
//        }
//
//        return convertView;
//    }
//
//    // ViewHolder静态类
//    static class ViewHolder {
//        public TextView time;
//    }

//}
