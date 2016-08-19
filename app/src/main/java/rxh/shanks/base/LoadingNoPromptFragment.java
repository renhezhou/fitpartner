package rxh.shanks.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import rxh.shanks.activity.R;
import rxh.shanks.loadlibrary.LoadingDrawable;
import rxh.shanks.loadlibrary.MaterialLoadingRenderer;


/**
 * Created by Administrator on 2016/4/21.
 */
public class LoadingNoPromptFragment extends DialogFragment {

    View view;
    ImageView loading_img;
    String  flag;
    private LoadingDrawable mMaterialDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        flag = bundle.getString("flag");
        if (flag.equals("true")) {
            getDialog().setCanceledOnTouchOutside(false);
        } else if (flag.equals("false")) {
            getDialog().setCanceledOnTouchOutside(true);
        }
        view = inflater.inflate(R.layout.fragment_loading_no_prompt, container);
        initview();
        return view;
    }

    public void initview() {
        loading_img = (ImageView) view.findViewById(R.id.loading_img);
        mMaterialDrawable = new LoadingDrawable(new MaterialLoadingRenderer(getActivity()));
        loading_img.setImageDrawable(mMaterialDrawable);
        mMaterialDrawable.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMaterialDrawable.stop();
    }
}
