package rxh.shanks.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.activity.ViewAppointmentActivity;
import rxh.shanks.entity.AlreadyMakeAnAppointmentEntity;
import rxh.shanks.entity.SystemLVEntity;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SystemNextAdapter extends BaseAdapter {


    Context context;
    private List<SystemLVEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public SystemNextAdapter(Context context, List<SystemLVEntity> data) {
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
                    R.layout.activity_system_next_lv_item, null);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.to_confirm = (Button) convertView.findViewById(R.id.to_confirm);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(data.get(position).getTime());
        holder.content.setText(data.get(position).getContent());
        holder.to_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ViewAppointmentActivity.class);
                intent.putExtra("lessonID", data.get(position).getLessonID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView time;
        public TextView content;
        public Button to_confirm;
    }

}
