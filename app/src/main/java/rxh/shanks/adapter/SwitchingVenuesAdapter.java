package rxh.shanks.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.entity.AlreadyMakeAnAppointmentEntity;
import rxh.shanks.entity.SwitchingVenuesCodeEntity;
import rxh.shanks.entity.SwitchingVenuesEntity;
import rxh.shanks.utils.MyApplication;

/**
 * Created by Administrator on 2016/8/1.
 */
public class SwitchingVenuesAdapter extends BaseExpandableListAdapter {

    Context context;
    private LayoutInflater mInflater = null;
    SwitchingVenuesCodeEntity data = new SwitchingVenuesCodeEntity();

    public SwitchingVenuesAdapter(
            SwitchingVenuesCodeEntity data, Context context) {
        this.data = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    // 得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getResult().get(groupPosition).get(childPosition).getClubName();
    }

    // 得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 设置子item的组件
    @SuppressLint("NewApi")
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder ih;
        if (convertView == null) {
            ih = new ItemHolder();
            convertView = mInflater.inflate(
                    R.layout.activity_switching_venues_lv_childrion_item, null);
            ih.img = (CircleImageView) convertView.findViewById(R.id.img);
            ih.children_name = (TextView) convertView
                    .findViewById(R.id.children_name);
            ih.address = (TextView) convertView
                    .findViewById(R.id.address);
            ih.phone_num = (TextView) convertView
                    .findViewById(R.id.phone_num);
            ih.state = (TextView) convertView
                    .findViewById(R.id.state);
            convertView.setTag(ih);
        } else {
            ih = (ItemHolder) convertView.getTag();
        }
        Glide
                .with(context)
                .load(data.getResult().get(groupPosition).get(childPosition).getLogoImageURL())
                .centerCrop()
                //.placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(ih.img);
        ih.children_name
                .setText(data.getResult().get(groupPosition).get(childPosition).getClubName());
        ih.address.setText(data.getResult().get(groupPosition).get(childPosition).getClubAddress());
        ih.phone_num.setText(data.getResult().get(groupPosition).get(childPosition).getPhoneNumber());
        if (MyApplication.currentClubID.equals(data.getResult().get(groupPosition).get(childPosition).getClubID())) {
            ih.state.setText("默认");
        } else {
            ih.state.setText("切换");
        }

        return convertView;
    }

    // 获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getResult().get(groupPosition).size();
    }

    // 获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return data.getResult().get(groupPosition).get(0).getBrandName();
    }

    @Override
    public int getGroupCount() {
        return data.getResult().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder gh;
        if (convertView == null) {
            gh = new GroupHolder();
            convertView = mInflater
                    .inflate(
                            R.layout.activity_switching_venues_lv_parents_item, null);
            gh.partent_name = (TextView) convertView.findViewById(R.id.parents_name);
            convertView.setTag(gh);
        } else {
            gh = (GroupHolder) convertView.getTag();
        }
        gh.partent_name.setText(data.getResult().get(groupPosition).get(0).getBrandName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupHolder {
        TextView partent_name;
    }

    public class ItemHolder {
        CircleImageView img;
        TextView children_name;
        TextView address;
        TextView phone_num;
        TextView state;
    }


}
