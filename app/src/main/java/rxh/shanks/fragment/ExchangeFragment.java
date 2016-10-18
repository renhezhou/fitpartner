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
 * Created by Administrator on 2016/10/12.
 * 积分兑换
 */
public class ExchangeFragment extends DialogFragment implements View.OnClickListener {

    View view;
    @Bind(R.id.prompt)
    TextView prompt;
    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.sure)
    Button sure;

    int position;
    String tisi;

    public interface ExchangeFragmentListener {
        void sure(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_exchange, null);
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
        position = bundle.getInt("position");
        tisi = bundle.getString("tisi");
        prompt.setText("是否确认使用\n" + tisi + "\n兑换此商品");
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                ExchangeFragmentListener listener = (ExchangeFragmentListener) getActivity();
                if (listener != null) {
                    listener.sure(position);
                    dismiss();
                }
                break;
            default:
                break;
        }
    }
}
