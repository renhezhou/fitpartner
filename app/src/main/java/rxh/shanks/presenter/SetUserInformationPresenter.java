package rxh.shanks.presenter;

import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;

import rxh.shanks.entity.SetUserInformationCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SetUserInformationView;

/**
 * Created by Administrator on 2016/8/5.
 */
public class SetUserInformationPresenter {

    private GetInfo getInfo;
    private SetUserInformationView setUserInformationView;

    public SetUserInformationPresenter(SetUserInformationView setUserInformationView) {
        getInfo = new Is_Networking();
        this.setUserInformationView = setUserInformationView;
    }

    public void setuserinformation(String nickname, String password, String reset_password) {
        if (nickname.length() == 0) {
            setUserInformationView.check("昵称不能为空");
            return;
        }
        if (password.length() == 0) {
            setUserInformationView.check("密码不能为空");
            return;
        }
        if (reset_password.length() == 0) {
            setUserInformationView.check("密码不能为空");
            return;
        }
        if (!password.equals(reset_password)) {
            setUserInformationView.check("两次密码不一致");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "setAccAndPass"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("account", nickname);
        params.addBodyParameter("password", password);
        setUserInformationView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SetUserInformationCodeEntity setUserInformationCodeEntity = JsonUtils.setuserinformation(result);
                if (setUserInformationCodeEntity.getCode().equals("0")) {
                    setUserInformationView.onSuccess();
                } else {
                    setUserInformationView.check(setUserInformationCodeEntity.getError());
                }

            }

            @Override
            public void onError(Throwable ex) {
                setUserInformationView.hide();
                setUserInformationView.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                setUserInformationView.hide();
            }

            @Override
            public void onFinished() {
                setUserInformationView.hide();
            }
        });
    }


    public void uploadheadportrait(File file) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(file, MyApplication.headImageURL, MyApplication.QNUPToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置。
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }


}
