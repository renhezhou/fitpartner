package rxh.shanks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.entity.MyPrivateEducationHeadEntity;

/**
 * Created by Administrator on 2016/10/19.
 */
public class MYERVAdapter extends RecyclerView.Adapter<MYEViewHolder> {


    Context context;
    int selectNum;//被选中的教练的顺序
    List<MyPrivateEducationHeadEntity> data = new ArrayList<>();
    OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MYERVAdapter(Context context, int selectNum, List<MyPrivateEducationHeadEntity> data) {
        this.context = context;
        this.selectNum = selectNum;
        this.data = data;
    }

    @Override
    public MYEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MYEViewHolder holder = new MYEViewHolder(LayoutInflater.from(
                context).inflate(R.layout.activity_my_private_education_gallery_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MYEViewHolder holder, final int position) {
        Picasso.with(context)
                .load(data.get(position).getHeadImageURL())
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(holder.head_portrait);
        Picasso.with(context)
                .load(R.drawable.sanjiaoxing)
                .into(holder.select);
        if (selectNum == position) {
            holder.select.setVisibility(View.VISIBLE);
        } else {
            holder.select.setVisibility(View.GONE);
        }
        holder.head_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class MYEViewHolder extends RecyclerView.ViewHolder {

    CircleImageView head_portrait;
    ImageView select;

    public MYEViewHolder(View view) {
        super(view);
        head_portrait = (CircleImageView) view.findViewById(R.id.head_portrait);
        select = (ImageView) view.findViewById(R.id.select);
    }

}
