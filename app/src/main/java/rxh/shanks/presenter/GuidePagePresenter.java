package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.entity.VersionNameCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.GuidePageView;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GuidePagePresenter {


    private GetInfo getInfo;
    private GuidePageView view;

    public GuidePagePresenter(GuidePageView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void login(String loginType, String phoneNumber, String userName, String password) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "login"));
        params.addBodyParameter("loginType", loginType);
        params.addBodyParameter("phoneNumber", phoneNumber);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("password", password);
        params.addBodyParameter("type", "0");
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                LoginCodeEntity response = JsonUtils.login(result);
                if (response.getCode().equals("1")) {
                    view.check(response.getError());
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
                    MyApplication.phoneNumber = response.getResult().getPhoneNumber();
                    MyApplication.sex = response.getResult().getSex();
                    MyApplication.fitTarget = response.getResult().getFitTarget();
                    MyApplication.defaultmembercard = response.getResult().getDefaultmembercard();
                    MyApplication.userName = response.getResult().getUserName();
                    MyApplication.QNDownToken = response.getResult().getQNDownToken();
                    MyApplication.imToken = response.getResult().getImToken();
                    view.onSuccess(response.getIsNew(), response);
                }

            }

            @Override
            public void onError(Throwable ex) {
                view.onError(ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void get_version_name() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "getAppVersion"));
        params.addBodyParameter("type", "3");
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        VersionNameCodeEntity entity = new VersionNameCodeEntity();
                        entity = JsonUtils.get_version_name(result);
                        if (entity.getCode().equals("0")) {
                            view.get_version_name(entity.getResult().getAppversion());
                        } else {
                            view.check(entity.getError());
                            view.get_version_name_error();
                        }
                    }

                    @Override
                    public void onError(Throwable ex) {
                        view.onError(ex);
                        view.get_version_name_error();
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                }

        );
    }

}
