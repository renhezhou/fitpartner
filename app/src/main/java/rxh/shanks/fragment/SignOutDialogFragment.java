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
import rxh.shanks.activity.R;

/**
 * Created by Administrator on 2016/11/10.
 */
public class SignOutDialogFragment extends DialogFragment {


    View view;
    @Bind(R.id.login)
    Button login;

    public interface SignOutDialogFragmentListener {
        void login();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.activity_sign_out_dialog_fragment, container);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOutDialogFragmentListener listener = (SignOutDialogFragmentListener) getActivity();
                if (listener != null) {
                    listener.login();
                    dismiss();
                }
            }
        });
    }
}
