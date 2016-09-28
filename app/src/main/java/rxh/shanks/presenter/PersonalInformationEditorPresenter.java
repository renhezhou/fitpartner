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
import rxh.shanks.view.PersonalInformationEditorView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class PersonalInformationEditorPresenter {

    private GetInfo getInfo;
    private PersonalInformationEditorView personalInformationEditorView;

    public PersonalInformationEditorPresenter(PersonalInformationEditorView personalInformationEditorView) {
        getInfo = new Is_Networking();
        this.personalInformationEditorView = personalInformationEditorView;
    }

    public void setuserinformationnext(String sex, String age, String nick_name, String phone_num, String contact_address, String fitness_needs) {
        if (age.length() == 0) {
            personalInformationEditorView.toast("年龄不能为空");
            return;
        }
        if (nick_name.length() == 0) {
            personalInformationEditorView.toast("昵称不能为空");
            return;
        }

        if (phone_num.length() == 0) {
            personalInformationEditorView.toast("电话号码不能为空");
            return;
        }
        if (contact_address.length() == 0) {
            personalInformationEditorView.toast("家庭地址不能为空");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "completeUserInfo"));
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("name", MyApplication.realName);
        params.addBodyParameter("currentClubID", MyApplication.currentClubID);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("age", age);
        params.addBodyParameter("nickName", nick_name);
        params.addBodyParameter("address", contact_address);
        params.addBodyParameter("fitTarget", fitness_needs);
        personalInformationEditorView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SetUserInformationCodeEntity setUserInformationCodeEntity = JsonUtils.setuserinformation(result);
                if (setUserInformationCodeEntity.getCode().equals("0")) {
                    personalInformationEditorView.success();
                } else {
                    personalInformationEditorView.toast(setUserInformationCodeEntity.getError());
                }

            }

            @Override
            public void onError(Throwable ex) {
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

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
