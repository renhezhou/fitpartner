package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.CoachDetailsCodeEntity;
import rxh.shanks.entity.FitCardCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.CoachDetailsView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CheckToPresenter {

    private GetInfo getInfo;
    private CheckToView checkToView;

    public CheckToPresenter(CheckToView checkToView) {
        getInfo = new Is_Networking();
        this.checkToView = checkToView;
    }

    public void generatedQR(String cardID, String cardType) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "generatedQR"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("cardID", cardID);
        params.addBodyParameter("cardType", cardType);
        checkToView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                checkToView.hide();
                CheckToCodeEntity checkToCodeEntity = new CheckToCodeEntity();
                checkToCodeEntity = JsonUtils.generatedQR(result);
                if (checkToCodeEntity.getCode().equals("0")) {
                    checkToView.generatedQR(checkToCodeEntity.getResult());
                } else {
                    checkToView.onFinished(checkToCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                checkToView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                checkToView.hide();
            }

            @Override
            public void onFinished() {
                checkToView.hide();
            }
        });
    }

    public void getFitCard() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "getFitCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        checkToView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                checkToView.hide();
                FitCardCodeEntity fitCardCodeEntity = new FitCardCodeEntity();
                fitCardCodeEntity = JsonUtils.getFitCard(result);
                if (fitCardCodeEntity.getCode().equals("0")) {
                    checkToView.getFitCard(fitCardCodeEntity.getResult());
                } else {
                    checkToView.onFinished(fitCardCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                checkToView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                checkToView.hide();
            }

            @Override
            public void onFinished() {
                checkToView.hide();
            }
        });
    }

}
