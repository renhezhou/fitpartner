package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ForgetPayPasswordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ForgetPayPasswordView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ForgetPayPasswordPresenter {


    private GetInfo getInfo;
    private ForgetPayPasswordView view;

    public ForgetPayPasswordPresenter(ForgetPayPasswordView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void forgetpaypassword() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "forgetPassword"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ForgetPayPasswordCodeEntity entity = new ForgetPayPasswordCodeEntity();
                entity = JsonUtils.forgetpaypassword(result);
                if (entity.getCode().equals("0")) {
                    view.success();
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
