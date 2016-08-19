package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.entity.ViewAppointmentEntity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ViewAppointmentAdapter extends BaseAdapter {


    Context context;
    private List<ViewAppointmentEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public ViewAppointmentAdapter(Context context, List<ViewAppointmentEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
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
                    R.layout.activity_view_appointment_lv_item, null);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.project = (TextView) convertView.findViewById(R.id.project);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.date.setText(data.get(position).getLessonData());
        holder.time.setText(data.get(position).getLessonTime());
        holder.project.setText(data.get(position).getLessonName());
        //00:完成未评价  01:完成已评价  10:预约中 11:代约中
        if (data.get(position).getState().equals("00")) {
            holder.state.setText("完成未评价");
        } else if (data.get(position).getState().equals("01")) {
            holder.state.setText("完成已评价");
        } else if (data.get(position).getState().equals("10")) {
            holder.state.setText("预约中");
        } else if (data.get(position).getState().equals("11")) {
            holder.state.setText("代约中");
        }

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView date;
        public TextView time;
        public TextView project;
        public TextView state;
    }
}
