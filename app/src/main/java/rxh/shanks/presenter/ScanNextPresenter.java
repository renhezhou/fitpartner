package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ScanCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ScanNextView;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ScanNextPresenter {

    private GetInfo getInfo;
    private ScanNextView view;

    public ScanNextPresenter(ScanNextView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }


    public void scan(String gymQR) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("checkin", "add"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("cardID", MyApplication.defaultmembercard);
        params.addBodyParameter("gymQR", gymQR);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ScanCodeEntity entity = new ScanCodeEntity();
                entity = JsonUtils.scan(result);
                if (entity.getCode().equals("0")) {
                    if (entity.getResult().getType().equals("in")) {
                        view.toast(entity.getResult().getGymName() + "成功签入");
                        view.success();
                    } else {
                        view.toast(entity.getResult().getGymName() + "成功签出");
                        view.success();
                    }
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
