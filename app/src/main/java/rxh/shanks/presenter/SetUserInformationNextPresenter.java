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
    private SetUserInformationNextView setUserInformationNextView;

    public SetUserInformationNextPresenter(SetUserInformationNextView setUserInformationNextView) {
        getInfo = new Is_Networking();
        this.setUserInformationNextView = setUserInformationNextView;
    }

    public void setuserinformationnext(String sex, String age, String nick_name, String contact_address, String fitness_needs) {
        if (age.length() == 0) {
            setUserInformationNextView.check("年龄不能为空");
            return;
        }
        if (nick_name.length() == 0) {
            setUserInformationNextView.check("昵称不能为空");
            return;
        }
        if (contact_address.length() == 0) {
            setUserInformationNextView.check("家庭地址不能为空");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "completeUserInfo"));
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("name", MyApplication.realName);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("age", age);
        params.addBodyParameter("nickName", nick_name);
        params.addBodyParameter("address", contact_address);
        params.addBodyParameter("fitTarget", fitness_needs);
        setUserInformationNextView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                setUserInformationNextView.hide();
                SetUserInformationCodeEntity setUserInformationCodeEntity = JsonUtils.setuserinformation(result);
                if (setUserInformationCodeEntity.getCode().equals("0")) {
                    setUserInformationNextView.onSuccess();
                } else {
                    setUserInformationNextView.check(setUserInformationCodeEntity.getError());
                }

            }

            @Override
            public void onError(Throwable ex) {
                setUserInformationNextView.hide();
                setUserInformationNextView.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                setUserInformationNextView.hide();
            }

            @Override
            public void onFinished() {
                setUserInformationNextView.hide();
            }
        });
    }

}
