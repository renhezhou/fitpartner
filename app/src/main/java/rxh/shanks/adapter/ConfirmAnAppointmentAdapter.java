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
import rxh.shanks.customview.SmoothCheckBox;
import rxh.shanks.entity.ConfirmAnAppointmentEntity;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ConfirmAnAppointmentAdapter extends BaseAdapter {

    Context context;
    private List<ConfirmAnAppointmentEntity> data = new ArrayList<>();
    List<String> selectflag = new ArrayList<>();
    LayoutInflater mInflater;

    public ConfirmAnAppointmentAdapter(Context context, List<ConfirmAnAppointmentEntity> data, List<String> selectflag) {
        this.context = context;
        this.data = data;
        this.selectflag = selectflag;
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
                    R.layout.fragment_confirm_an_appointment_lv_item, null);
            holder.class_time = (TextView) convertView.findViewById(R.id.class_time);
            holder.reservation_number = (TextView) convertView.findViewById(R.id.reservation_number);
            holder.select = (ImageView) convertView.findViewById(R.id.select);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.class_time.setText(data.get(position).getTime());
        holder.reservation_number.setText("已约人数:" + data.get(position).getOrderPeople() + "/" + data.get(position).getMaxPeople());
        //1表示选中，0表示未选中
        if (selectflag.get(position).equals("1")) {
            holder.select.setVisibility(View.VISIBLE);
        } else if (selectflag.get(position).equals("0")) {
            holder.select.setVisibility(View.GONE);
        }
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView class_time;
        public TextView reservation_number;
        public ImageView select;
    }

}
