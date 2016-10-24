package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.SwitchingVenuesAdapterEntity;
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
                List<SwitchingVenuesAdapterEntity> data = new ArrayList<>();
                if (switchingVenuesCodeEntity.getCode().equals("0")) {
                    for (int i = 0; i < switchingVenuesCodeEntity.getResult().size(); i++) {
                        SwitchingVenuesAdapterEntity entity = new SwitchingVenuesAdapterEntity();
                        entity.setBrandName(switchingVenuesCodeEntity.getResult().get(i).get(0).getBrandName());
                        entity.setEntityList(switchingVenuesCodeEntity.getResult().get(i));
                        data.add(entity);
                    }
                    switchingVenuesView.getallvenues(data);
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


    public void switchingvenues(final String clubID, final String clubName) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "completeUserInfo"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("currentClubID", clubID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SwitchingVenuesSuccessCodeEntity switchingVenuesSuccessCodeEntity = new SwitchingVenuesSuccessCodeEntity();
                switchingVenuesSuccessCodeEntity = JsonUtils.switchingvenues(result);
                if (switchingVenuesSuccessCodeEntity.getCode().equals("0")) {
                    MyApplication.currentClubID = clubID;
                    MyApplication.currentClubName = clubName;
                    switchingVenuesView.switchingvenues();
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

}