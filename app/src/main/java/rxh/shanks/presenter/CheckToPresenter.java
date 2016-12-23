package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.CheckToHandlerCodeEntity;
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
    private CheckToView view;

    public CheckToPresenter(CheckToView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void generatedQR(final String cardID, final String cardType) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "generatedQR"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("cardID", cardID);
        params.addBodyParameter("cardType", cardType);
        view.show(2);
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        view.hide();
                        CheckToCodeEntity checkToCodeEntity = new CheckToCodeEntity();
                        checkToCodeEntity = JsonUtils.generatedQR(result);
                        if (checkToCodeEntity.getCode().equals("0")) {
                            view.generatedQR(checkToCodeEntity.getResult());
                        } else if (checkToCodeEntity.getCode().equals("9")) {
                            view.handleCard(cardID, cardType);
                        } else {
                            view.onFinished(checkToCodeEntity.getError());
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
                }

        );
    }

    public void getFitCard() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "getFitCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        view.show(0);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                FitCardCodeEntity fitCardCodeEntity = new FitCardCodeEntity();
                fitCardCodeEntity = JsonUtils.getFitCard(result);
                if (fitCardCodeEntity.getCode().equals("0")) {
                    view.getFitCard(fitCardCodeEntity.getResult());
                } else {
                    view.onFinished(fitCardCodeEntity.getError());
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


    public void handleCard(final String cardID, final String cardType) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "handleCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("cardID", cardID);
        params.addBodyParameter("cardType", cardType);
        view.show(1);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                CheckToHandlerCodeEntity entity = new CheckToHandlerCodeEntity();
                entity = JsonUtils.handleCard(result);
                if (entity.getCode().equals("0")) {
                    view.handleCardSuccess(cardID, cardType);
                    view.generatedQR(entity.getResult());
                } else {
                    view.onFinished(entity.getError());
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
