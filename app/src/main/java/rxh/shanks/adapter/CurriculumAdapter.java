package rxh.shanks.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.entity.BrandGymPaidEntity;
import rxh.shanks.entity.CurriculumEntity;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CurriculumAdapter extends BaseAdapter {

    Context context;
    private List<BrandGymPaidEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public CurriculumAdapter(Context context, List<BrandGymPaidEntity> data) {
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
                    R.layout.fragment_curriculum_lv_item, null);
            holder.dial_telephone = (RelativeLayout) convertView.findViewById(R.id.dial_telephone);
            holder.club_name = (TextView) convertView.findViewById(R.id.club_name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.club_name.setText(data.get(position).getClubName());
        holder.address.setText(data.get(position).getClubAddress());
        holder.dial_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + data.get(position).getPhoneNumber()));
                context.startActivity(intentNO);
            }
        });

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public RelativeLayout dial_telephone;
        public TextView club_name;
        public TextView address;
    }

}
