package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.SetUserInformationCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SetUserInformationNextView;
import rxh.shanks.view.SetUserInformationView;

/**
 * Created by Administrator on 2016/8/5.
 */
public class SetUserInformationNextPresenter {

    private GetInfo getInfo;
    private SetUserInformationNextView view;

    public SetUserInformationNextPresenter(SetUserInformationNextView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void setuserinformationnext(String sex, String ID_card, String nick_name, String contact_address, String fitness_needs) {
        if (ID_card.length() == 0) {
            view.check("身份证号码不能为空");
            return;
        }
        if (nick_name.length() == 0) {
            view.check("昵称不能为空");
            return;
        }
        if (contact_address.length() == 0) {
            view.check("家庭地址不能为空");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "completeUserInfo"));
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("name", MyApplication.realName);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("IDcardNumber", ID_card);
        params.addBodyParameter("nickName", nick_name);
        params.addBodyParameter("address", contact_address);
        params.addBodyParameter("fitTarget", fitness_needs);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                SetUserInformationCodeEntity setUserInformationCodeEntity = JsonUtils.setuserinformation(result);
                if (setUserInformationCodeEntity.getCode().equals("0")) {
                    view.onSuccess();
                } else {
                    view.check(setUserInformationCodeEntity.getError());
                }

            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
                view.onError(ex);
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
