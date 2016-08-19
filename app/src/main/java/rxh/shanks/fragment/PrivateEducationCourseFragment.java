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
public class PrivateEducationCourseFragment extends Fragment implements PrivateEducationCourseView {

    View view;
    @Bind(R.id.gv)
    GridView gv;
    PrivateEducationCourseGVAdapter privateEducationCourseGVAdapter;
    @Bind(R.id.confirm_an_appointment)
    TextView confirm_an_appointment;
    List<PrivateEducationCourseGetHoldingTimeEntity> GetHoldingTimedata = new ArrayList<>();
    String date;
    PrivateEducationCoursePresenter privateEducationCoursePresenter;

    int positionflag = -1, restTime;

    List<DataSouceCoachEntity> data = new ArrayList<>();
    List<DataSouceCoachEntity> coachdata = new ArrayList<>();
    List<DataSouceCoachEntity> userdata = new ArrayList<>();

    public interface PrivateEducationCourseFragmentListener {
        void ConfirmAnAppointment();
    }


    public static PrivateEducationCourseFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date", date);
        PrivateEducationCourseFragment pageFragment = new PrivateEducationCourseFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_private_education_course, null);
        privateEducationCoursePresenter = new PrivateEducationCoursePresenter(this);
        date = getArguments().getString("date");
        ButterKnife.bind(this, view);
        initview();
        initdata();
        return view;
    }

    public void initview() {
        confirm_an_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionflag != -1) {
                    privateEducationCoursePresenter.bespokeLesson(CreatTime.qingqiu(date, data.get(positionflag).getTime(), restTime));
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
                    privateEducationCourseGVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), " 此时间段已被占用，无法排课", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void initdata() {
        privateEducationCoursePresenter.getHoldingTime(MyApplication.CoachID, date);

//        privateEducationCourseGVAdapter = new PrivateEducationCourseGVAdapter(getActivity(), data);
//        gv.setAdapter(privateEducationCourseGVAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //教练占用的时间
    @Override
    public void getHoldingTime(List<PrivateEducationCourseGetHoldingTimeEntity> privateEducationCourseGetHoldingTimeEntityList) {
        GetHoldingTimedata = privateEducationCourseGetHoldingTimeEntityList;
        if (GetHoldingTimedata.get(0).getIsRest().equals("0")) {
            //教练工作日
            privateEducationCoursePresenter.getUserHoldingTime(date);
            restTime = Integer.parseInt(GetHoldingTimedata.get(0).getRestTime());
        } else if (GetHoldingTimedata.get(0).getIsRest().equals("1")) {
            //教练休息日
        }
    }

    //用户占用的时间
    @Override
    public void getUserHoldingTime(List<PrivateEducationCourseGetUserHoldingTimeEntity> privateEducationCourseGetUserHoldingTimeEntityList) {
        //教练工作时间表生成方式
        data = PrivateEducationCourseFragmentDataSouceUtils.creatcoachdata(GetHoldingTimedata.get(0).getWorkTime(), GetHoldingTimedata.get(0).getRestTime());
        //教练已被占时间表
        for (int i = 0; i < GetHoldingTimedata.get(0).getHoldTime().size(); i++) {
            coachdata.addAll(PrivateEducationCourseFragmentDataSouceUtils.binary_system(GetHoldingTimedata.get(0).getHoldTime().get(i), GetHoldingTimedata.get(0).getRestTime()));
        }

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < coachdata.size(); j++) {
                if (data.get(i).getTime().equals(coachdata.get(j).getTime())) {
                    DataSouceCoachEntity dataSouceCoachEntity = new DataSouceCoachEntity();
                    dataSouceCoachEntity.setFlag(1);
                    dataSouceCoachEntity.setTime(data.get(i).getTime());
                    data.set(i, dataSouceCoachEntity);
                }
            }

        }
        //用户已被占用时间表
        for (int i = 0; i < privateEducationCourseGetUserHoldingTimeEntityList.get(0).getHoldTime().size(); i++) {
            userdata.addAll(PrivateEducationCourseFragmentDataSouceUtils.binary_system(privateEducationCourseGetUserHoldingTimeEntityList.get(0).getHoldTime().get(i), "1"));
        }

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userdata.size(); j++) {
                if (data.get(i).getTime().equals(userdata.get(j).getTime())) {
                    DataSouceCoachEntity dataSouceCoachEntity = new DataSouceCoachEntity();
                    dataSouceCoachEntity.setFlag(2);
                    dataSouceCoachEntity.setTime(data.get(i).getTime());
                    data.set(i, dataSouceCoachEntity);
                }
            }

        }
        privateEducationCourseGVAdapter = new PrivateEducationCourseGVAdapter(getActivity(), data);
        gv.setAdapter(privateEducationCourseGVAdapter);
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
                //0表示用户和教练都有空，1表示教练没空，2表示用户没空，3表示教练和用户都没空,4表示被选中
                if (data.get(position).getFlag() == 0) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.time.setTextColor(context.getResources().getColor(R.color.red));
                } else if (data.get(position).getFlag() == 1) {
                    holder.time.setBackgroundColor(context.getResources().getColor(R.color.text_not_selected));
                    holder.time.setTextColor(context.getResources().getColor(R.color.white));
                } else if (data.get(position).getFlag() == 2) {
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