package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.SetPayPasswordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SetPayPasswordView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class SetPayPasswordPresenter {


    private GetInfo getInfo;
    private SetPayPasswordView view;

    public SetPayPasswordPresenter(SetPayPasswordView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void setpaypassword(String pay_password, String reset_pay_password) {
        if (pay_password == null || pay_password.equals("")) {
            view.toast("请设置支付密码");
            return;
        }
        if (reset_pay_password == null || reset_pay_password.equals("")) {
            view.toast("请确认支付密码");
            return;
        }
        if (!pay_password.equals(reset_pay_password)) {
            view.toast("两次输入密码不一致");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "changePassword"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("password", pay_password);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                SetPayPasswordCodeEntity entity = new SetPayPasswordCodeEntity();
                entity = JsonUtils.setpaypassword(result);
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
