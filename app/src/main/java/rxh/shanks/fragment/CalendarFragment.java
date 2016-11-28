package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.activity.R;
import rxh.shanks.datepicker.cons.DPMode;
import rxh.shanks.datepicker.views.DatePicker;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CalendarFragment extends DialogFragment {

    public interface CalendarFragmentListener {
        void checkdate(String date);
    }

    View view;
    @Bind(R.id.datepicker)
    DatePicker date_picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_calendar, null);
        ButterKnife.bind(this, view);
        initview();
        return view;
    }

    public void initview() {
        Calendar c = Calendar.getInstance();
        date_picker.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        date_picker.setMode(DPMode.SINGLE);
        date_picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                CalendarFragmentListener listener = (CalendarFragmentListener) getActivity();
                if (listener != null) {
                    listener.checkdate(date);
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
