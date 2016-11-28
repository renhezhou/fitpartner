package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.SpringProgressView;
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
            holder.lession_name = (TextView) convertView.findViewById(R.id.lession_name);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.speed_of_progress = (TextView) convertView.findViewById(R.id.speed_of_progress);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.sp = (SpringProgressView) convertView.findViewById(R.id.sp);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lession_name.setText(data.get(position).getLessonName());
        holder.address.setText(data.get(position).getAddress());
        holder.speed_of_progress.setText(data.get(position).getOrderCount() + "/" + data.get(position).getTotalCount());
        float sp = Float.parseFloat(data.get(position).getOrderCount()) / Float.parseFloat(data.get(position).getTotalCount());
        holder.sp.setMaxCount(100f);
        holder.sp.setCurrentCount(sp * 100);

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView lession_name;
        public TextView state;
        public TextView speed_of_progress;
        public TextView address;
        public SpringProgressView sp;
    }

}
