package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ScanCodeEntity;
import rxh.shanks.entity.ScanCodeGetGymCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ScanView;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ScanPresenter {


    private GetInfo getInfo;
    private ScanView view;

    public ScanPresenter(ScanView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void get_gymInfo(String gymQR) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("checkin", "gymInfo"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("cardID", MyApplication.defaultmembercard);
        params.addBodyParameter("gymQR", gymQR);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ScanCodeGetGymCodeEntity entity = new ScanCodeGetGymCodeEntity();
                entity = JsonUtils.get_gymInfo(result);
                if (entity.getCode().equals("0")) {
                    view.get_gymInfo(entity.getResult());
                } else {
                    view.toast(entity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide();
            }

            @Override
            public void onFinished() {
                view.hide();
            }
        });
    }


}
