package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.activity.R;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ViewAppointmentDialogFragment extends DialogFragment {

    View view;
    @Bind(R.id.tisi)
    TextView tisi;
    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.sure)
    Button sure;

    String ts;
    int position, flag;

    public interface ViewAppointmentDialogFragmentListener {
        void sure(int flag, int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_view_appointment_dialog, null);
        ButterKnife.bind(this, view);
        initview();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initview() {
        Bundle bundle = getArguments();
        ts = bundle.getString("ts");
        position = bundle.getInt("position");
        flag = bundle.getInt("flag");
        tisi.setText(ts);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAppointmentDialogFragmentListener listener = (ViewAppointmentDialogFragmentListener) getActivity();
                if (listener != null) {
                    listener.sure(flag, position);
                    dismiss();
                }
            }
        });
    }
}
