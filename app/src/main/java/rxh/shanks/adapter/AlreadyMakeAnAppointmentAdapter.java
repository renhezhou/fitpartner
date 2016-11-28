package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.SpringProgressView;
import rxh.shanks.entity.AlreadyMakeAnAppointmentEntity;
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.utils.ChangeUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AlreadyMakeAnAppointmentAdapter extends BaseAdapter {

    Context context;
    OnItemClickLitener mOnItemClickLitener;
    private List<AlreadyMakeAnAppointmentEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public interface OnItemClickLitener {
        void onItemClick(int flag, int position);//0,1电话，IM
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public AlreadyMakeAnAppointmentAdapter(Context context, List<AlreadyMakeAnAppointmentEntity> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // 如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = mInflater.inflate(
                    R.layout.fragment_already_make_an_appointment_lv_item, null);
            holder.lession_name = (TextView) convertView.findViewById(R.id.lession_name);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.next_class_ll = (LinearLayout) convertView.findViewById(R.id.next_class_ll);
            holder.next_class = (TextView) convertView.findViewById(R.id.next_class);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.call = (ImageView) convertView.findViewById(R.id.call);
            holder.send_message = (ImageView) convertView.findViewById(R.id.send_message);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.sp = (SpringProgressView) convertView.findViewById(R.id.sp);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lession_name.setText(data.get(position).getLessonName());
        holder.state.setText("详情");
        if (data.get(position).getNextLesson().getTime() != null) {
            holder.next_class_ll.setVisibility(View.VISIBLE);
            String[] a = data.get(position).getNextLesson().getTime().split(" ");
            holder.date.setText(a[0]);
            holder.time.setText(a[1]);
            holder.next_class.setText(data.get(position).getNextLesson().getCoachName());
            holder.address.setText(data.get(position).getNextLesson().getAddress());
        } else {
            holder.next_class_ll.setVisibility(View.GONE);
        }
        float sp = Float.parseFloat(data.get(position).getOrderCount()) / Float.parseFloat(data.get(position).getTotalCount());
        holder.sp.setMaxCount(100f);
        holder.sp.setCurrentCount(sp * 100);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(0, position);
            }
        });
        holder.send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(1, position);
            }
        });
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView lession_name;
        public TextView state;
        public LinearLayout next_class_ll;
        public TextView next_class;
        public TextView address;
        public ImageView call;
        public ImageView send_message;
        public TextView date;
        public TextView time;
        public SpringProgressView sp;
    }

}
