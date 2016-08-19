package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.DonationCardCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.DonationCardView;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DonationCardPresenter {

    private GetInfo getInfo;
    private DonationCardView donationCardView;

    public DonationCardPresenter(DonationCardView donationCardView) {
        getInfo = new Is_Networking();
        this.donationCardView = donationCardView;
    }

    public void gitSurplusGiftQR(String cardID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "gitSurplusGiftQR"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("cardID", cardID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                DonationCardCodeEntity donationCardCodeEntity = new DonationCardCodeEntity();
                donationCardCodeEntity = JsonUtils.DonationCard(result);
                if (donationCardCodeEntity.getCode().equals("0")) {
                    donationCardView.gitSurplusGiftQR(donationCardCodeEntity.getResult());
                } else {
                    donationCardView.toast(donationCardCodeEntity.getError());
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


    public void giftQR(String clubID, String cardID, String cardType) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "giftQR"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("count", "1");
        params.addBodyParameter("cardType", cardType);
        params.addBodyParameter("clubID", clubID);
        params.addBodyParameter("cardID", cardID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                DonationCardCodeEntity donationCardCodeEntity = new DonationCardCodeEntity();
                donationCardCodeEntity = JsonUtils.DonationCard(result);
                if (donationCardCodeEntity.getCode().equals("0")) {
                    donationCardView.giftQR(donationCardCodeEntity.getResult());
                } else {
                    donationCardView.toast(donationCardCodeEntity.getError());
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
