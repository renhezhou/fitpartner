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
import rxh.shanks.entity.FitnessCalendarEntity;
import rxh.shanks.entity.FitnessCalendarLessonEntity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FitnessCalendarAdapter extends BaseAdapter {
    Context context;
    private List<FitnessCalendarEntity> noteDatedata = new ArrayList<>();
    private List<FitnessCalendarLessonEntity> lessondata = new ArrayList<>();
    LayoutInflater mInflater;

    public FitnessCalendarAdapter(Context context, List<FitnessCalendarEntity> noteDatedata, List<FitnessCalendarLessonEntity> lessondata) {
        this.context = context;
        this.noteDatedata = noteDatedata;
        this.lessondata = lessondata;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (noteDatedata == null) {
            return lessondata.size();
        } else {
            return noteDatedata.size();
        }
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
                    R.layout.activity_fitness_calendar_lv_item, null);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.remarks = (TextView) convertView.findViewById(R.id.remarks);
            holder.body_length = (TextView) convertView.findViewById(R.id.body_length);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.dizhi = (ImageView) convertView.findViewById(R.id.dizhi);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (noteDatedata == null) {
            holder.time.setText(lessondata.get(position).getStartTime() + "-" + lessondata.get(position).getEndTime());
            holder.remarks.setText(lessondata.get(position).getCoachName());
            holder.body_length.setText(lessondata.get(position).getLessonName());
            holder.address.setText("");
            holder.dizhi.setVisibility(View.GONE);
        } else {
            holder.time.setText(noteDatedata.get(position).getStartTime() + "-" + lessondata.get(position).getEndTime());
            holder.remarks.setText("时长:" + noteDatedata.get(position).getTimelong() + "分钟");
            holder.body_length.setText("");
            holder.address.setText(noteDatedata.get(position).getAddress());
        }

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView time;
        public TextView remarks;
        public TextView body_length;
        public TextView address;
        public ImageView dizhi;
    }
}
