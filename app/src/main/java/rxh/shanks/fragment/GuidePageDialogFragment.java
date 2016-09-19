package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import rxh.shanks.activity.R;


/**
 * Created by Administrator on 2016/9/19.
 */
public class GuidePageDialogFragment extends DialogFragment {

    View view;
    private TextView go;

    public interface GuidePageDialogFragmentListener {
        void go();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.fragment_guide_page_dialog, null);
        initview();
        return view;
    }

    public void initview() {
        go = (TextView) view.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuidePageDialogFragmentListener listener = (GuidePageDialogFragmentListener) getActivity();
                if (listener != null) {
                    listener.go();
                    dismiss();
                }
            }
        });
    }
}
