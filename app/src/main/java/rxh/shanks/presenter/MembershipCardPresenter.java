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
    private MembershipCardView view;

    public MembershipCardPresenter(MembershipCardView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getMembershipCard() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("card", "getMyCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                MembershipCardCodeEntity entity = new MembershipCardCodeEntity();
                entity = JsonUtils.getMembershipCard(result);
                if (entity.getCode().equals("0")) {
                    view.getFitCard(entity.getResult());
                } else {
                    view.toast(entity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.error();
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


    //修改首选会员卡
    public void setDefaultCard(final String cardID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("FitCoach/member", "setDefaultCard"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("cardID", cardID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                MembershipCardCodeEntity membershipCardCodeEntity = new MembershipCardCodeEntity();
                membershipCardCodeEntity = JsonUtils.getmyFitCard(result);
                if (membershipCardCodeEntity.getCode().equals("0")) {
                    view.setDefaultCard();
                    MyApplication.defaultmembercard = cardID;
                } else {
                    view.toast(membershipCardCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
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
