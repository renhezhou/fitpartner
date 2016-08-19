package rxh.shanks.adapter;

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
import rxh.shanks.entity.CouponEntity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class CouponAdapter extends BaseAdapter {

    Context context;
    private List<CouponEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public CouponAdapter(Context context, List<CouponEntity> data) {
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
                    R.layout.activity_coupon_lv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.club_name = (TextView) convertView.findViewById(R.id.club_name);
            holder.discount = (TextView) convertView.findViewById(R.id.discount);
            holder.applicable_shop = (TextView) convertView.findViewById(R.id.applicable_shop);
            holder.suitable_product = (TextView) convertView.findViewById(R.id.suitable_product);
            holder.term_of_validity = (TextView) convertView.findViewById(R.id.term_of_validity);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.club_name.setText(data.get(position).getClub_name());
        holder.discount.setText(data.get(position).getDiscount());
        holder.applicable_shop.setText(data.get(position).getApplicable_shop());
        holder.suitable_product.setText(data.get(position).getSuitable_product());
        holder.term_of_validity.setText(data.get(position).getTerm_of_validity());

        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView club_name;
        public TextView discount;
        public TextView applicable_shop;
        public TextView suitable_product;
        public TextView term_of_validity;
    }
}
