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
import rxh.shanks.customview.SpringProgressView;
import rxh.shanks.entity.MyPrivateTeachingAppointmentEntity;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MyPrivateTeachingAppointmentLVAdapter extends BaseAdapter {

    Context context;
    private List<MyPrivateTeachingAppointmentEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public MyPrivateTeachingAppointmentLVAdapter(Context context, List<MyPrivateTeachingAppointmentEntity> data) {
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
                    R.layout.activity_my_private_teaching_appointment_lv_item, null);
            holder.lession_name = (TextView) convertView.findViewById(R.id.lession_name);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.lession_details = (TextView) convertView.findViewById(R.id.lession_details);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.speed_of_progress = (TextView) convertView.findViewById(R.id.speed_of_progress);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.sp = (SpringProgressView) convertView.findViewById(R.id.sp);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //                        Html.fromHtml("还款金额"
//                                + "<font color=red>"
//                                + lvBeans.get(position).getRepayment_amount() + "</font>元")
        holder.lession_name.setText(data.get(position).getLession_name());
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView lession_name;
        public TextView state;
        public TextView lession_details;
        public TextView address;
        public TextView date;
        public TextView speed_of_progress;
        private TextView time;
        public SpringProgressView sp;
    }

}
