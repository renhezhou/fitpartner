package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.RatingBar;
import rxh.shanks.entity.CoachEntity;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CoachAdapter extends BaseAdapter {


    Context context;
    private List<CoachEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public CoachAdapter(Context context, List<CoachEntity> data) {
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
                    R.layout.fragment_coach_lv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.coach_ratingbar = (RatingBar) convertView.findViewById(R.id.coach_ratingbar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.project_name = (TextView) convertView.findViewById(R.id.project_name);
            holder.amount_of_money = (TextView) convertView.findViewById(R.id.amount_of_money);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data.get(position).getCoverImageArray() != null) {
            Glide
                    .with(context)
                    .load(data.get(position).getCoverImageArray().get(0))
                    .centerCrop()
//                    .placeholder(R.drawable.ic_launcher)
                    .crossFade()
                    .into(holder.img);
        }
        holder.name.setText(data.get(position).getName());
        holder.title.setText("执教" + data.get(position).getTeachTime() + "年");
        holder.project_name.setText(data.get(position).getIntroduction());
        holder.amount_of_money.setText("¥" + data.get(position).getMinimunPrice() + "元起");
        if (data.get(position).getEvaluate() != null) {
            holder.coach_ratingbar.setStar(Float.parseFloat(data.get(position).getEvaluate()));
        }
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public RatingBar coach_ratingbar;
        public TextView name;
        public TextView title;
        public TextView project_name;
        public TextView amount_of_money;
    }
}
