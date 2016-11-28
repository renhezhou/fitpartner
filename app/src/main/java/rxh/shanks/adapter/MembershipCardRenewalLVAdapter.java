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
import rxh.shanks.customview.SmoothCheckBox;
import rxh.shanks.entity.MembershipCardRenewalEntity;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MembershipCardRenewalLVAdapter extends BaseAdapter {


    Context context;
    item_selected itemSelected;
    int selected_position = -1;
    private List<MembershipCardRenewalEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public interface item_selected {
        void selected(int flag, int position);//0代表checkbox，1代表详情
    }

    public void set_item_selected(item_selected itemSelected) {
        this.itemSelected = itemSelected;
    }

    public MembershipCardRenewalLVAdapter(Context context, List<MembershipCardRenewalEntity> data) {
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
                    R.layout.activity_membership_card_renewal_lv_item, null);
            holder.selected = (ImageView) convertView.findViewById(R.id.selected);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.details = (TextView) convertView.findViewById(R.id.details);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (selected_position == position) {
            Picasso.with(context).load(R.drawable.selecte).into(holder.selected);
        } else {
            Picasso.with(context).load(R.drawable.back).into(holder.selected);
        }
        holder.money.setText(data.get(position).getMoney());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelected.selected(0, position);
                selected_position = position;
                notifyDataSetChanged();
            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelected.selected(1, position);
            }
        });
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView selected;
        public TextView money;
        public TextView details;
    }
}
