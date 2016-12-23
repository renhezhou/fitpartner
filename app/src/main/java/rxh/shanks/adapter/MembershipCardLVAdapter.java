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
import rxh.shanks.entity.CoachEntity;
import rxh.shanks.entity.MembershipUsedHistoryEntity;
import rxh.shanks.utils.ChangeUtils;
import rxh.shanks.utils.CheckUtils;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MembershipCardLVAdapter extends BaseAdapter {


    Context context;
    String cardType;
    private List<MembershipUsedHistoryEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public MembershipCardLVAdapter(Context context, List<MembershipUsedHistoryEntity> data, String cardType) {
        this.context = context;
        this.data = data;
        this.cardType = cardType;
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
                    R.layout.activity_membership_card_lv_item, null);
            holder.flag = (ImageView) convertView.findViewById(R.id.flag);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.club_name = (TextView) convertView.findViewById(R.id.club_name);
            holder.do_what = (TextView) convertView.findViewById(R.id.do_what);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (cardType.equals("0")) {
            Picasso.with(context).load(R.drawable.sign).into(holder.flag);
            holder.do_what.setText(data.get(position).getType());
        } else if (cardType.equals("1")) {
            Picasso.with(context).load(R.drawable.cikasign).into(holder.flag);
            holder.do_what.setText(data.get(position).getType());
        } else if (cardType.equals("2")) {
            Picasso.with(context).load(R.drawable.congzhi).into(holder.flag);
            holder.do_what.setText(ChangeUtils.get_consumption_type(data.get(position).getType()) + "消费:" + data.get(position).getPrice());
        }
        holder.time.setText(data.get(position).getTime());
        holder.club_name.setText(data.get(position).getGymName());
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView flag;
        public TextView time;
        public TextView club_name;
        public TextView do_what;
    }

}
