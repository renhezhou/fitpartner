package rxh.shanks.adapter;

import android.annotation.SuppressLint;
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
import rxh.shanks.entity.IntformationEntity;
import rxh.shanks.entity.SystemLVEntity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IntformationAdapter extends BaseAdapter {

    Context context;
    private List<SystemLVEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public IntformationAdapter(Context context, List<SystemLVEntity> data) {
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

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // 如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = mInflater.inflate(
                    R.layout.activity_information_lv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data.get(position).getType().equals("上课提醒")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.shangketixing));
        } else if (data.get(position).getType().equals("教练代约团课")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.jiaoliandaiyuetuanke));
        } else if (data.get(position).getType().equals("教练代约私教课")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.jiaoliandaiyuesijiaoke));
        } else if (data.get(position).getType().equals("会员卡即将到期")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.huiyuankadaoqi));
        } else if (data.get(position).getType().equals("新增优惠券")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.xinzhengyouhuiquan));
        } else if (data.get(position).getType().equals("课程扣除提醒")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.kechengkouchutixing));
        } else if (data.get(position).getType().equals("场馆通知")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.changguantongzhi));
        } else if (data.get(position).getType().equals("场馆活动")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.changguanhuodong));
        } else if (data.get(position).getType().equals("系统通知")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.xitongtongzhi));
        }
        holder.title.setText(data.get(position).getType());
        holder.time.setText(data.get(position).getTime());
        holder.content.setText(data.get(position).getContent());
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView time;
        public TextView content;
    }

}
