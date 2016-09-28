package rxh.shanks.adapter;

import android.content.Context;
import android.util.Log;
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
import rxh.shanks.entity.CourseDetailsEntity;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CourseDetailsAdapter extends BaseAdapter {


    Context context;
    private List<CourseDetailsEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public CourseDetailsAdapter(Context context, List<CourseDetailsEntity> data) {
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
                    R.layout.fragment_course_details_lv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.project = (TextView) convertView.findViewById(R.id.project);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.introduce = (TextView) convertView.findViewById(R.id.introduce);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.evaluate = (RatingBar) convertView.findViewById(R.id.evaluate);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide
                .with(context)
                .load(data.get(position).getLogo())
                .centerCrop()
                //  .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(holder.img);
        holder.project.setText(data.get(position).getCoachName());
        holder.time.setText("上课时长:" + data.get(position).getTime() + "分钟");
        holder.introduce.setText(data.get(position).getLessonIntro());
        holder.price.setText(data.get(position).getPrice() + "元/节");
        if (data.get(position).getEvaluate() != null) {
            holder.evaluate.setStar(Float.parseFloat(data.get(position).getEvaluate()));
        } else {
            holder.evaluate.setStar(0f);
        }

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView project;
        public TextView time;
        public TextView introduce;
        public TextView price;
        public RatingBar evaluate;
    }

}
