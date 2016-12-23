package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.PaymentCodeEntity;
import rxh.shanks.entity.PaymentConfirmCodeEntity;
import rxh.shanks.entity.PaymentInfoCodeEntity;
import rxh.shanks.entity.PaymentStoredValueCodeCardListEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.PaymentView;

/**
 * Created by Administrator on 2016/12/6.
 */
public class PaymentPresenter {


    private GetInfo getInfo;
    private PaymentView view;

    public PaymentPresenter(PaymentView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void generatedPaidQR() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "generatedPaidQR"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("userID", MyApplication.userID);
        view.show(0);
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        view.hide();
                        PaymentCodeEntity entity = new PaymentCodeEntity();
                        entity = JsonUtils.generatedPaidQR(result);
                        if (entity.getCode().equals("0")) {
                            view.generatedPaidQR(entity.getResult());
                        } else {
                            view.toast(entity.getError());
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


    public void getpaidcard(String orderID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "getPaidCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("gymID", MyApplication.currentClubID);
        params.addBodyParameter("orderID", orderID);
        view.show(1);
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        view.hide();
                        PaymentStoredValueCodeCardListEntity entity = new PaymentStoredValueCodeCardListEntity();
                        entity = JsonUtils.getpaidcard(result);
                        if (entity.getCode().equals("0")) {
                            view.getpaidcard(entity.getResult());
                        } else {
                            view.toast(entity.getError());
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

    public void get_pay_info(String orderID, String cardID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "getDiscountPrice"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("orderID", orderID);
        params.addBodyParameter("cardID", cardID);
        view.show(3);
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        view.hide();
                        PaymentInfoCodeEntity entity = new PaymentInfoCodeEntity();
                        entity = JsonUtils.get_pay_info(result);
                        if (entity.getCode().equals("0")) {
                            view.get_pay_info(entity.getResult());
                        } else {
                            view.toast(entity.getError());
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

    public void pay_confirm(String orderID, String cardID, String password) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("paidcard", "confirm"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("orderID", orderID);
        params.addBodyParameter("cardID", cardID);
        params.addBodyParameter("password", password);
        view.show(2);
        getInfo.getinfo(params, new Response() {
                    @Override
                    public void onSuccess(String result) {
                        view.hide();
                        PaymentConfirmCodeEntity entity = new PaymentConfirmCodeEntity();
                        entity = JsonUtils.pay_confirm(result);
                        if (entity.getCode().equals("0")) {
                            view.pay_confirm();
                        } else {
                            view.toast(entity.getError());
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
}
