package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ModifyLoginPasswordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.ModifyLoginPasswordView;

/**
 * Created by Administrator on 2016/12/2.
 */
public class ModifyLoginPasswordPresenter {


    private GetInfo getInfo;
    private ModifyLoginPasswordView view;

    public ModifyLoginPasswordPresenter(ModifyLoginPasswordView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void modifyloginpassword(String old_password, String new_password, String reset_new_password) {
        if (old_password.equals("") || old_password == null) {
            view.toast("旧密码不能为空");
            return;
        }
        if (new_password.equals("") || new_password == null) {
            view.toast("新密码不能为空");
            return;
        }
        if (reset_new_password.equals("") || reset_new_password == null) {
            view.toast("请确认新密码");
            return;
        }
        if (old_password.length() < 6 || new_password.length() < 6 || reset_new_password.length() < 6) {
            view.toast("密码长度不够。密码长度至少为6个字符");
            return;
        }
        if (!new_password.equals(reset_new_password)) {
            view.toast("两次密码不一致");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "modifyPassword"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("oldpassword", old_password);
        params.addBodyParameter("newpassword", new_password);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ModifyLoginPasswordCodeEntity entity = new ModifyLoginPasswordCodeEntity();
                entity = JsonUtils.modifyloginpassword(result);
                if (entity.getCode().equals("0")) {
                    view.toast("密码修改成功");
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
