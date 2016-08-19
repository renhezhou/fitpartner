package rxh.shanks.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * Created by Administrator on 2016/8/4.
 */
public class PopupFragment extends DialogFragment implements View.OnClickListener {

    View view;
    @Bind(R.id.prompt)
    TextView prompt;
    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.confirm)
    Button confirm;

    PopupFragmentListener listener;

    public interface PopupFragmentListener {
        void confirm();

        void cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_popup, null);
        ButterKnife.bind(this, view);
        initview();
        return view;
    }


    public void initview() {
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        listener = (PopupFragmentListener) getActivity();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                listener.cancel();
                dismiss();
                break;
            case R.id.confirm:
                listener.confirm();
                dismiss();
                break;
            default:
                break;
        }
    }
}
