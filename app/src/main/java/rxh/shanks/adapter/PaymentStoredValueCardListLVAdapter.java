package rxh.shanks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.entity.PaymentStoredValueCardListEntity;

/**
 * Created by Administrator on 2016/12/7.
 */
public class PaymentStoredValueCardListLVAdapter extends BaseAdapter {


    Context context;
    private List<PaymentStoredValueCardListEntity> data = new ArrayList<>();
    LayoutInflater mInflater;

    public PaymentStoredValueCardListLVAdapter(Context context, List<PaymentStoredValueCardListEntity> data) {
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
                    R.layout.activity_payment_stored_value_card_list_lv_item, null);
            holder.card_name = (TextView) convertView.findViewById(R.id.card_name);
            holder.due_time = (TextView) convertView.findViewById(R.id.due_time);
            holder.balance = (TextView) convertView.findViewById(R.id.balance);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.card_name.setText(data.get(position).getCardName());
        //holder.due_time.setText("2015-7-8");
        holder.balance.setText(data.get(position).getP());
        return convertView;
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView card_name;
        public TextView due_time;
        public TextView balance;
    }


}
