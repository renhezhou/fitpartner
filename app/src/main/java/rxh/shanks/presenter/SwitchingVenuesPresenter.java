package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.SwitchingVenuesCodeEntity;
import rxh.shanks.entity.SwitchingVenuesSuccessCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SwitchingVenuesView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SwitchingVenuesPresenter {


    private GetInfo getInfo;
    private SwitchingVenuesView switchingVenuesView;

    public SwitchingVenuesPresenter(SwitchingVenuesView switchingVenuesView) {
        getInfo = new Is_Networking();
        this.switchingVenuesView = switchingVenuesView;
    }

    public void getClub() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("gym", "getClub"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        switchingVenuesView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                switchingVenuesView.hide();
                SwitchingVenuesCodeEntity switchingVenuesCodeEntity = new SwitchingVenuesCodeEntity();
                switchingVenuesCodeEntity = JsonUtils.getClub(result);
                if (switchingVenuesCodeEntity.getCode().equals("0")) {
                    switchingVenuesView.getallvenues(switchingVenuesCodeEntity);
                }
            }

            @Override
            public void onError(Throwable ex) {
                switchingVenuesView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                switchingVenuesView.hide();
            }

            @Override
            public void onFinished() {
                switchingVenuesView.hide();
            }
        });

    }


    public void switchingvenues(final String clubID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "completeUserInfo"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("currentClubID", clubID);
        switchingVenuesView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                switchingVenuesView.hide();
                SwitchingVenuesSuccessCodeEntity switchingVenuesSuccessCodeEntity = new SwitchingVenuesSuccessCodeEntity();
                switchingVenuesSuccessCodeEntity = JsonUtils.switchingvenues(result);
                if (switchingVenuesSuccessCodeEntity.getCode().equals("0")) {
                    MyApplication.currentClubID = clubID;
                    switchingVenuesView.switchingvenues();
                }
            }

            @Override
            public void onError(Throwable ex) {
                switchingVenuesView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                switchingVenuesView.hide();
            }

            @Override
            public void onFinished() {
                switchingVenuesView.hide();
            }
        });

    }

}