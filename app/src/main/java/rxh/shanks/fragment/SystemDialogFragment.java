package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.activity.R;
import rxh.shanks.entity.CurriculumEventBusEntity;
import rxh.shanks.entity.SystemEventBusEntity;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SystemDialogFragment extends DialogFragment {

    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.confirm)
    Button confirm;

    public interface SystemDialogFragmentSystemListener {
        void del(String position);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_system_dialog, null);
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SystemEventBusEntity(""));
                dismiss();
            }
        });
    }

}

