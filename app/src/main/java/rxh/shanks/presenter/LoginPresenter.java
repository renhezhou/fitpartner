package rxh.shanks.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.LoginView;

/**
 * Created by Administrator on 2016/8/5.
 */
public class LoginPresenter {

    private GetInfo getInfo;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        getInfo = new Is_Networking();
        this.loginView = loginView;
    }

    public void login(String loginType, String phoneNumber, String userName, String password) {
        if (loginType.equals("2")) {
            if (userName.length() < 1) {
                loginView.check("用户名不能为空");
                return;
            }
            if (password.length() < 1) {
                loginView.check("密码不能为空");
                return;
            }
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "login"));
        params.addBodyParameter("loginType", loginType);
        params.addBodyParameter("phoneNumber", phoneNumber);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("password", password);
        loginView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                loginView.hide();
                LoginCodeEntity response = JsonUtils.login(result);
                if (response.getCode().equals("1")) {
                    loginView.check(response.getError());
                } else {
                    MyApplication.token = response.getToken();
                    MyApplication.QNUPToken = response.getResult().getQNUPToken();
                    MyApplication.userID = response.getResult().getUserID();
                    MyApplication.realName = response.getResult().getRealName();
                    MyApplication.nickName = response.getResult().getNickName();
                    MyApplication.headImageURL = response.getResult().getHeadImageURL();
                    MyApplication.currentClubID = response.getResult().getCurrentClubID();
                    MyApplication.currentClubName = response.getResult().getCurrentClubName();
                    MyApplication.address = response.getResult().getAddress();
                    MyApplication.age = response.getResult().getAge();
                    MyApplication.IDcardNumber = response.getResult().getIDcardNumber();
                    MyApplication.phoneNumber = response.getResult().getPhoneNumber();
                    MyApplication.sex = response.getResult().getSex();
                    MyApplication.fitTarget = response.getResult().getFitTarget();
                    MyApplication.defaultmembercard = response.getResult().getDefaultmembercard();
                    MyApplication.userName = response.getResult().getUserName();
                    MyApplication.QNDownToken = response.getResult().getQNDownToken();
                    MyApplication.imToken = response.getResult().getImToken();
                    loginView.onSuccess(response.getIsNew(), response);
                }

            }

            @Override
            public void onError(Throwable ex) {
                loginView.hide();
                loginView.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                loginView.hide();
            }

            @Override
            public void onFinished() {
                loginView.hide();
            }
        });
    }


    public void resetpassword(String loginType, String phoneNumber, String userName, String password) {
        if (loginType.equals("2")) {
            if (userName.length() < 1) {
                loginView.check("用户名不能为空");
                return;
            }
            if (password.length() < 1) {
                loginView.check("密码不能为空");
                return;
            }
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "login"));
        params.addBodyParameter("loginType", loginType);
        params.addBodyParameter("phoneNumber", phoneNumber);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("password", password);
        params.addBodyParameter("type", "0");
        loginView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                loginView.hide();
                LoginCodeEntity response = JsonUtils.login(result);
                if (response.getCode().equals("1")) {
                    loginView.check(response.getError());
                } else {
//                    MyApplication.token = response.getToken();
//                    MyApplication.QNUPToken = response.getResult().getQNUPToken();
//                    MyApplication.userID = response.getResult().getUserID();
//                    MyApplication.realName = response.getResult().getRealName();
//                    MyApplication.nickName = response.getResult().getNickName();
//                    MyApplication.headImageURL = response.getResult().getHeadImageURL();
//                    MyApplication.currentClubID = response.getResult().getCurrentClubID();
//                    MyApplication.currentClubName = response.getResult().getCurrentClubName();
                    loginView.onSuccess("", response);
                }

            }

            @Override
            public void onError(Throwable ex) {
                loginView.hide();
                loginView.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                loginView.hide();
            }

            @Override
            public void onFinished() {
                loginView.hide();
            }
        });
    }


}
