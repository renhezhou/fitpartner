package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.activity.R;
import rxh.shanks.adapter.ConfirmAnAppointmentAdapter;
import rxh.shanks.base.BaseFragment;
import rxh.shanks.entity.ConfirmAnAppointmentEntity;
import rxh.shanks.presenter.ConfirmAnAppointmentPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ConfirmAnAppointmentView;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ConfirmAnAppointmentFragment extends BaseFragment implements ConfirmAnAppointmentView {

    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.confirm_an_appointment)
    TextView confirm_an_appointment;
    View view;
    List<ConfirmAnAppointmentEntity> datas = new ArrayList<>();
    List<String> checkboxflag = new ArrayList<>();
    List<String> planID = new ArrayList<>();
    ConfirmAnAppointmentAdapter adapter;
    ConfirmAnAppointmentPresenter presenter;
    String date;

    public interface ConfirmAnAppointmentFragmentListener {
        void ConfirmAnAppointment();
    }


    public static ConfirmAnAppointmentFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date", date);
        ConfirmAnAppointmentFragment pageFragment = new ConfirmAnAppointmentFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_an_appointment, null);
        presenter = new ConfirmAnAppointmentPresenter(this);
        date = getArguments().getString("date");
        ButterKnife.bind(this, view);
        presenter.getTeamLessonFreeTime(date);
        initview();
        return view;
    }


    public void initview() {
        confirm_an_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.orderTeamLesson(MyApplication.lessonID, MyApplication.CoachID, planID);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkboxflag.get(position).equals("1")) {
                    //表示这行已被选中,点击了表示取消
                    checkboxflag.set(position, "0");
                    for (int i = 0; i < planID.size(); i++) {
                        if (planID.get(i).equals(checkboxflag.get(position))) {
                            planID.remove(i);
                        }
                    }
                } else if (checkboxflag.get(position).equals("0")) {
                    //表示这行未被选中，点击了表示选中
                    checkboxflag.set(position, "1");
                    planID.add(datas.get(position).getPlanID());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
    public void getTeamLessonFreeTime(List<ConfirmAnAppointmentEntity> data) {
//        datas.clear();
        //1表示选中，0表示未选中
        for (int i = 0; i < data.size(); i++) {
            checkboxflag.add("0");
        }
        datas = data;
        adapter = new ConfirmAnAppointmentAdapter(getActivity(), datas, checkboxflag);
        lv.setAdapter(adapter);
    }

    //预约团课成功
    @Override
    public void success() {
        ConfirmAnAppointmentFragmentListener listener = (ConfirmAnAppointmentFragmentListener) getActivity();
        listener.ConfirmAnAppointment();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
