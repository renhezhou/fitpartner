package rxh.shanks.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.entity.ConfirmTheAppointmentOfPrivateEducationEntity;
import rxh.shanks.utils.ChangeUtils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class ConfirmTheAppointmentOfPrivateEducationLVAdapter extends BaseAdapter {

    int selected_position = 0;
    Context context;
    OnItemClickLitener mOnItemClickLitener;
    private List<ConfirmTheAppointmentOfPrivateEducationEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public interface OnItemClickLitener {
        void onItemClick(int flag, int position);//0,1,2分别代表头像，电话，IM
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ConfirmTheAppointmentOfPrivateEducationLVAdapter(Context context, List<ConfirmTheAppointmentOfPrivateEducationEntity> data) {
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
                    R.layout.activity_confirm_the_appointment_of_private_education_lv_item, null);
            holder.head_portrait = (CircleImageView) convertView.findViewById(R.id.head_portrait);
            holder.call = (ImageView) convertView.findViewById(R.id.call);
            holder.send_message = (ImageView) convertView.findViewById(R.id.send_message);
            holder.coach_name = (TextView) convertView.findViewById(R.id.coach_name);
            holder.coaching_time = (TextView) convertView.findViewById(R.id.coaching_time);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (selected_position == position) {
            holder.head_portrait.setBorderColor(Color.parseColor("#FC6419"));
        } else {
            holder.head_portrait.setBorderColor(Color.parseColor("#ffffff"));
        }
        Picasso.with(context)
                .load(data.get(position).getHeadImageURL())
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(holder.head_portrait);
        holder.coach_name.setText(data.get(position).getName());
        holder.coaching_time.setText("执教" + data.get(position).getTeachTime() + "年");
        holder.score.setText("评分:" + data.get(position).getEvaluate() + "分");
        holder.head_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                mOnItemClickLitener.onItemClick(0, selected_position);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(1, position);
            }
        });
        holder.send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(2, position);
            }
        });
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public CircleImageView head_portrait;
        public ImageView call;
        public ImageView send_message;
        public TextView coach_name;
        public TextView coaching_time;
        public TextView score;
    }
}
