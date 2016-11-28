package rxh.shanks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.activity.R;
import rxh.shanks.base.BaseFragment;
import rxh.shanks.entity.DataSouceCoachEntity;
import rxh.shanks.entity.PrivateEducationCourseEntity;
import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeEntity;
import rxh.shanks.presenter.PrivateEducationCoursePresenter;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.utils.PrivateEducationCourseFragmentDataSouceUtils;
import rxh.shanks.view.PrivateEducationCourseView;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PrivateEducationCourseFragment extends BaseFragment implements PrivateEducationCourseView {

    View view;
    @Bind(R.id.gv)
    GridView gv;
    @Bind(R.id.confirm_an_appointment)
    TextView confirm_an_appointment;
    String date, lessonID;
    PrivateEducationCourseGVAdapter adapter;
    PrivateEducationCoursePresenter presenter;

    int positionflag = -1;
    String time;

    List<DataSouceCoachEntity> data = new ArrayList<>();

    public interface PrivateEducationCourseFragmentListener {
        void ConfirmAnAppointment();
    }


    public static PrivateEducationCourseFragment newInstance(String lessonID, String date, String time) {
        Bundle args = new Bundle();
        args.putString("lessonID", lessonID);
        args.putString("date", date);
        args.putString("time", time);
        PrivateEducationCourseFragment pageFragment = new PrivateEducationCourseFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_private_education_course, null);
        presenter = new PrivateEducationCoursePresenter(this);
        lessonID = getArguments().getString("lessonID");
        date = getArguments().getString("date");
        time = getArguments().getString("time");
        ButterKnife.bind(this, view);
        initview();
        presenter.getCoachTime(MyApplication.CoachID, date, time);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initview() {
        gv.setEmptyView(view.findViewById(R.id.empty));
        confirm_an_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionflag != -1) {
                    presenter.bespokeLesson(lessonID, CreatTime.qingqiu(date, data.get(positionflag).getTime(), Integer.parseInt(time)));
                } else {
                    Toast.makeText(getActivity(), "请选择预约时间", Toast.LENGTH_LONG).show();
                }
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断此项是否可以选中，0表示可以被选中
                if (data.get(position).getFlag() == 0) {
                    positionflag = position;
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), " 此时间段已被占用，无法排课", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void show(int flag) {
        if (flag == 3) {
            loading("预约中", "true");
        }
    }

    @Override
    public void hide(int flag) {
        if (flag == 3) {
            dismiss();
        }
    }

    @Override
    public void getCoachTime(List<DataSouceCoachEntity> entities) {
        data.clear();
        data.addAll(entities);
        adapter = new PrivateEducationCourseGVAdapter(getActivity(), data);
        gv.setAdapter(adapter);
    }

    //预约私教课成功的回调
    @Override
    public void bespokeLesson() {
        PrivateEducationCourseFragmentListener listener = (PrivateEducationCourseFragmentListener) getActivity();
        listener.ConfirmAnAppointment();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }


    public class PrivateEducationCourseGVAdapter extends BaseAdapter {


        Context context;
        List<DataSouceCoachEntity> data = new ArrayList<>();
        LayoutInflater mInflater;

        public PrivateEducationCourseGVAdapter(Context context, List<DataSouceCoachEntity> data) {
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
                        R.layout.fragment_private_education_course_gv_item, null);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.time.setText(data.get(position).getTime());
            if (positionflag == position) {
                holder.time.setBackgroundColor(context.getResources().getColor(R.color.red));
                holder.time.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                //0表示用户和教练都有空，1表示时间被私教课占用，2表示时间被团课和免费课占用，3表示时间已经过去了,4表示被选中
                if (data.get(position).getFlag() == 0) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.time.setTextColor(context.getResources().getColor(R.color.red));
                } else if (data.get(position).getFlag() == 1) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
                    holder.time.setTextColor(context.getResources().getColor(R.color.white));
                } else if (data.get(position).getFlag() == 2) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
                    holder.time.setTextColor(context.getResources().getColor(R.color.white));
                } else if (data.get(position).getFlag() == 3) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
                    holder.time.setTextColor(context.getResources().getColor(R.color.white));
                }
            }

            return convertView;
        }

        // ViewHolder静态类
        class ViewHolder {
            public TextView time;
        }
    }
}