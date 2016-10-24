package rxh.shanks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.activity.R;

/**
 * Created by Administrator on 2016/8/3.
 * 资料fragment
 */
public class DataFragment extends Fragment {

    View view;
    @Bind(R.id.data)
    TextView data;
    String descrip = null;

    public static DataFragment newInstance(String descrip) {
        Bundle args = new Bundle();
        args.putString("descrip", descrip);
        DataFragment pageFragment = new DataFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data, null);
        ButterKnife.bind(this, view);
        descrip = getArguments().getString("descrip");
        data.setText(descrip);
        return view;
    }
}
