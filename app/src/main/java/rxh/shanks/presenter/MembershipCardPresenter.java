package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.MembershipCardCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.MembershipCardView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MembershipCardPresenter {

    private GetInfo getInfo;
    private MembershipCardView membershipCardView;

    public MembershipCardPresenter(MembershipCardView membershipCardView) {
        getInfo = new Is_Networking();
        this.membershipCardView = membershipCardView;
    }

    public void getFitCard() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "getFitCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        membershipCardView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                membershipCardView.hide();
                MembershipCardCodeEntity membershipCardCodeEntity = new MembershipCardCodeEntity();
                membershipCardCodeEntity = JsonUtils.getmyFitCard(result);
                if (membershipCardCodeEntity.getCode().equals("0")) {
                    membershipCardView.getFitCard(membershipCardCodeEntity.getResult());
                }
            }

            @Override
            public void onError(Throwable ex) {
                membershipCardView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                membershipCardView.hide();
            }

            @Override
            public void onFinished() {
                membershipCardView.hide();
            }
        });
    }


    //修改首选会员卡
    public void setDefaultCard(final String cardID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("FitCoach/member", "setDefaultCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("cardID", cardID);
        membershipCardView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                membershipCardView.hide();
                MembershipCardCodeEntity membershipCardCodeEntity = new MembershipCardCodeEntity();
                membershipCardCodeEntity = JsonUtils.getmyFitCard(result);
                if (membershipCardCodeEntity.getCode().equals("0")) {
                    membershipCardView.setDefaultCard();
                    MyApplication.defaultmembercard = cardID;
                } else {
                    membershipCardView.toast(membershipCardCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                membershipCardView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                membershipCardView.hide();
            }

            @Override
            public void onFinished() {
                membershipCardView.hide();
            }
        });
    }
}
