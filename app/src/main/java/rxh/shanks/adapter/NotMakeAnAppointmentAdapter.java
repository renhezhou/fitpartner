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
import rxh.shanks.entity.NotMakeAnAppointmentEntity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class NotMakeAnAppointmentAdapter extends BaseAdapter {

    Context context;
    private List<NotMakeAnAppointmentEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public NotMakeAnAppointmentAdapter(Context context, List<NotMakeAnAppointmentEntity> data) {
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
                    R.layout.fragment_not_make_an_appointment_lv_item, null);
            holder.project = (TextView) convertView.findViewById(R.id.project);
            holder.number_of_remaining_appointments = (TextView) convertView.findViewById(R.id.number_of_remaining_appointments);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.project.setText(data.get(position).getLessonName());
        holder.number_of_remaining_appointments.setText("剩余可预约次数 " + data.get(position).getUnOrderCount() + "/" + data.get(position).getTotalCount());

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView project;
        public TextView number_of_remaining_appointments;
    }

}
