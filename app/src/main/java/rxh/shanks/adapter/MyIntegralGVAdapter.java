package rxh.shanks.adapter;

import android.content.Context;
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
import rxh.shanks.entity.MyIntegralEntity;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MyIntegralGVAdapter extends BaseAdapter {


    Context context;
    private List<MyIntegralEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public MyIntegralGVAdapter(Context context, List<MyIntegralEntity> data) {
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
                    R.layout.activity_integral_gv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.integral = (TextView) convertView.findViewById(R.id.integral);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(data.get(position).getImg())
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(holder.img);

        holder.name.setText(data.get(position).getName());
        holder.integral.setText(data.get(position).getIntegral());
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView integral;
    }
}
