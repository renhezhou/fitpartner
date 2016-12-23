package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ModifyPayPasswordCodeEntity;
import rxh.shanks.entity.SetPayPasswordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ModifyPayPasswordView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ModifyPayPasswordPresenter {


    private GetInfo getInfo;
    private ModifyPayPasswordView view;

    public ModifyPayPasswordPresenter(ModifyPayPasswordView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void modifypaypassword(String oldPassword, String new_pay_password, String reset_new_pay_password) {
        if (oldPassword == null || oldPassword.equals("")) {
            view.toast("请输入旧支付密码");
            return;
        }
        if (new_pay_password == null || new_pay_password.equals("")) {
            view.toast("请输入新支付密码");
            return;
        }
        if (reset_new_pay_password == null || reset_new_pay_password.equals("")) {
            view.toast("请再次输入新支付密码");
            return;
        }
        if (!new_pay_password.equals(reset_new_pay_password)) {
            view.toast("新密码输入不一致");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "changePassword"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("oldPassword", oldPassword);
        params.addBodyParameter("password", new_pay_password);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ModifyPayPasswordCodeEntity entity = new ModifyPayPasswordCodeEntity();
                entity = JsonUtils.modifypaypassword(result);
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
