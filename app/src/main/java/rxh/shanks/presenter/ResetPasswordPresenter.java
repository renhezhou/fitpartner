package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.entity.ResetPasswordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.LoginView;
import rxh.shanks.view.ResetPasswordView;

/**
 * Created by Administrator on 2016/8/5.
 */
public class ResetPasswordPresenter {

    private GetInfo getInfo;
    private ResetPasswordView resetPasswordView;

    public ResetPasswordPresenter(ResetPasswordView resetPasswordView) {
        getInfo = new Is_Networking();
        this.resetPasswordView = resetPasswordView;
    }

    public void resetPassword(String password, String rest_pssword) {
        if (password.length() == 0) {
            resetPasswordView.check("密码不能为空");
            return;
        }
        if (rest_pssword.length() == 0) {
            resetPasswordView.check("密码不能为空");
            return;
        }
        if (!password.equals(rest_pssword)) {
            resetPasswordView.check("两次密码不一致");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "resetUser"));
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("password", password);
        resetPasswordView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                resetPasswordView.hide();
                ResetPasswordCodeEntity resetPasswordCodeEntity = JsonUtils.resetPassword(result);
                if (resetPasswordCodeEntity.getCode().equals("0")) {
                    resetPasswordView.onSuccess("重置密码成功");
                    resetPasswordView.go();
                } else {
                    resetPasswordView.check(resetPasswordCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                resetPasswordView.hide();
                resetPasswordView.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                resetPasswordView.hide();
            }

            @Override
            public void onFinished() {
                resetPasswordView.hide();
            }
        });
    }

}
